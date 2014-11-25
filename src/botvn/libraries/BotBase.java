package botvn.libraries;

import static botvn.FBookBot.getCurrentUserID;
import botvn.HttpUtil;
import botvn.Response;
import botvn.botconfig.BotConfig;
import botvn.botconfig.BotUrlFormatter;
import botvn.libraries.message.BotMessageAds;
import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.BotSearchObjectType;
import botvn.libraries.search.fanpage.BotFanPage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jodd.jerry.Jerry;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vanvo
 */
public abstract class BotBase {

    /**
     *
     */
    protected ThreadGroup mThreadGroups;

    /**
     * Cookie store when logged
     */
    protected CookieStore mCks;
    
    /**
     * 
     */
    protected static CookieStore mLoginCookies;

    /**
     * The keyword
     */
    protected String mKeyword;

    /**
     * Search type, default as user
     */
    protected BotSearchObjectType mSearchType = BotSearchObjectType.USER;

    /**
     * The list of search results
     */
    protected List<BotSearchObject> mResults;

    /**
     * 
     */
    protected BotEventListener<BotSearchObject> mBotSearchListener;

    /**
     * 
     */
    protected String mCurrentUserID;
    
    /**
     * Constructor
     */
    protected BotBase() {
        if (mThreadGroups == null) {
            mThreadGroups = new ThreadGroup(this.toString());
        }
    }

    /**
     * The keyword to search
     *
     * @param keyword
     */
    public final void setKeyword(String keyword) {
        this.mKeyword = keyword;
    }

    /**
     * Set current cookie store when user login
     *
     * @param cks
     */
    public void setLoginCookieStore(CookieStore cks) {
        // Always override newest cookies
        mLoginCookies = cks;
    }
    
    public void setCookieStore(CookieStore cks){
        mCks = cks;
    }
    /**
     * Search by user, page, group or event
     *
     * @param type
     */
    public void setSearchType(BotSearchObjectType type) {
        mSearchType = type;
    }

    /**
     * Search default, only get name and id
     *
     * @param url
     */
    protected final void search(String url, BotSearchObjectType type) {
        try {
            Response res = HttpUtil.getLocal(url, mCks);
            if (res.getHtml() != null) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonPage = (JSONObject) jsonParser.parse(res.getHtml());
                if (jsonPage.containsKey("data")) {
                    JSONArray data = (JSONArray) jsonPage.get("data");
                    if (data.size() == 0) {
                        return;
                    }
                    Iterator i = data.iterator();
                    BotSearchObject object = null;
                    while (i.hasNext()) {
                        JSONObject element = (JSONObject) i.next();
                        String id = "", name = "";
                        if (element.containsKey("id")) {
                            id = (String) element.get("id");
                        }

                        if (element.containsKey("name")) {
                            name = (String) element.get("name");
                        }

                        object = new BotSearchObject();
                        object.ID = id;
                        object.Name = name;
                        object.Type = type;

                        mBotSearchListener.OnReceiving(object);
                        mResults.add(object);

                        LoggingUtils.print(element.toString());
                    }
                }

                if (jsonPage.containsKey("paging")) {
                    JSONObject paging = (JSONObject) jsonPage.get("paging");
                    if (paging.containsKey("next")) {
                        String url_next = (String) paging.get("next");
                        search(url_next, type);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(BotFanPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get current user id
     * @return 
     */
    public String getThisID(){
        String userId = "";
        List<Cookie> cks = mCks.getCookies();
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
    
    /**
     * 
     * @return 
     * @throws java.io.IOException 
     */
    public LoginInfo getCurrentInfo() throws IOException {
        Response response = HttpUtil.getLocal(BotConfig.URLFriends, mLoginCookies);
        Jerry doc = Jerry.jerry(response.getHtml());
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
     * 
     * @param results
     * @return 
     */
    protected String getJsonResponse(String results){
        int begin = results.indexOf("{");
        String jsonRaw = results.substring(begin);
        return jsonRaw;
    }
    
    /**
     * 
     * @param results 
     * @return  
     */
    protected boolean isSuccess(String results){
        String jsonRaw = getJsonResponse(results);
        try {
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(jsonRaw);
            if(!(jsonObj.containsKey("error") && jsonObj.containsKey("errorSummary"))){
                return true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(BotMessageAds.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
