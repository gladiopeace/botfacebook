package botvn.libraries.search;

/**
 *
 * @author vanvo
 */
public class BotSearchObject {
    public boolean IsSelected;
    public String ID;
    public String Name;
    public BotSearchObjectType Type;
    public String URL;

    @Override
    public String toString() {
        return Name;
    }
    
    
}
