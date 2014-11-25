package botvn.libraries.search;

import botvn.HttpUtil;
import botvn.Response;
import botvn.botconfig.BotUrlFormatter;
import botvn.libraries.BotBase;
import botvn.libraries.BotEventListener;
import botvn.libraries.LoggingUtils;
import botvn.libraries.search.event.BotEventObject;
import botvn.libraries.search.fanpage.BotFanPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vanvo
 */
public class BotSearch extends BotBase implements Runnable{

    /**
     * 
     */
    private Thread mThread;
    
    /**
     * 
     */
    private int value;
    
    /**
     * 
     * @param listener 
     */
    public BotSearch(BotEventListener<BotSearchObject> listener, int value){
        this.value = value;
        mBotSearchListener = listener;
        if(mResults == null){
            mResults = new ArrayList<>();
        }
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        if((value & BotSearchObjectType.USER.getValue()) == BotSearchObjectType.USER.getValue()){
            searchUser(BotUrlFormatter.getSearchUserUrl(mKeyword));
        }
        
        if((value & BotSearchObjectType.PAGE.getValue()) == BotSearchObjectType.PAGE.getValue()){
            searchFanPage(BotUrlFormatter.getSearchFanPageUrl(mKeyword));
        }
        
        if((value & BotSearchObjectType.GROUP.getValue()) == BotSearchObjectType.GROUP.getValue()){
            searchGroup(BotUrlFormatter.getSearchGroupUrl(mKeyword));
        }
        
        if((value & BotSearchObjectType.EVENT.getValue()) == BotSearchObjectType.EVENT.getValue()){
            searchEvent(BotUrlFormatter.getSearchEventUrl(mKeyword));
        }
        
        mBotSearchListener.OnReceveFinished(mResults);
    }
    
    /**
     * 
     */
    public void start(){
        if(mThread == null){
            mThread = new Thread(this, this.getClass().getName());
            mThread.start();
        }
    }
    
    /**
     * 
     * @param url 
     */
    private void searchUser(String url){
        search(url, BotSearchObjectType.USER);
    }
    
    /**
     * 
     * @param url 
     */
    private void searchGroup(String url){
        search(url, BotSearchObjectType.GROUP);
    }
    
    /**
     * 
     * @param url 
     */
    private void searchEvent(String url){
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
                    BotEventObject event = null;
                    while (i.hasNext()) {
                        JSONObject element = (JSONObject) i.next();
                        String id = "";
                        String name = "";
                        String start_time = "";
                        String location = "";
                        String timezone = "";
                        
                        if (element.containsKey("id")) {
                            id = (String) element.get("id");
                        }

                        if (element.containsKey("name")) {
                            name = (String) element.get("name");
                        }
                        
                        if(element.containsKey("start_time")){
                            start_time = (String)element.get("start_time");
                        }
                        
                        if(element.containsKey("location")){
                            location = (String)element.get("location");
                        }
                        
                        if(element.containsKey("timezone")){
                            timezone = (String)element.get("timezone");
                        }

                        event = new BotEventObject();
                        event.ID = id;
                        event.Name = name;
                        event.Location = location;
                        event.StartTime = start_time;
                        event.TimeZone = timezone;
                        
                        mBotSearchListener.OnReceiving(event);
                        mResults.add(event);

                        LoggingUtils.print(element.toString());
                    }
                }

                if (jsonPage.containsKey("paging")) {
                    JSONObject paging = (JSONObject) jsonPage.get("paging");
                    if (paging.containsKey("next")) {
                        String url_next = (String) paging.get("next");
                        searchEvent(url_next);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(BotFanPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param url
     */
    private void searchFanPage(String url) {
        search(url, BotSearchObjectType.PAGE);
    }
}
