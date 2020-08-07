package com.kockumation.backEnd.controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
@ServerEndpoint(value = "/getPontoonInfo")
public class ServerEndPoint {
    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("onOpen");
    }
       @OnMessage
       public void echo(String message, Session session) throws IOException {
           System.out.println(message);
        if (message.equalsIgnoreCase("meamo")){
             session.getBasicRemote().sendText(" (Meamo)");



        }else if (message.equalsIgnoreCase("stop")){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    @OnClose
    public void onClose(Session session) {
        System.out.println("Server Closed");
    }


}
