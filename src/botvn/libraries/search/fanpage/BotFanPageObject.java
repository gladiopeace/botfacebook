package botvn.libraries.search.fanpage;

import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.BotSearchObjectType;

/**
 *
 * @author vanvo
 */
public final class BotFanPageObject extends BotSearchObject{
    public int Likes;

    /**
     * 
     */
    public BotFanPageObject() {
        Type = BotSearchObjectType.PAGE;
    }
    
}
