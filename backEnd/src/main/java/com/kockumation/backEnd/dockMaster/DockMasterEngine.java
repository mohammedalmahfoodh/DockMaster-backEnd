package com.kockumation.backEnd.dockMaster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kockumation.backEnd.dockMaster.model.TankDataForMap;
import com.kockumation.backEnd.global.Db;
import com.kockumation.backEnd.global.FilePath;
import com.kockumation.backEnd.utilities.DockConfig;
import com.kockumation.backEnd.utilities.PontoonInfo;
import com.kockumation.backEnd.utilities.PontoonInformations;
import org.glassfish.tyrus.client.ClientManager;
import org.json.simple.JSONObject;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DockMasterEngine extends Thread {

    public static Map<Integer, TankDataForMap> tankMapData = new HashMap<Integer, TankDataForMap>();

    public DetectAndSaveAlarms detectAndSaveAlarms;
    ClientManager client = ClientManager.createClient();
    TankLiveDataSubscription tankLiveDataSubscription;
    PontoonInformations pontoonInformations;
    private final String uri = FilePath.getLocalUri();
    private InitiateDataBase initiateDataBase;

    public DockMasterEngine() {
        tankLiveDataSubscription = new TankLiveDataSubscription();
        pontoonInformations = new PontoonInformations();
        detectAndSaveAlarms = new DetectAndSaveAlarms();
        initiateDataBase = new InitiateDataBase();
    }

    @Override
    public void run() {
        dockMasterEngine();
    }

    // Dock Master Engine method *******************  Dock Master Engine method *******************************
    public void dockMasterEngine() {

        // Subscribe to tanks live data   ************** Subscribe to tanks live data **********************
        try {

            JSONObject tankSubscription = new JSONObject();
            JSONObject tankId = new JSONObject();
            tankId.put("tankId", 0);
            tankSubscription.put("setTankSubscriptionOn", tankId);

            String tankSubscriptionStr = tankSubscription.toString();
            client.connectToServer(tankLiveDataSubscription, new URI(uri));
            tankLiveDataSubscription.sendMessage(tankSubscriptionStr);

            try {
                boolean mapPopulate = Db.populatePontoonMapData().get();
                boolean ifDataExistsInPontoons = false;
                if (mapPopulate) {
                     ifDataExistsInPontoons = initiateDataBase.checkIfDataExists().get();
                }

                boolean inserted = false;
                if (ifDataExistsInPontoons) {
                    inserted = initiateDataBase.updatePontoonsIntoDB().get();
                } else {
                    inserted = initiateDataBase.insertPontoonsIntoDB().get();
                }
                if (inserted) {
                    boolean checkIfSubscriptionOk = tankLiveDataSubscription.subscriptionExists().get();
                    if (checkIfSubscriptionOk) {
                        detectAndSaveAlarms.createNewAlarmDetector();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }




        } catch (DeploymentException e) {
            System.out.println("Live Data web socket not ready start web socket server.");

            //  e.printStackTrace();
        } catch (IOException e) {
            //   e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Subscribe to tanks live data   ************** Subscribe to tanks live data **********************


    }// Dock Master Engine method *******************  Dock Master Engine method *******************************


}
