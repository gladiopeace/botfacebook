package botvn.libraries.search.event;

import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.BotSearchObjectType;

/**
 *
 * @author vanvo
 */
public final class BotEventObject extends BotSearchObject{
    public String StartTime;
    public String Location;
    public String TimeZone; //option

    /**
     * 
     */
    public BotEventObject() {
        Type = BotSearchObjectType.EVENT;
    }
    
}
