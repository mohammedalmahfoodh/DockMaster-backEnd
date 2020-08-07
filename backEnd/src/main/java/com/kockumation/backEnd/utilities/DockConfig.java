package com.kockumation.backEnd.utilities;

import com.kockumation.backEnd.global.FilePath;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DockConfig {


    public static String getPontoonInfoString() {
        File file = null;
        String fileLocation = FilePath.getCurrentDirPath()+"\\config\\Pontoon information.json";
        file = new File(fileLocation);

//Read File Content
        String content = null;
        try {
            content = new String(Files.readAllBytes(file.toPath()));
          //  System.out.println(content);

        } catch (IOException e) {
        //    e.printStackTrace();
            Scanner in = new Scanner(System.in);
            System.out.println("******************************************** OOPS **********************************************************");
            System.out.println("No config file please place it inside config folder inside the root folder and restart ");
            String s = in.nextLine();
            System.out.println("No config file please place it in the inside config folder inside the root folder and restart ");

        }
        // System.out.println(content);
        return content;
    }


}
