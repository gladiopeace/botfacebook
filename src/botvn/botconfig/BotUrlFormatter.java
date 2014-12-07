package botvn.botconfig;

/**
 *
 * @author vanvo
 */
public class BotUrlFormatter {
    /**
     * http://unicodelookup.com/
     * @param url
     * @return 
     */
    private static String escape(String url){
        url = url.replace(" ", "%20");
        return url;
    }
    
    /**
     * 
     * @param keyword
     * @return 
     */
    public static String getSearchFanPageUrl(String keyword){
        String url = String.format(BotConfig.GraphSearch.Page, keyword, BotConfig.Account.Access_token);
        return escape(url);
    }
    
    /**
     * 
     * @param keyword
     * @return 
     */
    public static String getSearchUserUrl(String keyword){
        String url = String.format(BotConfig.GraphSearch.User, keyword, BotConfig.Account.Access_token);
        return escape(url);
    }
    
    /**
     * 
     * @param keyword
     * @return 
     */
    public static String getSearchGroupUrl(String keyword){
        String url = String.format(BotConfig.GraphSearch.Group, keyword, BotConfig.Account.Access_token);
        return escape(url);
    }
    
    /**
     * 
     * @param keyword
     * @return 
     */
    public static String getSearchEventUrl(String keyword){
        String url = String.format(BotConfig.GraphSearch.Event, keyword, BotConfig.Account.Access_token);
        return escape(url);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public static String getProfileMobileUrl(String id){
        String url = String.format(BotConfig.URLProfile, id);
        return escape(url);
    }
    
    public static String getShortProfileUrl(String id){
        String url = String.format("%s%s", BotConfig.URLHome, id);
        return escape(url);
    }
    
    public static String getShortProfileUrlMobile(String id){
        String url = String.format("%s%s", BotConfig.URLHomeMobile, id);
        return escape(url);
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public static String getGroupPage(String id){
        String url = String.format(BotConfig.URLGroupProfile, id);
        return escape(url);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public static String getUrlFormComposer(String id){
        String url = String.format(BotConfig.URLComposerForm, id, 17);
        return escape(url);
    }
    
    /**
     * 
     */
    public static String getUrlReadMessaage(String id){
        String url = String.format(BotConfig.URLReadSendMessage, id);
        return escape(url);
    }
}
