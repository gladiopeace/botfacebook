package botvn.botconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vanvo
 */
public final class BotMessageTemplate {

    /**
     *
     * @param message
     */
    public static void Append(long ttstamp, String message) {
        String jsonRaw = BotConfigLocal.read(BotConfigLocal.getMessageFile());
        if (jsonRaw == null) {
            return;
        }
        if (!jsonRaw.isEmpty()) {
            // Append
            try {
                JSONObject json = (JSONObject) (new JSONParser().parse(jsonRaw));
                if (json.containsKey("messages")) {
                    JSONArray messages = (JSONArray) json.get("messages");

                    JSONObject key = new JSONObject();
                    key.put(ttstamp, message);

                    messages.add(key);

                    JSONObject root = new JSONObject();
                    root.put("messages", messages);
                    BotConfigLocal.write(BotConfigLocal.getMessageFile(), root.toJSONString());
                }
            } catch (ParseException ex) {
                Logger.getLogger(BotMessageTemplate.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            JSONObject key = new JSONObject();
            key.put(System.currentTimeMillis(), message);

            JSONArray messages = new JSONArray();
            messages.add(key);

            JSONObject root = new JSONObject();
            root.put("messages", messages);

            String jsonToWrite = root.toJSONString();
            BotConfigLocal.write(BotConfigLocal.getMessageFile(), jsonToWrite);
        }
    }

    /**
     * 
     * @return 
     */
    public static List<BotMessageObject> toList() {
        List<BotMessageObject> tmp = new ArrayList<>();
        String jsonRaw = BotConfigLocal.read(BotConfigLocal.getMessageFile());
        if (jsonRaw == null || jsonRaw.length() == 0) {
            return null;
        }
        try {
            JSONObject json = (JSONObject) (new JSONParser().parse(jsonRaw));
            if (json.containsKey("messages")) {
                JSONArray messages = (JSONArray) json.get("messages");
                for (Object message : messages) {
                    if(message instanceof JSONObject){
                        JSONObject j = (JSONObject)message;
                        String[] v = j.entrySet().iterator().next().toString().split("=");
                        tmp.add(new BotMessageObject(v[0], v[1]));
                    }
                }
                return tmp;
            }
        } catch (ParseException ex) {
            Logger.getLogger(BotMessageTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param key 
     */
    public static void RemoveMessage(String key){
        String jsonRaw = BotConfigLocal.read(BotConfigLocal.getMessageFile());
        if (jsonRaw == null || jsonRaw.length() == 0) {
            return;
        }
        try {
            JSONObject json = (JSONObject) (new JSONParser().parse(jsonRaw));
            if (json.containsKey("messages")) {
                JSONArray messages = (JSONArray) json.get("messages");
                int index = -1;
                for(int i = 0; i< messages.size(); i++){
                    JSONObject j = (JSONObject)messages.get(i);
                    String[] v = j.entrySet().iterator().next().toString().split("=");
                        if(key.equals(v[0])){
                            index = i;
                        }
                }
                if(index != -1){
                    messages.remove(index);
                    // Write file again
                    JSONObject root = new JSONObject();
                    root.put("messages", messages);
                    BotConfigLocal.write(BotConfigLocal.getMessageFile(), root.toJSONString());
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(BotMessageTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param key 
     */
    public static void Update(String key, String message){
        String jsonRaw = BotConfigLocal.read(BotConfigLocal.getMessageFile());
        if (jsonRaw == null || jsonRaw.length() == 0) {
            return;
        }
        try {
            JSONObject json = (JSONObject) (new JSONParser().parse(jsonRaw));
            if (json.containsKey("messages")) {
                JSONArray messages = (JSONArray) json.get("messages");
                for (Object message1 : messages) {
                    JSONObject j = (JSONObject) message1;
                    String[] v = j.entrySet().iterator().next().toString().split("=");
                    if(key.equals(v[0])){
                        j.replace(key, message);
                        JSONObject root = new JSONObject();
                        root.put("messages", messages);
                        BotConfigLocal.write(BotConfigLocal.getMessageFile(), root.toJSONString());
                        break;
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(BotMessageTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
