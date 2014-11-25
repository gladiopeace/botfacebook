package botvn.libraries.search;

/**
 *
 * @author vanvo
 */
public enum BotSearchObjectType {
    USER(1),
    PAGE(2),
    EVENT(4),
    GROUP(8),
    ALL(16);
    private final int value;
    /**
     * 
     * @param value 
     */
    private BotSearchObjectType(int value){
        this.value = value;
    }
    /**
     * 
     * @return 
     */
    public int getValue(){
        return value;
    }
}
