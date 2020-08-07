package com.kockumation.backEnd.service;

import com.kockumation.backEnd.controller.ServerEndPoint;
import org.glassfish.tyrus.server.Server;


import javax.websocket.DeploymentException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebSocketServer {


    public  void runServer() {

        Server server = new Server("localhost", 8082, "/websockets", null, ServerEndPoint.class);
        System.out.println("Websocket server running on port 8082");
        try {
            server.start();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }

      /*  try {

            server.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Please press a key to stop the server.");

            reader.readLine();

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            server.stop();

        }*/

    }

}
