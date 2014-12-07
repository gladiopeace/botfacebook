package botvn.libraries.search.group;

import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.BotSearchObjectType;

/**
 *
 * @author vanvo
 */
public class BotGroupObject extends BotSearchObject{

    /**
     * 
     */
    public BotGroupObject() {
        Type = BotSearchObjectType.GROUP;
    }
    
    public int Members;

    @Override
    public String toString() {
        return Name;
    }
    
    
}
