package com.kockumation.backEnd.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kockumation.backEnd.dockMaster.DetectAndSaveAlarms;

import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ClientEndpoint
public class WebSocketClientTestConnection {

    public Session session;
    private ExecutorService executor;
    public DetectAndSaveAlarms detectAndSaveAlarms;


    public WebSocketClientTestConnection() {
        detectAndSaveAlarms = new DetectAndSaveAlarms();
        executor = Executors.newSingleThreadExecutor();
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;

    }

    @OnMessage
    public void onMessage(String message, Session session) throws InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(message);
            System.out.println(node);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    } // On Message


    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {

        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
        System.out.println("Error occurred.");
    }

    @OnClose
    public Future<Boolean> onClose() throws IOException {

        session = null;
        return executor.submit(() -> {



            return true;
        });
    }

    // Is Open Or Not
    public boolean isOpenOrNot(){

        return session.isOpen();

    }// Is Open Or Not

    public void closeSession() {
        try {
            session.close();
        } catch (IOException ex) {
        }
    }




}//Class LiveDataWebSocketClient
