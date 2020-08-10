package com.kockumation.backEnd.utilities;

import com.kockumation.backEnd.global.FilePath;
import org.apache.tomcat.jni.Global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLJDBCUtil {
    private static String propertiesFileLocation = FilePath.getCurrentDirPath()+"\\config\\application.properties";

    private static FileInputStream fis = null;
    /**
     * Get database connection
     *
     * @return a Connection object
     * @throws SQLException
     */

    public static synchronized Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            fis = new FileInputStream(propertiesFileLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Configure mysql timezone to work with UTC time zone
    // SET GLOBAL time_zone = '+00:00';
        try  {
            Properties props = new Properties();
            //load a properties file from file location.
            props.load(fis);
            // assign db parameters
            String url = props.getProperty("url");
           // System.out.println("url is : "+url);
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }








        /*  try {
            // db parameters
            String url       = pros.getProperty("url");;
            String user      = "root";
            String password  = "secret";

            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
            // more processing here
            // ...
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;*/



}
