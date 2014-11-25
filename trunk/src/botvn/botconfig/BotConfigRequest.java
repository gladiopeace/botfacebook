package botvn.botconfig;

import botvn.HttpUtil;
import botvn.RemoteConstants;
import botvn.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vanvo
 */
public final class BotConfigRequest implements Runnable {

    private BotConfigListener mBotConfigListener;

    /**
     *
     * @param listener
     */
    public BotConfigRequest(BotConfigListener listener) {
        mBotConfigListener = listener;
    }

    /**
     *
     */
    private Thread mThread;

    @Override
    public void run() {
        try {
            Response response = HttpUtil.getLocal(RemoteConstants.URL_CONFIG, null);
            if (response.getHtml() != null) {
                JSONParser json = new JSONParser();
                JSONObject j = (JSONObject) json.parse(response.getHtml());
                if (j.containsKey("configs")) {
                    JSONObject configs = (JSONObject) j.get("configs");
                    if (configs.containsKey("fb_parse")) {
                        JSONObject fbParse = (JSONObject) configs.get("fb_parse");

                        if (fbParse.containsKey("url_home")) {
                            BotConfig.URLHome = (String) fbParse.get("url_home");
                        }

                        if (fbParse.containsKey("url_profile")) {
                            BotConfig.URLProfile = (String) fbParse.get("url_profile");
                        }

                        if (fbParse.containsKey("url_access_token")) {
                            BotConfig.URLShortAccessToken = (String) fbParse.get("url_access_token");
                        }

                        if (fbParse.containsKey("url_access_token_2_months")) {
                            BotConfig.URLLongAccessToken = (String) fbParse.get("url_access_token_2_months");
                        }
                        
                        if(fbParse.containsKey("url_list_friends")){
                            BotConfig.URLFriends = (String)fbParse.get("url_list_friends");
                        }
                        
                        if(fbParse.containsKey("url_like_fanpage")){
                            BotConfig.URLLikeFanPage = (String)fbParse.get("url_like_fanpage");
                        }
                    }

                    if (configs.containsKey("graph_search")) {
                        JSONObject graphSearch = (JSONObject) configs.get("graph_search");
                        if (graphSearch.containsKey("page")) {
                            BotConfig.GraphSearch.Page = (String) graphSearch.get("page");
                        }

                        if (graphSearch.containsKey("user")) {
                            BotConfig.GraphSearch.User = (String) graphSearch.get("user");
                        }

                        if (graphSearch.containsKey("group")) {
                            BotConfig.GraphSearch.Group = (String) graphSearch.get("group");
                        }

                        if (graphSearch.containsKey("event")) {
                            BotConfig.GraphSearch.Event = (String) graphSearch.get("event");
                        }
                    }

                    if (configs.containsKey("account_test")) {
                        JSONObject account_test = (JSONObject) configs.get("account_test");
                        if (account_test.containsKey("email")) {
                            BotConfig.Account.Email = (String) account_test.get("email");
                        }

                        if (account_test.containsKey("pwd")) {
                            BotConfig.Account.Pwd = (String) account_test.get("pwd");
                        }

                        if (account_test.containsKey("access_token")) {
                            BotConfig.Account.Access_token = (String) account_test.get("access_token");
                        }
                    }

                    if (configs.containsKey("bot")) {
                        JSONObject bot = (JSONObject) configs.get("bot");
                        if (bot.containsKey("messages")) {
                            JSONObject messages = (JSONObject) bot.get("messages");
                            if (messages.containsKey("url")) {
                                BotConfig.BotMessage.Url = (String) messages.get("url");
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BotConfigRequest.class.getName()).log(Level.SEVERE, null, ex);
            if (mBotConfigListener != null) {
                mBotConfigListener.OnInitError();
            }
        } catch (ParseException ex) {
            if (mBotConfigListener != null) {
                mBotConfigListener.OnInitError();
            }
            Logger.getLogger(BotConfigRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mBotConfigListener != null) {
            mBotConfigListener.OnInitSuccess();
        }
    }

    public void start() throws IllegalThreadStateException {
        if (mThread == null) {
            mThread = new Thread(this, this.getClass().getName());
            mThread.start();
        }
    }
}
