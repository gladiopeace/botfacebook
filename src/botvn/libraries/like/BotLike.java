package botvn.libraries.like;

import botvn.HackUtils;
import botvn.HttpUtil;
import botvn.Response;
import botvn.botconfig.BotConfig;
import botvn.libraries.BotBase;
import botvn.libraries.LoggingUtils;
import botvn.libraries.LoginInfo;
import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.BotSearchObjectType;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vanvo
 */
public class BotLike extends BotBase implements Runnable {

    /**
     *
     */
    private Thread mThread;

    /**
     *
     */
    private LoginInfo mLoginInfo;

    /**
     *
     */
    private List<BotSearchObject> mListPage;

    /**
     *
     */
    private BotLikeListener mLikeListener;

    /**
     *
     * @param loginInfo
     */
    public BotLike(LoginInfo loginInfo) {
        mLoginInfo = loginInfo;
    }

    /**
     *
     * @param listPage
     */
    public void setListPage(List<BotSearchObject> listPage) {
        mListPage = listPage;
    }

    /**
     *
     * @param listener
     */
    public void setLikeListener(BotLikeListener listener) {
        mLikeListener = listener;
    }

    /**
     *
     */
    @Override
    public void run() {
        Random rad = new Random();
        for (BotSearchObject obj : mListPage) {
            if (obj.Type == BotSearchObjectType.PAGE && obj.IsSelected) {
                try {
                    if (mLikeListener != null) {
                        mLikeListener.OnLikeProcessing(obj.Name);
                    }
                    String results = Like(obj.ID);
                    LoggingUtils.print(results);
                    if (isSuccess(results)) {
                        if (mLikeListener != null) {
                            mLikeListener.OnLiked(true);
                        }
                    } else {
                        if (mLikeListener != null) {
                            mLikeListener.OnLiked(false);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(BotLike.class.getName()).log(Level.SEVERE, null, ex);
                }
                rad.setSeed(System.currentTimeMillis());
                try {
                    Thread.sleep(rad.nextInt(rad.nextInt(45) * 1000));
                } catch (InterruptedException ex) {
                    Logger.getLogger(BotLike.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (mLikeListener != null) {
            mLikeListener.OnLikeFinished();
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    private String Like(String id) throws IOException {
        LoggingUtils.print(String.format("Like: %s", id));

        HashMap<String, String> args = new HashMap<>();
        args.put("__a", "1");
        args.put("__user", mLoginInfo.userId);
        args.put("fb_dtsg", mLoginInfo.fbdtsg);
        args.put("ttstamp", HackUtils.generatettstamp(mLoginInfo.fbdtsg));
        args.put("fbpage_id", id);
        args.put("add", "true");
        args.put("reload", "false");
        args.put("fan_origin", "page_timeline");
        args.put("fan_source", "");
        args.put("cat", "");
        Response response = HttpUtil.postLocal(BotConfig.URLLikeFanPage, args, mLoginCookies);
        return response.getHtml();
    }

    /**
     *
     */
    public void RunLike() {
        mThread = new Thread(this, this.getClass().getName());
        mThread.start();
    }
}
