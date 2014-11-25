package botvn.libraries;

import java.util.List;

/**
 *
 * @author vanvo
 * @param <T>
 */
public interface BotEventListener<T> {
    /**
     * 
     * @param object 
     */
    public void OnReceiving(T object);
    
    /**
     * 
     * @param results 
     */
    public void OnReceveFinished(List<T> results);
}
