package com.kockumation.backEnd.global;

import com.kockumation.backEnd.dockMaster.model.PontoonDataForMap;
import com.kockumation.backEnd.dockMaster.model.TankDataForMap;
import com.kockumation.backEnd.dockMaster.model.TankSubscriptionData;
import com.kockumation.backEnd.utilities.PontoonInfo;
import com.kockumation.backEnd.utilities.PontoonInformations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Db {
    public static Map<Integer, PontoonDataForMap> pontoonMapData = new HashMap<>();

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    // Object contains list of tanks live data such as level,volume
    public static TankSubscriptionData tankSubscriptionData;

    //List of pontoons configuration.
    public static PontoonInformations pontoonInformations;


    public static void printSubscriptionList() {
        Db.tankSubscriptionData.getSetTankSubscriptionData().stream().forEach(tankAlarmData -> {
            System.out.println(tankAlarmData);
        });
    }

    public static int getListSize() {

        return tankSubscriptionData.getSetTankSubscriptionData().size();
    }

    public static void iteratePontoonMap() {
        pontoonMapData.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));

    }

    // Populate pontoon Map Data *****************************************
    public static Future<Boolean> populatePontoonMapData() {

        try {
            if (pontoonInformations != null) {

                for (PontoonInfo pontoonInfo : pontoonInformations.getPontoonConfig()) {
                    PontoonDataForMap pontoonDataForMap = new PontoonDataForMap();

                    pontoonDataForMap.setPontoon_id(pontoonInfo.getId());
                    pontoonDataForMap.setxCoordinate(pontoonInfo.getxCoordinate());
                    pontoonDataForMap.setDeflectionLimit(pontoonInfo.getDeflectionLimit());
                    pontoonDataForMap.setOffset(pontoonInfo.getOffset());
                    if (pontoonInfo.getId() % 2 == 0) {
                        pontoonDataForMap.setShipSide("STARBOARD");
                    }else {
                        pontoonDataForMap.setShipSide("PORT");
                    }

                    pontoonMapData.put(pontoonInfo.getId(),pontoonDataForMap);
                }

            }
        } catch (NullPointerException e) {
            System.out.print("All Pontoons configuration is missing..");
            return executor.submit(() -> {

                return false;
            });
        }

        if (pontoonMapData.size() > 0){
            return executor.submit(() -> {
                return true;
            });
        }else {
            return executor.submit(() -> {
                return false;
            });
        }



    }    // Populate pontoon Map Data *****************************************

}
