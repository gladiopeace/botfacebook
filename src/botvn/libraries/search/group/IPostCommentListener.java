package botvn.libraries.search.group;

/**
 *
 * @author vanvo
 */
public interface IPostCommentListener {
    public void OnComment(String post_url);
    public void OnFinished();
}
