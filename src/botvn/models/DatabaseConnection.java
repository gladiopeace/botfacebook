package botvn.models;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private String serverURL;
    private int port;
    private String dbName;
    private String username;
    private String pwd;
    private Connection con;
    private PreparedStatement ps;
    private Properties prop = new Properties();
    private InputStream input = null;

    public DatabaseConnection() {

        try {

//            input = new FileInputStream("config.properties");
//
//            // load a properties file
//            prop.load(input);
//
//            // get the property value and print it out
//            serverURL = prop.getProperty("ServerURL");
//            dbName = prop.getProperty("DatabaseName");
//            port = Integer.valueOf(prop.getProperty("Port"));
//            username = prop.getProperty("Username");
//            pwd = prop.getProperty("Password");
            
            
            
            // get the property value and print it out
            serverURL = "112.213.89.2";
            dbName = "botvn_wp";
            port = 3306;
            username = "botvn_skypeads";
            pwd = "123456";


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "File config not exist", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("jdbc:mysql://" + serverURL + ":" + port + "/" + dbName);
            con = DriverManager.getConnection("jdbc:mysql://" + serverURL + ":" + port + "/" + dbName, username, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql, Object[] params) throws SQLException {
//        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean executeUpdate(String sql, Object[] params) throws SQLException {
        int r = -1;
//        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            r = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (r > 0) {
            return true;
        }
        return false;
    }

    public void closeConnection() {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
