package com.kockumation.backEnd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kockumation.backEnd.dockMaster.DetectAndSaveAlarms;
import com.kockumation.backEnd.dockMaster.model.PontoonCurrentCoordinate;
import com.kockumation.backEnd.dockMaster.model.PontoonDataForMap;
import com.kockumation.backEnd.dockMaster.model.PontoonRefCoordinate;
import com.kockumation.backEnd.global.Db;
import com.kockumation.backEnd.model.*;
import com.kockumation.backEnd.utilities.DockConfig;
import com.kockumation.backEnd.utilities.MySQLJDBCUtil;
import com.kockumation.backEnd.utilities.PontoonInformations;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class PontoonService {

    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public PontoonService() {
        getListOfPontoonInfo();

    }


    //Get  a list of latest 100 alarms from MySql. ******************** Get  a list of latest 100 alarms from MySql. ****************************************************
    public Future<List<Alarm>> getHundredAlarms() {

        List<Alarm> alarmList = new ArrayList<>();
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "SELECT al.alarm_id,al.alarm_name,al.alarm_active,al.acknowledged,al.pontoon_id,al.alarm_date,al.alarm_description,al.alarm_state,al.time_accepted,al.time_retrieved FROM alarms al order by  alarm_active desc ,acknowledged asc , alarm_state asc , alarm_date desc LIMIT 100";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Alarm alarm = new Alarm();
                alarm.setAlarm_name(rs.getString("alarm_name"));
                alarm.setAlarm_id(rs.getInt("alarm_id"));
                alarm.setPontoon_id(rs.getInt("pontoon_id"));
                alarm.setAlarm_active(rs.getInt("alarm_active"));
                alarm.setAcknowledged(rs.getInt("acknowledged"));
                alarm.setAlarm_date(rs.getString("alarm_date"));
                alarm.setAlarm_description(rs.getString("alarm_description"));
                alarm.setAlarm_state(rs.getInt("alarm_state"));
                alarm.setTime_accepted(rs.getString("time_accepted"));
                alarm.setTime_retrieved(rs.getString("time_retrieved"));

                alarmList.add(alarm);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return alarmList;
            });
        } // End of catch
        return executor.submit(() -> {
            return alarmList;
        });

    }//Get  a list of latest 100 alarms from MySql. ******************** Get  a list of latest 100 alarms from MySql. *************************************

    // Get list of Pontoons information ********* Get list of Pontoons information *************************
    public void getListOfPontoonInfo() {

        try {

            Db.pontoonInformations = new ObjectMapper().readValue(DockConfig.getPontoonInfoString(), PontoonInformations.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //  System.out.println(pontoonInformations.getPontoonInfo().get(2));
        //  DockConfig.getPontoonInfoString();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // return pontoonInformations;
    }  // Get list of Pontoons information ********* Get list of Pontoons information *************************

    // Get Map of Reference Pontoons Draft *****  Get Map of Reference Pontoons Draft *************
    public Future<Map<Integer, PontoonRefCoordinate>> getReferencePontoons() {

        if (DetectAndSaveAlarms.pontoonRefCoordinateMap.size() > 0) {

            Map<Integer, PontoonRefCoordinate> finalPontoonRefCoordinateList = DetectAndSaveAlarms.pontoonRefCoordinateMap;

            return executor.submit(() -> {

                return finalPontoonRefCoordinateList;
            });


        } else {
            Map<Integer, PontoonRefCoordinate> finalPontoonRefCoordinateList = null;
            return executor.submit(() -> {
                return finalPontoonRefCoordinateList;
            });
        } // else

    }// Get list of Reference Pontoons Draft *****  Get list of Reference Pontoons Draft *************

    // Get Map of Current Pontoons Draft *****  Get Map of Current Pontoons Draft *************
    public Future<Map<Integer, PontoonCurrentCoordinate>> getCurrentPontoons() {

        if (DetectAndSaveAlarms.pontoonCurrentCoordinateMap.size() > 0) {

            Map<Integer, PontoonCurrentCoordinate> finalPontoonCurrentCoordinateList = DetectAndSaveAlarms.pontoonCurrentCoordinateMap;

            return executor.submit(() -> {

                return finalPontoonCurrentCoordinateList;
            });


        } else {
            Map<Integer, PontoonCurrentCoordinate> finalPontoonCurrentCoordinateList = null;
            return executor.submit(() -> {
                return finalPontoonCurrentCoordinateList;
            });
        } // else

    }// Get Map of Current Pontoons Draft *****  Get Map of Current Pontoons Draft *************

    // Make Pontoon alarm Acknowledged *************** Make Pontoon alarm Acknowledged
    public Future<Boolean> makeAlarmAcknowledged(PontoonPostObject pontoonPostObject) {
        boolean acceptAlarm = false;
        int pontoon_id = pontoonPostObject.getPontoon_id();
        PontoonDataForMap pontoonDataForMap = null;
        if (Db.pontoonMapData.containsKey(pontoon_id)) {
            pontoonDataForMap = Db.pontoonMapData.get(pontoon_id);

            if (pontoonDataForMap.isAlarm_active() || pontoonDataForMap.getAlarm_state() == 3) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timeAccepted = LocalDateTime.now(Clock.systemUTC()).format(formatter);

                pontoonDataForMap.setTime_accepted(timeAccepted);
                pontoonDataForMap.setAlarm_description("Alarm accepted");
                pontoonDataForMap.setAcknowledged(true);
                pontoonDataForMap.setUpdateRed(true);
                pontoonDataForMap.setUpdateBlue(true);
                pontoonDataForMap.setAlarm_state(2);
                try {
                    acceptAlarm = updatePontoonAlarm(pontoonDataForMap).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return executor.submit(() -> {
                    return true;
                });
            }//(pontoonDataForMap.isAlarm_active() || pontoonDataForMap.getAlarm_state() == 3)
            else {
                return executor.submit(() -> {
                    return false;
                });
            }

        } else {
            return executor.submit(() -> {
                return false;
            });
        } // else

    } // Make Pontoon alarm Acknowledged *************** Make Pontoon alarm Acknowledged

    // Calculate heel and difference between current draft.
    public Future<Heeling> calculateHeeling() {
        Heeling heelingObject = new Heeling();

        if (Db.pontoonMapData.size() == 16) {

            float starboardCurrentDrafts = 0.0f;
            float portCurrentDrafts = 0.0f;
            for (Map.Entry<Integer, PontoonDataForMap> entry : Db.pontoonMapData.entrySet()) {
                int id = entry.getValue().getPontoon_id();
                if (id % 2 == 0) {
                    starboardCurrentDrafts += entry.getValue().getCurrentDraft();
                } else {
                    portCurrentDrafts += entry.getValue().getCurrentDraft();
                }

            }// For

            float starboardAverage = starboardCurrentDrafts / 8;
            float portAverage = portCurrentDrafts / 8;
            float heel = Math.abs(starboardAverage - portAverage);
            heelingObject.setHeeling(heel);

            int counter = 1;
            for (int i = 1; i <= 15; i = i + 2) {

                float currentDraftPort = Db.pontoonMapData.get(i).getCurrentDraft();
                float currentDraftStarboard = Db.pontoonMapData.get(i + 1).getCurrentDraft();
                DeltaDraft deltaDraft = new DeltaDraft();
                deltaDraft.setDeltaDraft(currentDraftPort - currentDraftStarboard);
                deltaDraft.setDeltaDraftName("Delta Draft " + counter);
                heelingObject.getDeltaDrafts().add(deltaDraft);

                counter++;
            }


            return executor.submit(() -> {

                return heelingObject;
            });
        } // if (Db.pontoonMapData.size() == 16)
        return executor.submit(() -> {

            return heelingObject;
        });
    }  // Calculate heel and difference between current draft.

    // Update Valve alarm  ****************************** Update Valve alarm   ***************************************
    public Future<Boolean> updatePontoonAlarm(PontoonDataForMap pontoonDataForMap) {

        try (Connection conn = MySQLJDBCUtil.getConnection()) {

            String updateAlarms = "UPDATE alarms set alarm_description = ?,time_accepted =?,acknowledged=?,alarm_state = ? where (pontoon_id = ? && (alarm_state = 1 || alarm_state = 3));";
            PreparedStatement preparedStmt = conn.prepareStatement(updateAlarms, Statement.RETURN_GENERATED_KEYS);

            preparedStmt.setString(1, pontoonDataForMap.getAlarm_description());
            preparedStmt.setString(2, pontoonDataForMap.getTime_accepted());
            preparedStmt.setBoolean(3, pontoonDataForMap.isAcknowledged());
            preparedStmt.setInt(4, pontoonDataForMap.getAlarm_state());
            preparedStmt.setInt(5, pontoonDataForMap.getPontoon_id());

            int rowAffected = preparedStmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }
        return executor.submit(() -> {

            return true;
        });

    }// Update Valve alarm  ****************************** Update Valve alarm   ***************************************

    //Get pontoons table from MySql. ******************** Get tanks table from MySql. ****************************************************
    public Future<List<Pontoon>> getPontoonsTable() {
        List<Pontoon> pontoons = new ArrayList<>();
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "select t.pontoon_id,t.alarm_name,t.xCoordinate,t.shipSide,t.deflectionLimit,t.`offset`,t.refDraft,t.currentDraft\n" +
                    "    from   pontoons t";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Pontoon pontoon = new Pontoon();
                pontoon.setPontoon_id(rs.getInt("pontoon_id"));
                pontoon.setAlarm_name((rs.getString("alarm_name")));
                pontoon.setxCoordinate(rs.getFloat("xCoordinate"));
                pontoon.setShipSide(rs.getString("shipSide"));
                pontoon.setDeflectionLimit(rs.getFloat("deflectionLimit"));
                pontoon.setOffset(rs.getFloat("offset"));
                pontoon.setRefDraft(rs.getFloat("refDraft"));
                pontoon.setCurrentDraft(rs.getFloat("currentDraft"));


                pontoons.add(pontoon);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return pontoons;
            });
        } // End of catch
        return executor.submit(() -> {
            return pontoons;
        });

    }//Get tanks table from MySql. ******************** Get tanks table from MySql. ****************************************************

} // Class PontoonService
