
package botvn.libraries.like;

/**
 *
 * @author vanvo
 */
public interface BotLikeListener {
    public void OnLikeProcessing(String fanpage_name);
    public void OnLiked(boolean success);
    public void OnLikeFinished();
}
