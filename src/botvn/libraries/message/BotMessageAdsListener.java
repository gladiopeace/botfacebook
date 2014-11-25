package botvn.libraries.message;

/**
 *
 * @author vanvo
 */
public interface BotMessageAdsListener {
    public void OnMessageProcessing(String receiver);
    public void OnMessageSend(boolean isSuccess, String received);
    public void OnMessageSendFinished();
}
