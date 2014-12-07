package botvn.botconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vanvo
 */
public final class BotConfigLocal {

    public static String USER_DIR = "user.dir";
    private static String CONFIG_FILE = "%s/data/config.db";
    private static String MESSAGE_FILE = "%s/data/messages.db";
    private static String DATA_FOLDER = "%s/data";

    /**
     *
     */
    private static FileOutputStream sFileWriter = null;

    /**
     *
     */
    private static FileInputStream sFileReader = null;

    /**
     *
     * @return
     */
    public static String getCurrentDir() {
        return System.getProperty(USER_DIR);
    }

    /**
     *
     * @return
     */
    public static boolean createDataFolder() {
        if (!isDataFolderExist()) {
            return new File(String.format(DATA_FOLDER, getCurrentDir())).mkdir();
        }
        return false;
    }

    /**
     *
     * @return
     */
    public static boolean isDataFolderExist() {
        return new File(String.format(DATA_FOLDER, getCurrentDir())).exists();
    }

    /**
     *
     * @param filepath
     * @return
     */
    public static boolean createFile(String filepath) {
        createDataFolder();
        File config = new File(filepath);
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(BotConfigLocal.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @param filepath
     * @return
     */
    public static boolean isFileExist(String filepath) {
        File config = new File(filepath);
        return config.exists();
    }

    /**
     *
     * @return
     */
    public static String getConfigFile() {
        return String.format(CONFIG_FILE, getCurrentDir());
    }

    /**
     *
     * @return
     */
    public static String getMessageFile() {
        return String.format(MESSAGE_FILE, getCurrentDir());
    }

    /**
     *
     * @param filepath
     * @param jsonString
     * @return
     */
    public static boolean write(String filepath, String jsonString) {
        try {
            sFileWriter = new FileOutputStream(filepath);
            try (OutputStreamWriter write = new OutputStreamWriter(sFileWriter, "UTF-8")) {
                write.write(jsonString);
            }
            sFileWriter.flush();
            sFileWriter.close();
            sFileReader = null;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(BotConfigLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param filepath
     * @return
     */
    public static String read(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BotConfigLocal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BotConfigLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
