/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botvn.libraries;

import botvn.HackUtils;
import botvn.HttpUtil;
import botvn.Response;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import jodd.util.StringUtil;
import org.apache.http.client.CookieStore;

/**
 *
 * @author vanvo
 */
public class MessageAds {
    private String fb_dtsg;
    private CookieStore cks;
    
    /**
     * 
     * @param fb_dtsg
     * @param cks 
     */
    public MessageAds(String fb_dtsg, CookieStore cks){
        this.fb_dtsg = fb_dtsg;
        this.cks = cks;
    }
    
    /**
     * 
     * @param message
     * @param fbUserID
     * @param fbReceiver 
     */
    public void Send(String message, String fbUserID, String fbReceiver) throws IOException
    {
        System.out.println(String.format("Sending message to: %s", fbReceiver));
        
        HashMap<String, String> args = new HashMap<>();
        args.put("__a", "1");
        args.put("__user", fbUserID);
        args.put("fb_dtsg", this.fb_dtsg);
        args.put("message_batch[0][action_type]", "ma-type%3Auser-generated-message");
        args.put("message_batch[0][thread_id]", "");
        args.put("message_batch[0][author]", String.format("fbid:%s", fbUserID));
        args.put("message_batch[0][author_email]", "");
        args.put("message_batch[0][coordinates]", "");
        args.put("message_batch[0][timestamp]", String.format("%s", new Date().getTime()));
        //args.put("message_batch[0][timestamp_absolute]:Today
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
        args.put("message_batch[0][specific_to_list][0]", String.format("fbid:%s", fbReceiver));
        args.put("message_batch[0][specific_to_list][1]", String.format("fbid:%s", fbUserID));
        args.put("message_batch[0][force_sms]", "true");
        args.put("message_batch[0][ui_push_phase]", "V3");
        args.put("message_batch[0][status]", "0");
        //args.put("message_batch[0][message_id]=%3C1414855379%3A2492850558-1088837342%40mail.projektitan.com%3E
        args.put("message_batch[0][auto_retry_cnt]", "0");
        args.put("message_batch[0][manual_retry_cnt]", "0");
        args.put("message_batch[0][client_thread_id]", String.format("user:%s", fbReceiver));
        //args.put("client", "mercury");
        args.put("ttstamp", HackUtils.generatettstamp(this.fb_dtsg));
        
        String url = "https://www.facebook.com/ajax/mercury/send_messages.php";
        
        Response response = HttpUtil.postLocal(url, args, this.cks);
        System.out.println(String.format("%s%s%s", StringUtil.repeat("=", 5), "SEND MESSAGE", StringUtil.repeat("=", 5)));
        System.out.println(response.getStatusLine());
        System.out.println(response.getHtml());
    }
}
