package com.kockumation.backEnd;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
@ClientEndpoint
public class TestWebsocketClient {
    private final String uri="ws://194.103.55.106:8089";
    private Session session;


    public TestWebsocketClient(){

        try{
            WebSocketContainer container= ContainerProvider.
                    getWebSocketContainer();
            container.connectToServer(this, new URI(uri));

        }catch(Exception ex){

        }
    }

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        System.out.println("Connection opened.");
    }

    @OnMessage
    public void onMessage(String message, Session session){
        //  clientWindow.writeServerMessage(message);
        System.out.println("Message received.");
        System.out.println(message);

    }
    public void sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {

        }
    }

}
