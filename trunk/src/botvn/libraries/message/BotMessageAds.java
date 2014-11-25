package botvn.libraries.message;

import botvn.HackUtils;
import botvn.HttpUtil;
import botvn.Response;
import botvn.botconfig.BotConfig;
import botvn.botconfig.BotMessageObject;
import botvn.libraries.BotBase;
import botvn.libraries.LoggingUtils;
import botvn.libraries.LoginInfo;
import botvn.libraries.search.BotSearchObject;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vanvo
 */
public class BotMessageAds extends BotBase implements Runnable{
    /**
     * 
     */
    private Thread mThread;
    
    /**
     * 
     */
    private LoginInfo mLoginInfo;
    
    /**
     * For one user
     */
    private String mMessage;
    private String mReceiver;

    /**
     * For multi users
     */
    private List<BotMessageObject> mMessages;
    private List<BotSearchObject> mReceivers;
    
    /**
     * 
     */
    private BotMessageAdsListener mMessageListener;
    
    /**
     * 
     * @param loginInfo 
     */
    public BotMessageAds(LoginInfo loginInfo){
        mLoginInfo = loginInfo;
    }
    
    /**
     * Set argument for a user
     * 
     * @param message
     * @param receiver 
     */
    public void setMessage(String message, String receiver){
        mMessage = message;
        mReceiver = receiver;
    }
    
    /**
     * Set argument for multi user
     * 
     * @param messages
     * @param receivers 
     */
    public void setMessages(List<BotMessageObject> messages, List<BotSearchObject> receivers){
        mMessages = messages;
        mReceivers = receivers;
    }
    
    /**
     * 
     * @param listener 
     */
    public void setBotMessageListener(BotMessageAdsListener listener){
        mMessageListener = listener;
    }
    
    /**
     * 
     * @throws java.io.IOException 
     */
    private String Send(String message, String receiver) throws IOException
    {
        LoggingUtils.print(String.format("Sending message to: %s", receiver));
        
        HashMap<String, String> args = new HashMap<>();
        args.put("__a", "1");
        args.put("__user", mLoginInfo.userId);
        args.put("fb_dtsg", mLoginInfo.fbdtsg);
        args.put("message_batch[0][action_type]", "ma-type%3Auser-generated-message");
        args.put("message_batch[0][thread_id]", "");
        args.put("message_batch[0][author]", String.format("fbid:%s", mLoginInfo.userId));
        args.put("message_batch[0][author_email]", "");
        args.put("message_batch[0][coordinates]", "");
        args.put("message_batch[0][timestamp]", String.format("%s", new Date().getTime()));
        args.put("message_batch[0][timestamp_absolute]", "Today");
        //args.put("message_batch[0][timestamp_relative]=9%3A33pm
        args.put("message_batch[0][timestamp_time_passed]", "0");
        args.put("message_batch[0][is_unread]", "false");
        args.put("message_batch[0][is_cleared]", "false");
        args.put("message_batch[0][is_forward]", "false");
        args.put("message_batch[0][is_filtered_content]", "false");
        args.put("message_batch[0][is_spoof_warning]", "false");
        args.put("message_batch[0][source]", "source%3Atitan%3Aweb");
        args.put("message_batch[0][body]", message);
        args.put("message_batch[0][has_attachment]", "false");
        args.put("message_batch[0][html_body]", "true");
        args.put("message_batch[0][specific_to_list][0]", String.format("fbid:%s", receiver));
        args.put("message_batch[0][specific_to_list][1]", String.format("fbid:%s", mLoginInfo.userId));
        args.put("message_batch[0][force_sms]", "true");
        args.put("message_batch[0][ui_push_phase]", "V3");
        args.put("message_batch[0][status]", "0");
        //args.put("message_batch[0][message_id]=%3C1414855379%3A2492850558-1088837342%40mail.projektitan.com%3E
        args.put("message_batch[0][auto_retry_cnt]", "0");
        args.put("message_batch[0][manual_retry_cnt]", "0");
        args.put("message_batch[0][client_thread_id]", String.format("user:%s", receiver));
        args.put("client", "mercury");
        args.put("ttstamp", HackUtils.generatettstamp(mLoginInfo.fbdtsg));
        
        Response response = HttpUtil.postLocal(BotConfig.BotMessage.Url, args, mLoginCookies);
        //LoggingUtils.print(String.format("%s%s%s", StringUtil.repeat("=", 5), "SEND MESSAGE", StringUtil.repeat("=", 5)));
        //LoggingUtils.print(response.getStatusLine().toString());
        return response.getHtml();
    }

    @Override
    public void run() {
        try {
            String results = Send(mMessage, mReceiver);
            LoggingUtils.print(results);
            boolean success = isSuccess(results);
            if(mMessageListener != null){
                mMessageListener.OnMessageSend(success, success ? mReceiver : null);
            }
        } catch (IOException ex) {
            Logger.getLogger(BotMessageAds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     */
    public void start(){
        mThread = new Thread(this, this.getClass().getName());
        mThread.start();
    }
    
    public void startMultiUser(){
        mThread = new Thread(new MultiUser(), this.getClass().getName());
        mThread.start();
    }
    
    /**
     * 
     */
    class MultiUser implements Runnable{

        @Override
        public void run() {
            try {
                Random rad = new Random();
                LoggingUtils.print("Sending...");
                for (BotSearchObject receiver : mReceivers) {
                    if(mMessageListener != null){
                        mMessageListener.OnMessageProcessing(receiver.Name);
                    }
                    rad.setSeed(System.currentTimeMillis());
                    Thread.sleep(rad.nextInt(10) * 1000);
                    // get random message
                    int messageIndex = rad.nextInt(mMessages.size() - 1);
                    BotMessageObject message = mMessages.get(messageIndex);
                    String msg = message.toString();
                    if(msg.contains("@")){
                        msg = message.toString().replace("@", receiver.Name);
                    }
                    String results = Send(msg, receiver.ID);
                    LoggingUtils.print(results);
                    boolean success = isSuccess( results);
                    if(mMessageListener != null){
                        mMessageListener.OnMessageSend(success, success ? receiver.Name : null);
                    }
                    Thread.sleep(rad.nextInt(30) * 1000);
                }
                
                if(mMessageListener != null){
                    mMessageListener.OnMessageSendFinished();
                }
                LoggingUtils.print("DONE");
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(BotMessageAds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
