package botvn;

import botvn.botconfig.BotConfig;
import botvn.botconfig.BotConfigListener;
import botvn.botconfig.BotConfigRequest;
import botvn.libraries.BotUtils;
import botvn.libraries.LoggingUtils;
import botvn.libraries.LoginInfo;
import botvn.libraries.message.BotMessageAds;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import jodd.mutable.MutableInteger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ex.HttpClientRequestSSL;
import org.apache.http.cookie.Cookie;

public class FBookBot implements BotConfigListener{
    private static Response mResponseLogin = null;
    
    public static void main(String[] args) throws IOException {
        new BotConfigRequest(new FBookBot()).start();
        
        // Send message ads
        //mResponseLogin = loginToFacebook(EMAIL, PASS);
        //String userID = getThisID(mResponseLogin);
        
        //BotUtils.getFriends(mResponseLogin.getCookieStore());
        
        // Get access token long lived (2 months)
        //String access_token = BotUtils.getAccessToken(mResponseLogin.getCookieStore());
        //System.out.println("access_token: " + access_token);
        
        //getUserInfo(userID, mResponseLogin.getCookieStore());

        //LoginInfo loginInfo = getCurrentInfo(findFriends(mResponseLogin).getHtml());
        //MessageAds msg = new MessageAds(loginInfo.fbdtsg, mResponseLogin.getCookieStore());
        //sendMessage("test", "394312050717787", "100000078898384");
        //msg.Send("Ban hom nay the nao, khoe khong? ", loginInfo.userId, "100008467447092");//100000078898384");
        //msg.Send("FacebookBotvn", loginInfo.userId, "100000078898384");//100000078898384");
        /*
         for(int i = 1; i <= 1; i++){
         try {
                
         //msg.Send("this is a test " + (i + 1), loginInfo.userId, "100000078898384");//100000078898384");
         Thread.sleep(1000);
         }
         //response = findFriends(responseLogin);
         //listAndAddFriends(response.getHtml(), new MutableInteger(0), responseLogin);
         catch (InterruptedException ex) {
         Logger.getLogger(FBookBot.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         */
        // Send request add friend: http://findmyfacebookid.com/
        /*
         responseLogin = loginToFacebook(EMAIL, PASS);
         LoginInfo loginInfo = getCurrentInfo(findFriends(responseLogin).getHtml());
         addFriend(loginInfo.userId, "100008467447092", loginInfo.fbdtsg, responseLogin.getCookieStore());
         */
    }
    
    @Override
    public void OnInitSuccess() {
        try {
            // Send message ads
            mResponseLogin = loginToFacebook(BotConfig.Account.Email, BotConfig.Account.Pwd);
            String userID = getThisID(mResponseLogin);
            
            //BotUtils.getFriends(mResponseLogin.getCookieStore());
            
            // Get access token long lived (2 months)
            String access_token = BotUtils.getAccessToken(mResponseLogin.getCookieStore());
            System.out.println("access_token: " + access_token);
            
        } catch (IOException ex) {
            Logger.getLogger(FBookBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void OnInitError() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    static Response getUserInfo(String userID, CookieStore cks) throws IOException {
        //
        String url = String.format(BotConfig.URLProfile, userID);
        
        Response me = HttpUtil.getLocal(url, cks);
        String htmlMe = me.getHtml();
        Jerry doc = Jerry.jerry(htmlMe);
        
        // find fb_dtsg
        Jerry jerry = doc.$("input[name='fb_dtsg']");
        String fb_dtsg = jerry.attr("value");
        LoggingUtils.print("faceID", "value = " + fb_dtsg);
        LoggingUtils.print("fb_dtsg", "value = " + fb_dtsg);
        return null;
    }

    /**
     * Creates new Facebook session.
     */
    public static Response loginToFacebook(String email, String pass) throws IOException {
        System.out.println("login...");
        Response response = HttpUtil.getLocal("https://www.facebook.com", null);// HttpUtil.get("http://www.facebook.com", null);
        Jerry doc = Jerry.jerry(response.getHtml());
        Jerry loginForm = doc.$("#login_form");

        String action = loginForm.attr("action");

        Map<String, String> loginFormParams = JerryUtil.form(loginForm);
        loginFormParams.put("email", email);
        loginFormParams.put("pass", pass);
        CookieStore cks = response.getCookieStore();
        Response loginResponse = HttpUtil.postLocal(action, loginFormParams, cks);
        return loginResponse;
    }

    /**
     * Reads the page with friends proposals.
     */
    public static Response findFriends(Response response) throws IOException {
        System.out.println("finding friends...");
        // another link: https://www.facebook.com/?sk=ff
        // https://www.facebook.com/friends/requests/?ref=tn&fcref=ffb
        response = HttpUtil.getLocal("https://www.facebook.com/?sk=ff", response.getCookieStore());
        return response;
    }

    /**
     * Lists all friends and invite some.
     */
    public static void listAndAddFriends(String html, final MutableInteger numberOfFriendsToInvite, final Response response) {
        System.out.println("listing friends...\n");
        Jerry doc = Jerry.jerry(html);

        // find user id
        Jerry userLink = doc.$("#facebook div#pagelet_bluebar ul li a img");
        final String facebookUserId = getCurrentUserID(userLink);
        if (facebookUserId == null) {
            return;
        }
        System.out.println("facebook user id: " + facebookUserId);

        // find fb_dtsg
        Jerry jerry = doc.$("input[name='fb_dtsg']");
        final String fb_dtsg = jerry.attr("value");
        System.out.println("fb_dtsg: " + fb_dtsg);

        // parse friends
        Jerry form = doc.$("form.friendBrowserForm");

        form.$("ul.uiList li div.fsl").each(new JerryFunction() {
            public boolean onNode(Jerry $this, int index) {
                String friendName = $this.find("a").text();

                String friendFacebookId = extractId($this.$("a"));

                System.out.println(friendFacebookId + " > " + friendName);
                if (numberOfFriendsToInvite.value > 0) {

                    try {
                        addFriend(facebookUserId, friendFacebookId, fb_dtsg, response);
                    } catch (IOException ioex) {

                        System.out.println("failed.");
                    }

                    numberOfFriendsToInvite.value--;
                }
                return true;
            }
        });
    }

    public static void addFriend(String facebookUserId, String friendFacebookId, String fb_dtsg, Response response) throws IOException {
        System.out.println(">>> adding friend: " + facebookUserId);

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("__a", "1");
        params.put("__user", facebookUserId);
        params.put("to_friend", friendFacebookId);
        params.put("action", "add_friend");
        params.put("how_found", "requests_page_pymk");
        params.put("fb_dtsg", fb_dtsg);
        params.put("outgoing_id", "js_2");
        params.put("ttstamp", HackUtils.generatettstamp(fb_dtsg));
        String url = String.format("https://www.facebook.com/ajax/add_friend/action.php");

        response = HttpUtil.postLocal(url, params, response.getCookieStore());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHtml());
    }

    static void sendMessage(String message, String access_token, String facebookUserId) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("message", message);
        params.put("access_token", access_token);

        Response response = HttpUtil.postLocal("https://graph.facebook.com/" + facebookUserId + "/feed", params, null);
        System.out.println(response.getStatusLine());
        System.out.println(response.getHtml());
    }

    public static void addFriend(String facebookUserId, String friendFacebookId, String fb_dtsg, CookieStore cks) throws IOException {
        System.out.println(">>> adding friend: " + facebookUserId);

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("__a", "1");
        params.put("__user", facebookUserId);
        params.put("to_friend", friendFacebookId);
        params.put("action", "add_friend");
        params.put("how_found", "requests_page_pymk");
        params.put("fb_dtsg", fb_dtsg);
        params.put("outgoing_id", "js_2");
        params.put("ttstamp", HackUtils.generatettstamp(fb_dtsg));
        String url = String.format("https://www.facebook.com/ajax/add_friend/action.php");

        Response response = HttpUtil.postLocal(url, params, cks);
        System.out.println(response.getStatusLine());
        System.out.println(response.getHtml());
    }

    /**
     * Extracts facebook id from a link.
     */
    public static String extractId(Jerry link) {
        String href = link.attr("href");

        int index1 = href.indexOf("id=");
        if (index1 == -1) {
            href = link.attr("data-hovercard");
            index1 = href.indexOf("id=");
            if (index1 == -1) {
                return null;
            }
        }
        int index2 = href.indexOf('&', index1);
        if (index2 == -1) {
            return href.substring(index1);
        }
        return href.substring(index1 + 3, index2);
    }

    public static String getCurrentUserID(Jerry link) {
        String href = link.attr("id");
        if (href == null) {
            return "";
        }
        String[] split = href.split("_");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        return "";
    }

    public static LoginInfo getCurrentInfo(String html) {
        Jerry doc = Jerry.jerry(html);
        // find user id
        Jerry userLink = doc.$("#facebook div#pagelet_bluebar ul li a img");
        final String facebookUserId = getCurrentUserID(userLink);
        if (facebookUserId == null) {
            return null;
        }
        // find fb_dtsg
        Jerry jerry = doc.$("input[name='fb_dtsg']");
        final String fb_dtsg = jerry.attr("value");

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.userId = facebookUserId;
        loginInfo.fbdtsg = fb_dtsg;
        return loginInfo;
    }

    /**
     * Get current user ID logged
     * @param loginResponse
     * @return 
     */
    public static String getThisID(Response loginResponse){
        String userId = "";
        List<Cookie> cks = loginResponse.getCookies();
        for (Cookie ck : cks) {
            if (ck.getName().equals("c_user")) {
                String val = ck.getValue();
                if (val != null && val.length() > 0) {
                    userId = val;
                }
                break;
            }
        }

        return userId;
    }
}
