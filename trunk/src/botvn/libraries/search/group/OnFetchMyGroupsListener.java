package botvn.libraries.search.group;

import java.util.List;

/**
 *
 * @author vanvo
 */
public interface OnFetchMyGroupsListener {
    public void OnFetchGroup(BotGroupObject obj);
    public void OnFetchGroupError();
    public void OnFetchFinished(List<BotGroupObject> list);
}
