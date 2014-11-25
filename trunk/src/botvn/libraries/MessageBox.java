package botvn.libraries;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author vanvo
 */
public class MessageBox {
    
    public enum Buttons{
        OK, 
        YES,
        NO,
        CANCEL,
    }
    
    public static void Show(Component parent, String message, String title, Buttons buttons){
        Object[] options = getButtons(buttons);
        JOptionPane.showOptionDialog(parent, 
                message, 
                title, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.WARNING_MESSAGE, 
                null,
                options, 
                options[0]);
    }
    
    private static Object[] getButtons(Buttons buttons){
        Object[] options = {"OK"};

        return options;
    }
}
