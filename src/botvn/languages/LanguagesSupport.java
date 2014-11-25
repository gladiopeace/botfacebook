package botvn.languages;

import java.util.ResourceBundle;

/**
 *
 * @author vanvo
 */
public class LanguagesSupport {
    public enum Languages{
        ENGLISH,
        VIETNAMESE
    }
    
    /**
     * 
     * @param lang 
     * @return  
     */
    public static ResourceBundle setLanguage(Languages lang){
        String defaultLanguage = "botvn/languages/language_vi_VN"; // Vietnamese
        if(lang == Languages.ENGLISH){
            defaultLanguage = "botvn/languages/language_en_US";
        }
        ResourceBundle bundle = ResourceBundle.getBundle(defaultLanguage); // NOI18N
        return bundle;
    }
}
