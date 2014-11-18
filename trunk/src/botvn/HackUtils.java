/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botvn;

/**
 *
 * @author vanvo
 */
public class HackUtils {
    public static String generatettstamp(String fb_dtsg){
        String genStr = "";
        for(int i = 0; i < fb_dtsg.length(); i++){
            genStr += String.format("%s", (int)fb_dtsg.charAt(i));
        }
        return '2' + genStr;
    }
}
