/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botvn.libraries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author vanvo
 */
public class StorageCookies {

    public static final String COOKIE_FILE = ".db";
    public static final String USER_DIR  = "user.dir";
    //
    private CookieStore mCookieStore;

    public StorageCookies(CookieStore cks) {
        this.mCookieStore = cks;
    }

    public boolean Save() {
        if (mCookieStore != null) {
            FileWriter file = null;
            try {
                List<Cookie> ckss = mCookieStore.getCookies();
                HashMap<String, Object> d;
                JSONArray allCks = new JSONArray();
                for (Cookie cks : ckss) {
                    d = new HashMap<>(ckss.size());
                    String name = cks.getName();
                    long expires = cks.getExpiryDate() != null ? cks.getExpiryDate().getTime() : 0;
                    String path = cks.getPath();
                    String domain = cks.getDomain();
                    boolean secure = cks.isSecure();

                    d.put("name", name);
                    d.put("expires", expires);
                    d.put("path", path);
                    d.put("domain", domain);
                    d.put("secure", secure);
                    allCks.add(d);
                }

                String jsonStr = allCks.toJSONString();
                String currentDir = System.getProperty(USER_DIR);
                file = new FileWriter(currentDir + "/" + COOKIE_FILE);
                file.write(jsonStr);
                file.flush();
                file.close();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(StorageCookies.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return false;
    }
    
    //
    public static boolean isCookieExist(){
        String currentDir = System.getProperty(USER_DIR);
        File cookieFile = new File(currentDir + "/" + COOKIE_FILE);
        return cookieFile.exists();
    }
}
