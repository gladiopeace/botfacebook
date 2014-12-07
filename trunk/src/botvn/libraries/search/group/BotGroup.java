package botvn.libraries.search.group;

import botvn.Constants;
import botvn.HttpUtil;
import botvn.JerryUtil;
import botvn.Response;
import botvn.botconfig.BotConfig;
import botvn.botconfig.BotUrlFormatter;
import botvn.controllers.MainFormController;
import botvn.libraries.BotBase;
import botvn.libraries.BotEventListener;
import botvn.libraries.LoggingListener;
import botvn.libraries.LoggingUtils;
import botvn.libraries.Utils;
import botvn.libraries.search.fanpage.BotFanPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vanvo
 */
public class BotGroup extends BotBase implements Runnable {

    private Thread mThread;

    private BotEventListener<BotGroupObject> mBotGroupListener;
    private LoggingListener mLogging;
    private List<BotGroupObject> mResults;
    private List<BotGroupObject> mMyGroups;
    private int mLimit = 2000; // as defaults
    private OnFetchMyGroupsListener mFetchMyGroupListener;

    /**
     *
     * @param listener
     */
    public BotGroup(BotEventListener listener) {
        mBotGroupListener = listener;
        if (mResults == null) {
            mResults = new ArrayList<>();
        }
    }

    /**
     *
     * @param limit
     */
    public void setLimit(int limit) {
        mLimit = limit;
    }

    public void setLog(LoggingListener logListener) {
        mLogging = logListener;
    }

    @Override
    public void run() {
        graphSearchGroup(BotUrlFormatter.getSearchGroupUrl(mKeyword), mLimit);
        mBotGroupListener.OnReceveFinished(mResults);
    }

    /**
     *
     */
    public void start() {
        mThread = new Thread(this, this.getClass().getName());
        mThread.start();
    }

    /**
     *
     * @param url
     * @param limit
     */
    private void graphSearchGroup(String url, int limit) {
        try {
            Response res = HttpUtil.getLocal(url, mLoginCookies, false);
            if (res.getHtml() != null) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonPage = (JSONObject) jsonParser.parse(res.getHtml());
                if (jsonPage.containsKey("data")) {
                    JSONArray data = (JSONArray) jsonPage.get("data");
                    if (data.size() == 0) {
                        return;
                    }
                    Iterator i = data.iterator();
                    BotGroupObject group = null;
                    while (i.hasNext()) {
                        JSONObject element = (JSONObject) i.next();
                        String id = "", name = "";
                        if (element.containsKey("id")) {
                            id = (String) element.get("id");
                        }

                        if (element.containsKey("name")) {
                            name = (String) element.get("name");
                        }

                        int members = findMember(id);
                        if (members < limit) {
                            continue;
                        }

                        group = new BotGroupObject();
                        group.IsSelected = true;
                        group.ID = id;
                        group.Name = name;
                        group.Members = members;
                        group.URL = BotUrlFormatter.getGroupPage(id);;

                        mBotGroupListener.OnReceiving(group);
                        mResults.add(group);
                        LoggingUtils.print(element.toString());
                    }
                }

                if (jsonPage.containsKey("paging")) {
                    JSONObject paging = (JSONObject) jsonPage.get("paging");
                    if (paging.containsKey("next")) {
                        String url_next = (String) paging.get("next");
                        graphSearchGroup(url_next, limit);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(BotFanPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BotGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param groupID
     * @return
     */
    private int findMember(String groupID) {
        try {
            String url = BotUrlFormatter.getGroupPage(groupID);
            Response res = HttpUtil.getLocal(url, mLoginCookies, false);
            Jerry doc = Jerry.jerry(res.getHtml());
            Jerry group_status = doc.$("div#root tr td div");
            String text = group_status.text();
            Jerry members = doc.$("div#root tr td span");
            String count = members.first().text();
            if (count != null || count.length() > 0) {
                int c = Integer.parseInt(count);
                return c;
            }
            return 0;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(BotGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     *
     */
    public void JoinGroup() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (BotGroupObject group : mResults) {
                    try {
                        Response res = HttpUtil.getLocal(group.URL, mLoginCookies, false);
                        Jerry doc = Jerry.jerry(res.getHtml());
                        Jerry formJoin = doc.$("div#root div form");
                        String action = formJoin.attr("action");
                        Map<String, String> joinFormParams = JerryUtil.form(formJoin);
                        String urlAction = BotConfig.URLHomeMobile + action;
                        res = HttpUtil.postLocal(urlAction, joinFormParams, mLoginCookies, true);
                        if (mLogging != null) {
                            mLogging.OnLog(LoggingUtils.print("Sent", group.URL.replace("m.", "")));
                        }
                        Thread.sleep(Utils.randSec(Constants.MIN_DELAY, Constants.MAX_DELAY));
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(BotGroup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (mLogging != null) {
                    mLogging.OnLog(LoggingUtils.print("GROUP", "--DONE--"));
                }
            }
        }).start();
    }

    /**
     *
     */
    public void ListGroups() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fetchListMyGroups(BotConfig.UrlSeeMoreGroups);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(BotGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void setFetchMyGroupsEvent(OnFetchMyGroupsListener listener){
        mFetchMyGroupListener = listener;
    }
    
    
    /**
     * @param url
     * @param listener
     * @throws IOException
     * @throws InterruptedException
     */
    private void fetchListMyGroups(final String url) throws IOException, InterruptedException {
        Response res = HttpUtil.getLocal(url, mLoginCookies, false);
        Jerry doc = Jerry.jerry(res.getHtml());
        Jerry lists = doc.$("div#root table tbody tr td div:nth-child(2)");
        if (lists.length() > 1) {
            if (mMyGroups == null) {
                mMyGroups = new ArrayList<>();
            }
            lists.first().$("ul li").each(new JerryFunction() {

                @Override
                public boolean onNode(Jerry $this, int index) {
                    Jerry a = $this.find("a").first();
                    if (a.length() > 0) {
                        String href = a.attr("href");
                        if (!href.contains("seemore")) {
                            String name = a.text();
                            BotGroupObject botGroupObject = new BotGroupObject();
                            botGroupObject.URL = BotUrlFormatter.getShortProfileUrlMobile(href);
                            botGroupObject.Name = name;
                            botGroupObject.IsSelected = true;
                            if(mFetchMyGroupListener != null){
                                mFetchMyGroupListener.OnFetchGroup(botGroupObject);
                            }
                            mMyGroups.add(botGroupObject);
                        } else {
                            Jerry seemore = $this.$("div#m_more_item");
                            if (seemore.length() > 0) {
                                String seemore_href = seemore.find("a").attr("href");
                                String seemore_url = BotUrlFormatter.getShortProfileUrlMobile(seemore_href);
                                try {
                                    fetchListMyGroups(seemore_url);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(BotGroup.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    return true;
                }
            });
            if(mFetchMyGroupListener != null){
                mFetchMyGroupListener.OnFetchFinished(mMyGroups);
                mFetchMyGroupListener = null;
            }
        }
    }

    /**
     *
     * @return
     */
    public List<BotGroupObject> getMyGroups() {
        return mMyGroups;
    }

    public void LikeComment(final List<BotGroupObject> groups, final String commentTemp, final IPostCommentListener listener) {
        final String[] commentsTemp = commentTemp.split("\\|");
        final Random rand = new Random();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (BotGroupObject obj : groups) {
                    if(!obj.IsSelected) continue;
                    rand.setSeed(System.currentTimeMillis());
                    final int comment_post = commentsTemp.length > 1 ? rand.nextInt(commentsTemp.length - 1) : 0;
                    try {
                        Response res = HttpUtil.getLocal(obj.URL, mLoginCookies, true);
                        Jerry doc = Jerry.jerry(res.getHtml());
                        Jerry posts = doc.$("div#m_group_stories_container div");
                        posts.$("div").each(new JerryFunction() {

                            @Override
                            public boolean onNode(Jerry $this, int index) {
                                Jerry action = $this.find("div").last().last();
                                Jerry like = action.find("span a");

                                String like_href = like.attr("href"); // like href
                                if (like.length() > 1) {
                                    like_href = like.last().attr("href");
                                }
                                // like
                                like_href = like_href.substring(1);
                                String like_action = BotUrlFormatter.getShortProfileUrlMobile(like_href);
                                LoggingUtils.print("URL like", like_action);
                                try {
                                    HttpUtil.getLocal(like_action, mLoginCookies, true);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                // find and open url comment
                                Jerry comments = action.$("a:nth-child(1)");
                                String comment_href = comments.attr("href");
                                try {
                                    String comment_action = BotUrlFormatter.getShortProfileUrlMobile(comment_href);
                                    LoggingUtils.print("URL comment", comment_action);
                                    Response res = HttpUtil.getLocal(comment_action, mLoginCookies, true);
                                    Jerry doc = Jerry.jerry(res.getHtml());
                                    Jerry form = doc.$("div#m_story_permalink_view div form");
                                    String action_href = form.attr("action");
                                    Map<String, String> params = JerryUtil.form(form);
                                    params.put("comment_text", commentsTemp[comment_post]);
                                    String url_comment = BotUrlFormatter.getShortProfileUrlMobile(action_href);
                                    res = HttpUtil.postLocal(url_comment, params, mLoginCookies, true);
                                    String s = res.getStatusLine().toString();
                                    listener.OnComment(comment_action);
                                    /*
                                     // find and open form comment by attach
                                     Jerry attach = doc.$("div#root div#m_story_permalink_view div:last-child form span a").first();
                                     String href = attach.attr("href");
                                     res = HttpUtil.getLocal(BotUrlFormatter.getShortProfileUrlMobile(href), mLoginCookies, true);
                                     doc = Jerry.jerry(res.getHtml());
                                     Jerry attach_form = doc.$("div#root form");
                                     Jerry input = attach_form.find("input[name=photo]");
                                     if(input.length() > 0){
                                     String image = BotConfigLocal.getCurrentDir() + "/1.png";
                                     Map<String, String> params = JerryUtil.form(attach_form);
                                     Map<String, Object> args = new HashMap<>();
                                     args.putAll(params);
                                    
                                     args.put("photo", new File(image));
                                     args.put("file", new File(image));
                                     args.put("comment_text", "test");
                                     String action_upload = attach_form.attr("action");
                                     res = HttpUtil.postLocal(action_upload, params, mLoginCookies, true);                                    
                                     }*/
                                    //LoggingUtils.print(href);
                                } catch (IOException | InterruptedException ex) {
                                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                return false;
                            }
                        });
                        Thread.sleep(Utils.randSec(Constants.MIN_DELAY, Constants.MAX_DELAY));
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                listener.OnFinished();
            }
        }).start();
    }

}
