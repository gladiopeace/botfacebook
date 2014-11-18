/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botvn.libraries;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author vanvo
 */
public class Log {
    
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
    public static String Log(String debug){
        
        String format = String.format("%s: %s\n", getTimeFormat(), debug);
        return format;
    }
    
    /**
     * 
     * @param title
     * @param debug
     * @return 
     */
    public static String Log(String title, String debug){
        String format = String.format("%s\n%s %s\n", getTimeFormat(), title, debug);
        return format;
    }
}
