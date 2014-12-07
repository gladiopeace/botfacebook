package botvn.botconfig;

/**
 *
 * @author vanvo
 */
public final class BotConfig{
    public static String URLHome;
    public static String URLHomeMobile;
    public static String URLProfile;
    public static String URLShortAccessToken;
    public static String URLLongAccessToken;
    public static String URLFriends;
    public static String URLLikeFanPage;
    public static String URLLogin;
    public static String URLComposerForm;
    public static String URLSendMessage;
    public static String URLReadSendMessage;
    public static String URLGroupProfile;
    public static String URLSearchForm;
    public static String UrlSeeMoreGroups;
    
    /**
     * 
     */
    public static class Account{
        public static String Email;
        public static String Pwd;
        public static String Access_token; // Expires in 2 months
    }
    
    /*
     *
     */
    public static class GraphSearch{
        public static String Page;
        public static String Group;
        public static String User;
        public static String Event;
        public static String Place;
    }
    
    /**
     * 
     */
    public static class BotMessage{
        public static String Url;
    }
}
