package botvn.libraries;

import botvn.HttpUtil;
import botvn.JerryUtil;
import botvn.Response;
import botvn.botconfig.BotConfig;
import java.io.IOException;
import java.util.Map;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;
import jodd.json.JsonParser;
import org.apache.http.client.CookieStore;

/**
 *
 * @author vanvo
 */
public class BotUtils {

    /**
     * Return an access token expired in 2 months
     * 
     * @param cks : cookie when login with facebook
     * @return
     */
    public static String getAccessToken(CookieStore cks) throws IOException {
        if (cks == null) {
            return null;
        }
        
        Response response = HttpUtil.getLocal(BotConfig.URLShortAccessToken, cks);
        Jerry doc = Jerry.jerry(response.getHtml());
        String href = doc.$("a").attr("href");
        if (href != null) {
            // Get access token short lived
            response = HttpUtil.getLocal(href, cks);
            String jsonRaw = response.getHtml();
            JsonParser jsonResults = new JsonParser();
            Map<String, String> json = jsonResults.parse(jsonRaw);
            int errorCode = Integer.parseInt(json.get("error"));
            if (errorCode == 0) {
                String accessToken = json.get("access_token");
                String url = BotConfig.URLLongAccessToken + "&access_token=" + accessToken;
                // Get access token long lived (2 months)
                response = HttpUtil.getLocal(url, cks);
                json = jsonResults.parse(response.getHtml());
                url = json.get("url");
                response = HttpUtil.getLocal(url, cks);
                String rawResult = response.getHtml();
                String[] tmp = rawResult.split("&");
                if(tmp.length > 1){
                    String access_token_2m = tmp[0].split("=")[1];
                    return access_token_2m;
                }
                return accessToken;
            }
        }
        return null;
    }

    /**
     * 
     * @param cks
     * @return
     * @throws IOException 
     */
    public static String getFriends(CookieStore cks) throws IOException{
        if(cks == null) return null;
        HttpBrowser browser = new HttpBrowser();
        HttpRequest request = HttpRequest.get(BotConfig.URLHome + "van.vo0?sk=friends");
        
        Response response = HttpUtil.getLocal(BotConfig.URLHome + "van.vo0?sk=friends", cks);
        String html = response.getHtml();
        return "";
    }
    
    /**
     * 
     * @param keyword 
     */
    public static void FindFanPage(String keyword){
        
    }
    
    /**
     * 
     * @param email
     * @param pass
     * @return
     * @throws IOException 
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
}
