package botvn.botconfig;

/**
 *
 * @author vanvo
 */
public class BotMessageObject {
    private String key;
    private String value;
    public boolean IsSelected;

    public BotMessageObject(String k, String v) {
        key = k;
        value = v;
    }
    
    @Override
    public String toString() {
        return value != null ? value : "";
    }
    
    public String getKey(){
        return key;
    }
}
