package com.kockumation.backEnd.global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FilePath {

  // Get current Directory path .
    public static String getCurrentDirPath(){
        Path currentRelativePath = Paths.get("");
        String currentDirPath = currentRelativePath.toAbsolutePath().toString();
        return  currentDirPath;
    }// Get current Directory path .

    // Get local uri for web socket server from application.properties located in config folder  ************
    public static String getLocalUri(){

        String fileLocation =getCurrentDirPath()+"\\config\\application.properties";
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileLocation);
        } catch (FileNotFoundException e) {

            System.out.println("Config file not found in \\Kockum Sonics\\ShipMaster-backEnd\\config");
        }
        prop = new Properties();
        try {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String uriLocalIp = prop.getProperty("uriLocalIp");
        return uriLocalIp;
    } // Get local uri for web socket server from application.properties located in config folder  ************


}
