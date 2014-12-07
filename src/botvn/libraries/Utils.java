package botvn.libraries;

import java.util.Random;

/**
 *
 * @author vanvo
 */
public class Utils {
    /**
     * 
     * @param min
     * @param max
     * @return 
     */
    public static int randSec(int min, int max){
        Random rad = new Random();
        int radNum = rad.nextInt((max - min) + 1) + min;
        return radNum * 1000;
    }
}
