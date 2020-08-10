package com.kockumation.backEnd;


import com.kockumation.backEnd.dockMaster.DockMasterEngine;
import com.kockumation.backEnd.global.Db;
import com.kockumation.backEnd.global.FilePath;
import com.kockumation.backEnd.utilities.WebSocketClientTestConnection;
import com.kockumation.backEnd.utilities.MySQLJDBCUtil;
import com.kockumation.backEnd.utilities.PontoonInformations;
import org.glassfish.tyrus.client.ClientManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.websocket.DeploymentException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.sql.Connection;
import java.sql.SQLException;


@SpringBootApplication
public class BackEndApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BackEndApplication.class, args);
        System.out.println("listening on port " + 8081);


        WebSocketClientTestConnection webSocketClient = new WebSocketClientTestConnection();
        PontoonInformations pontoonInformations = new PontoonInformations();
        DockMasterEngine dockMasterEngine;
        ClientManager client = ClientManager.createClient();
        boolean checkDataBase = true;

        while (checkDataBase) {

            try (Connection conn = MySQLJDBCUtil.getConnection()) {
                checkDataBase = false;

                System.out.println(String.format("Connected to database %s "
                        + "successfully.", conn.getCatalog()));

                // Try connecting to web socket
                while (true) {
                    try {
                        if (webSocketClient.session == null) {
                            try {
                                client.connectToServer(webSocketClient, new URI(FilePath.getLocalUri()));
                                System.out.println("Web Socket is Opened");

                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }

                            dockMasterEngine = new DockMasterEngine();
                            dockMasterEngine.start();

                        }

                    } catch (DeploymentException e) {

                        dockMasterEngine = null;

                        	Db.pontoonMapData.clear();


                        System.out.println("Live Data Web Socket not ready start web Socket server.");
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } // While true


            } catch (SQLException ex) {
                //System.out.println(ex.getMessage());
                switch (ex.getErrorCode()) {
                    case 1049:
                        System.out.println("Please install database ship_master_java.");
                        break;
                    case 1045:
                        System.out.println("Please change database user to root and password to tyfon");
                        break;
                }
                try {
                    Thread.sleep(4000);
                    checkDataBase = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        } //while (checkDataBase)


    }


}
