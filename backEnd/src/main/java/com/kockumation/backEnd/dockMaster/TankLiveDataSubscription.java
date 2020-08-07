package com.kockumation.backEnd.dockMaster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kockumation.backEnd.dockMaster.model.TankSubscriptionData;
import com.kockumation.backEnd.global.Db;

import javax.websocket.*;
import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ClientEndpoint
public class TankLiveDataSubscription {
    private Session session;
    private ExecutorService executor
            = Executors.newSingleThreadExecutor();




    public TankLiveDataSubscription() {

    }



    // Check if tank Subscription Data has data
    public Future<Boolean> subscriptionExists() {

        while (true) {
            System.out.println("Waiting for list of pontoons current draft......");
            if (Db.tankSubscriptionData != null){
                if (Db.tankSubscriptionData.getSetTankSubscriptionData().size() > 0) {
                    return executor.submit(() -> {

                        return true;
                    });
                }
            }


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }// while (true)

    }  // Check if tank Subscription Data has data



    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Pontoon Subscription live Data server opened.");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) throws InterruptedException {
        //  System.out.println("Inside Tank Live Data " + Thread.currentThread());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {

            node = mapper.readTree(message);
            // System.out.println(node);
            if (node.has("setTankSubscriptionData")) {
                Gson gson = new Gson();
                Db.tankSubscriptionData = gson.fromJson(message, TankSubscriptionData.class);

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    } // OnMessage

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
    public void onClose()  {



            DetectAndSaveAlarms.timer.cancel();
            DetectAndSaveAlarms.timer.purge();
            System.out.println("Live Data WebSocket closed ");

    }

    public void closeSession() {
        try {
            session.close();
        } catch (IOException ex) {

        }
    }
} // public class TankLiveDataSubscription
