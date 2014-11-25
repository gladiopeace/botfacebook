package botvn.libraries.search.fanpage;

import botvn.HttpUtil;
import botvn.Response;
import botvn.botconfig.BotUrlFormatter;
import botvn.libraries.BotBase;
import botvn.libraries.BotEventListener;
import botvn.libraries.LoggingUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class BotFanPage extends BotBase implements Runnable {
    
    /**
     *
     */
    private Thread mThread;

    /**
     * Contain the results of search
     */
    private List<BotFanPageObject> mResults;

    /**
     * 
     */
    private BotEventListener<BotFanPageObject> mBotFanPageListener;
    
    /**
     * 
     */
    private int mCount = 0;
    
    /**
     *
     * @param listener
     */
    public BotFanPage(BotEventListener listener) {
        mBotFanPageListener = listener;
        if (mResults == null) {
            mResults = new ArrayList<>();
        }
    }

    @Override
    public void run() {
        fetchFanPage(BotUrlFormatter.getSearchFanPageUrl(mKeyword));
        mBotFanPageListener.OnReceveFinished(mResults);
    }

    /**
     *
     */
    public void start() throws IllegalThreadStateException {
        if (mThread == null) {
            mThread = new Thread(this, this.getClass().getName());
            mThread.start();
        }
    }

    /**
     *
     * @param url
     */
    private void fetchFanPage(String url) {
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
                    BotFanPageObject fanpage = null;
                    while (i.hasNext()) {
                        JSONObject element = (JSONObject) i.next();
                        String id = "", name = "";
                        if (element.containsKey("id")) {
                            id = (String) element.get("id");
                        }

                        if (element.containsKey("name")) {
                            name = (String) element.get("name");
                        }
                        
                        fanpage = new BotFanPageObject();
                        fanpage.ID = id;
                        fanpage.Name = name;
                        
                        mBotFanPageListener.OnReceiving(fanpage);
                        mResults.add(fanpage);
                        
                        LoggingUtils.print(element.toString());
                    }
                }

                if (jsonPage.containsKey("paging")) {
                    JSONObject paging = (JSONObject) jsonPage.get("paging");
                    if (paging.containsKey("next")) {
                        String url_next = (String) paging.get("next");
                        fetchFanPage(url_next);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BotFanPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BotFanPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void findLikes(){
        
    }

}
