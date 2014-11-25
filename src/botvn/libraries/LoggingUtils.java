package botvn.libraries;

import java.util.Calendar;

/**
 *
 * @author vanvo
 */
public class LoggingUtils {
    
    /**
     * 
     */
    public static boolean DEBUG = true;
    public static boolean XDEBUG = true;
    
    /**
     * 
     * @return 
     */
    private static String getTimeFormat(){
        int d = Calendar.getInstance().get(Calendar.DATE);
        int m = Calendar.getInstance().get(Calendar.MONTH);
        int y = Calendar.getInstance().get(Calendar.YEAR);
        int h = Calendar.getInstance().get(Calendar.HOUR);
        int mm = Calendar.getInstance().get(Calendar.MINUTE);
        int s = Calendar.getInstance().get(Calendar.SECOND);
        //int ms = Calendar.getInstance().get(Calendar.MILLISECOND);
        return String.format("== %02d\\%02d\\%02d %02d:%02d:%02d", d, m, y, h, mm, s);
    }
    /**
     * 
     * @param debug
     * @return 
     */
    public static String print(String debug){
        String format = String.format("%s: %s\n", getTimeFormat(), debug);
        System.out.println(format);
        return format;
    }
    
    /**
     * 
     * @param title
     * @param debug
     * @return 
     */
    public static String print(String title, String debug){
        String format = String.format("%s\n%s %s\n", getTimeFormat(), title, debug);
        System.out.println(format);
        return format;
    }
}
