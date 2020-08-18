package com.kockumation.backEnd.dockMaster;

import com.kockumation.backEnd.dockMaster.model.PontoonCurrentCoordinate;
import com.kockumation.backEnd.dockMaster.model.PontoonDataForMap;
import com.kockumation.backEnd.dockMaster.model.PontoonRefCoordinate;
import com.kockumation.backEnd.dockMaster.model.TankAlarmData;
import com.kockumation.backEnd.global.Db;
import com.kockumation.backEnd.utilities.MySQLJDBCUtil;
import com.kockumation.backEnd.utilities.PontoonInfo;

import java.sql.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class DetectAndSaveAlarms {
    public static Timer timer;
    public static boolean firstRun = true;
    private ExecutorService executor
            = Executors.newSingleThreadExecutor();

    public static Map<Integer, PontoonRefCoordinate> pontoonRefCoordinateMap;
    public static Map<Integer, PontoonCurrentCoordinate> pontoonCurrentCoordinateMap;

    public DetectAndSaveAlarms() {

        pontoonRefCoordinateMap = new HashMap<>();
        pontoonCurrentCoordinateMap = new HashMap<>();

    }

    public void createNewAlarmDetector() {
        timer = new Timer();
        detectAlarms();
    }

    // Make Unresolved Alarms Archived  ****************************** Make Unresolved Alarms Archived    ***************************************
    public Future<Boolean> makeUnresolvedAlarmsArchived() {
        int rowAffected;
        try (Connection conn = MySQLJDBCUtil.getConnection()) {

            String updateAlarms = "UPDATE alarms set alarm_description = ?,alarm_active = 0,alarm_state = 4 where (alarm_state = 1 || alarm_state = 2 || alarm_state = 3);";
            PreparedStatement preparedStmt = conn.prepareStatement(updateAlarms, Statement.RETURN_GENERATED_KEYS);

            preparedStmt.setString(1, "Archived Alarm");

             rowAffected = preparedStmt.executeUpdate();
           // System.out.println(rowAffected + " Alarms updated");
            String updateTanks = "UPDATE pontoons set alarm_name = null ;";
            PreparedStatement preparedStmt2 = conn.prepareStatement(updateTanks, Statement.RETURN_GENERATED_KEYS);
            int rowAffected2 = preparedStmt2.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }
        if (rowAffected > 0){
            return executor.submit(() -> {

                return true;
            });
        }else {
            return executor.submit(() -> {

                return false;
            });
        }


    }// Make Unresolved Alarms Archived  ****************************** Make Unresolved Alarms Archived    ***************************************




    // Insert new Alarm  ****************************** Insert new Alarm into alarms table   ***************************************
    public Future<Boolean> insertNewAlarm(PontoonDataForMap pontoonDataForMap) {

        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            String query = "INSERT INTO alarms (alarm_name,pontoon_id,acknowledged,alarm_description,alarm_active,alarm_date,alarm_state) VALUES (?,?,?,?,?,?,?);";

            PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // System.out.println(tankDataForMap);
            preparedStmt.setString(1, pontoonDataForMap.getAlarm_name());
            preparedStmt.setInt(2, pontoonDataForMap.getPontoon_id());
            preparedStmt.setBoolean(3, pontoonDataForMap.isAcknowledged());
            preparedStmt.setString(4, pontoonDataForMap.getAlarm_description());
            preparedStmt.setBoolean(5, pontoonDataForMap.isAlarm_active());
            preparedStmt.setString(6, pontoonDataForMap.getAlarm_date());
            preparedStmt.setInt(7, pontoonDataForMap.getAlarm_state());
            int rowAffected = preparedStmt.executeUpdate();
            System.out.println(rowAffected + " Pontoon Alarm inserted");

            String updateTanks = "UPDATE pontoons set alarm_name = ? where pontoon_id = ?;";
            PreparedStatement preparedStmt2 = conn.prepareStatement(updateTanks, Statement.RETURN_GENERATED_KEYS);
            preparedStmt2.setString(1, pontoonDataForMap.getAlarm_name());
            preparedStmt2.setInt(2, pontoonDataForMap.getPontoon_id());
            int rowAffected2 = preparedStmt2.executeUpdate();
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }
        return executor.submit(() -> {

            return true;
        });

    }// Insert new Alarm  ****************************** Insert new Alarm into alarms table   ***************************************

    // Update Alarm  ****************************** Update Alarm   ***************************************
    public Future<Boolean> updateAlarm(PontoonDataForMap pontoonDataForMap) {

        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            // String updateAlarms = "UPDATE alarms set alarm_name= ?,alarm_description = ?,blue_alarm = ?,alarm_date =? ,time_retrieved =?,alarm_active =?,time_accepted =?,acknowledged=?,valve_status = ? where (valve_id = ? && (archive = 0));";
            String updateAlarms = "UPDATE alarms set alarm_name = ?,acknowledged = ?,alarm_description =?,alarm_date =?,alarm_active =?,alarm_date = ?,alarm_state = ?,time_retrieved = ?,time_accepted = ? where (pontoon_id = ? && (alarm_state = 1 || alarm_state = 2 || alarm_state = 3));";
            PreparedStatement preparedStmt = conn.prepareStatement(updateAlarms, Statement.RETURN_GENERATED_KEYS);
            // System.out.println(tankDataForMap);
            preparedStmt.setString(1, pontoonDataForMap.getAlarm_name());
            preparedStmt.setBoolean(2, pontoonDataForMap.isAcknowledged());
            preparedStmt.setString(3, pontoonDataForMap.getAlarm_description());
            preparedStmt.setString(4, pontoonDataForMap.getAlarm_date());

            preparedStmt.setBoolean(5, pontoonDataForMap.isAlarm_active());
            preparedStmt.setString(6, pontoonDataForMap.getAlarm_date());

            preparedStmt.setInt(7, pontoonDataForMap.getAlarm_state());
            preparedStmt.setString(8, pontoonDataForMap.getTime_retrieved());
            preparedStmt.setString(9, pontoonDataForMap.getTime_accepted());
            preparedStmt.setInt(10, pontoonDataForMap.getPontoon_id());

            int rowAffected = preparedStmt.executeUpdate();
            System.out.println(rowAffected + " Pontoon Alarm inserted");
            String updateTanks = "UPDATE pontoons set alarm_name = ? where pontoon_id = ?;";
            PreparedStatement preparedStmt2 = conn.prepareStatement(updateTanks, Statement.RETURN_GENERATED_KEYS);
            preparedStmt2.setString(1, pontoonDataForMap.getAlarm_name());
            preparedStmt2.setInt(2, pontoonDataForMap.getPontoon_id());
            int rowAffected2 = preparedStmt2.executeUpdate();
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }
        return executor.submit(() -> {

            return true;
        });

    }// Update Alarm  ****************************** Update Alarm   ***************************************


    // Update Archive alarm  ****************************** Update Archive alarm   ***************************************
    public Future<Boolean> updateArchiveAlarm(PontoonDataForMap pontoonDataForMap) {

        try (Connection conn = MySQLJDBCUtil.getConnection()) {

            String updateAlarms = "UPDATE alarms set alarm_description = ?,time_retrieved =?,alarm_active =?,alarm_state = ? where (pontoon_id = ? && (alarm_state = 2 || alarm_state = 3));";
            PreparedStatement preparedStmt = conn.prepareStatement(updateAlarms, Statement.RETURN_GENERATED_KEYS);

            preparedStmt.setString(1, pontoonDataForMap.getAlarm_description());
            preparedStmt.setString(2, pontoonDataForMap.getTime_retrieved());
            preparedStmt.setBoolean(3, pontoonDataForMap.isAlarm_active());
            preparedStmt.setInt(4, pontoonDataForMap.getAlarm_state());
            preparedStmt.setInt(5, pontoonDataForMap.getPontoon_id());
            int rowAffected = preparedStmt.executeUpdate();

            String updateTanks = "UPDATE pontoons set alarm_name = null where pontoon_id = ?;";
            PreparedStatement preparedStmt2 = conn.prepareStatement(updateTanks, Statement.RETURN_GENERATED_KEYS);

            preparedStmt2.setInt(1, pontoonDataForMap.getPontoon_id());
            int rowAffected2 = preparedStmt2.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }
        return executor.submit(() -> {
            return true;
        });

    }// Update Archive alarm  ****************************** Update Archive alarm   ***************************************


    //Update pontoons table with refDraft and current draft
    private void updatePontoonsRefDraft() {
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            try {
                String updatePontoons = "UPDATE pontoons set refDraft = ?,currentDraft =? where (pontoon_id = ? );";
                PreparedStatement preparedStmt = conn.prepareStatement(updatePontoons, Statement.RETURN_GENERATED_KEYS);

                Db.pontoonMapData.entrySet().stream().forEach(e -> {
                    try {
                        preparedStmt.setFloat(1, e.getValue().getRefDraft());
                        preparedStmt.setFloat(2, e.getValue().getCurrentDraft());
                        preparedStmt.setInt(3, e.getValue().getPontoon_id());

                        int rowAffected = preparedStmt.executeUpdate();
                        preparedStmt.clearParameters();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                });

            } catch (NullPointerException e) {
                System.out.print("All Pontoons not updated");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    } //Update pontoons table with refDraft and current draft


    // Round to decimal places  ***********************************
    float roundToDecimalPlaces(float value, int decimalPlaces) {
        float shift = (float) Math.pow(10, decimalPlaces);
        return Math.round(value * shift) / shift;
    } // Round to decimal places  ***********************************

    public void detectAlarms() {
        try {
        boolean  makeAlarmsArchived = makeUnresolvedAlarmsArchived().get();
           if (makeAlarmsArchived){
               System.out.println("Unresolved alarms now becomes archive");
           }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                if (Db.tankSubscriptionData.getSetTankSubscriptionData() != null) {
                    for (TankAlarmData tankAlarmData : Db.tankSubscriptionData.getSetTankSubscriptionData()) {
                      /*   if (tankAlarmData.getTankId() == 1){
                             System.out.println(tankAlarmData);
                         }*/

                        int id = tankAlarmData.getTankId();

                        float currentDraft = tankAlarmData.getLevel();
                        if (id <= 16) {
                            if (id % 2 == 0) {
                                //  System.out.println("even "+id);

                                // Calculate Ship side starboard ,draft and x coordinate for reference.
                                PontoonRefCoordinate pontoonRefCoordinate = new PontoonRefCoordinate();
                                PontoonInfo pontoonInfo = Db.pontoonInformations.getPontoonConfig().get(id - 1);

                                pontoonRefCoordinate.setId(id);
                                pontoonRefCoordinate.setShipSide("STARBOARD");
                                pontoonRefCoordinate.setxCoordinate(pontoonInfo.getxCoordinate());
                                pontoonRefCoordinateMap.put(id, pontoonRefCoordinate);

                                // Calculate current draft, x coordinate and ship side starboard.
                                PontoonCurrentCoordinate pontoonCurrentCoordinate = new PontoonCurrentCoordinate();
                                pontoonCurrentCoordinate.setId(id);
                                pontoonCurrentCoordinate.setShipSide("STARBOARD");
                                pontoonCurrentCoordinate.setxCoordinate(pontoonInfo.getxCoordinate());
                                pontoonCurrentCoordinate.setCurrentDraft(currentDraft);
                                pontoonCurrentCoordinateMap.put(id, pontoonCurrentCoordinate);
                                Db.pontoonMapData.get(id).setCurrentDraft(currentDraft);

                            } else {
                                // System.out.println("odd "+id);
                                // Calculate Ship side port ,draft and x coordinate for reference .
                                PontoonRefCoordinate pontoonRefCoordinate = new PontoonRefCoordinate();
                                PontoonInfo pontoonInfo = Db.pontoonInformations.getPontoonConfig().get(id - 1);
                                pontoonRefCoordinate.setId(id);
                                pontoonRefCoordinate.setShipSide("PORT");
                                pontoonRefCoordinate.setxCoordinate(pontoonInfo.getxCoordinate());
                                pontoonRefCoordinateMap.put(id, pontoonRefCoordinate);

                                // Calculate current draft, x coordinate and ship side port.
                                PontoonCurrentCoordinate pontoonCurrentCoordinate = new PontoonCurrentCoordinate();
                                pontoonCurrentCoordinate.setId(id);
                                pontoonCurrentCoordinate.setShipSide("PORT");
                                pontoonCurrentCoordinate.setxCoordinate(pontoonInfo.getxCoordinate());
                                pontoonCurrentCoordinate.setCurrentDraft(currentDraft);
                                pontoonCurrentCoordinateMap.put(id, pontoonCurrentCoordinate);
                                Db.pontoonMapData.get(id).setCurrentDraft(currentDraft);
                            }
                        } // if (id <= 16)
                        if (id == 17) {

                            break;
                        }

                    }// for (TankAlarmData tankAlarmData : TankLiveDataSubscription.tankSubscriptionData.getSetTankSubscriptionData())

                    // Calculate Reference draft *************************************************************************
                    try {
                        PontoonCurrentCoordinate pontoonCurr15 = pontoonCurrentCoordinateMap.get(15);
                        PontoonCurrentCoordinate pontoonCurr1 = pontoonCurrentCoordinateMap.get(1);
                        PontoonCurrentCoordinate pontoonCurr2 = pontoonCurrentCoordinateMap.get(2);
                        PontoonCurrentCoordinate pontoonCurr16 = pontoonCurrentCoordinateMap.get(16);

                        // Calculate port side refDraft

                       /* An equation in the slope-intercept form is written as y=mx+b
                       For Calculating the slope between two points (pontoonCurr15 and pontoonCurr1 for port side)
                       m=(y2−y1) / (x2−x1) OR   m=Δy / Δx
                       */
                        float m = (pontoonCurr15.getCurrentDraft() - pontoonCurr1.getCurrentDraft()) / (pontoonCurr15.getxCoordinate() - pontoonCurr1.getxCoordinate());

                        //int b = y - (m * x ); equation for finding b (y-intercept)
                        float b = pontoonCurr15.getCurrentDraft() - (m * pontoonCurr15.getxCoordinate());

                        //    System.out.println("pontoon Current 15 "+ pontoonCurr15);
                        //   System.out.println("pontoon Current 1 "+ pontoonCurr1);
                        // System.out.println("M: "+m);
                        //   System.out.println("B y-intercept: "+b);
                        for (int i = 15; i >= 1; i = i - 2) {
                            // y = mx + b   ===>> refDraft = m * xCoordinate + b
                            float refDraft = m * (pontoonRefCoordinateMap.get(i).getxCoordinate()) + b;
                            pontoonRefCoordinateMap.get(i).setRefDraft(refDraft);
                            Db.pontoonMapData.get(i).setRefDraft(refDraft);

                        }

                        // Calculate Starboard side refDraft
                        float mStarboard = (pontoonCurr16.getCurrentDraft() - pontoonCurr2.getCurrentDraft()) / (pontoonCurr16.getxCoordinate() - pontoonCurr2.getxCoordinate());
                        //int b = y - (m * x ); equation for finding b (y-intercept)
                        float bStarboard = pontoonCurr16.getCurrentDraft() - (mStarboard * pontoonCurr16.getxCoordinate());
                        //  System.out.println("B is : " + bStarboard);
                        //  System.out.println("M is : " + mStarboard);
                        for (int i = 16; i >= 2; i = i - 2) {
                            // y = mx + b   ===>> refDraft = m * xCoordinate + b
                            float refDraft = mStarboard * (pontoonRefCoordinateMap.get(i).getxCoordinate()) + bStarboard;
                            pontoonRefCoordinateMap.get(i).setRefDraft(refDraft);
                            Db.pontoonMapData.get(i).setRefDraft(refDraft);
                        }

                    } catch (NullPointerException e) {
                        System.out.println("No data coming from web socket server Tanks live data");
                    } // Calculate Reference draft

                    // Alarms status ***************************************************

                    Db.pontoonMapData.entrySet().stream().forEach(pontoon -> {
                        int id = pontoon.getValue().getPontoon_id();
                        float currentDraft = pontoon.getValue().getCurrentDraft();
                        float referenceDraft = pontoon.getValue().getRefDraft();
                        float deflectionLimit = pontoon.getValue().getDeflectionLimit();
                        boolean updateRed = pontoon.getValue().isUpdateRed();
                        boolean updateBlue = pontoon.getValue().isUpdateBlue();
                        boolean alarm_active = pontoon.getValue().isAlarm_active();
                        boolean acknowledged = pontoon.getValue().isAcknowledged();
                        String time_retrieved = pontoon.getValue().getTime_retrieved();
                        boolean inserted = pontoon.getValue().isInserted();
                        float draftDifference = Math.abs(currentDraft - referenceDraft);
                        draftDifference = roundToDecimalPlaces(draftDifference, 4);
                        pontoon.getValue().setDraftDifference(draftDifference);

                       /* if (id == 3) {
                            //  System.out.println("Difference is: "+ draftDifference + " Deflection Limit "+ deflectionLimit);
                            System.out.println(pontoon.getValue());
                        }*/


                        // Calculate Red Alarm *****************************************************************
                        if (updateRed && (draftDifference >= deflectionLimit) && (alarm_active == false)) {
                            boolean isUpdateRed = false;
                            pontoon.getValue().setUpdateRed(false);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String alarmDateTriggered = LocalDateTime.now(Clock.systemUTC()).format(formatter);
                            pontoon.getValue().setAlarm_date(alarmDateTriggered);
                            pontoon.getValue().setAlarm_active(true);
                            pontoon.getValue().setUpdateBlue(true);
                            pontoon.getValue().setTime_retrieved(null);
                            pontoon.getValue().setAlarm_state(1);
                            String alarmName = "Pontoon " + id + " Alarm";
                            pontoon.getValue().setAlarm_name(alarmName);
                            pontoon.getValue().setAlarm_description("Active unaccepted alarm");
                            pontoon.getValue().setAlarm_state(1);
                            if (inserted) {
                                try {
                                    isUpdateRed = updateAlarm(pontoon.getValue()).get();
                                    if (isUpdateRed) pontoon.getValue().setInserted(true);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    isUpdateRed = insertNewAlarm(pontoon.getValue()).get();
                                    if (isUpdateRed) pontoon.getValue().setInserted(true);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }


                        } // Calculate Red Alarm *****************************************************************

                        // Calculate Blue Alarm *****************************************************************
                        if (updateBlue && (draftDifference < deflectionLimit) && alarm_active && (acknowledged == false)) {
                            boolean isUpdateBlue = false;
                            pontoon.getValue().setUpdateRed(true);
                            pontoon.getValue().setUpdateBlue(false);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String alarmRetrieved = LocalDateTime.now(Clock.systemUTC()).format(formatter);

                            pontoon.getValue().setAlarm_active(false);
                            pontoon.getValue().setTime_retrieved(alarmRetrieved);
                            pontoon.getValue().setAlarm_state(3);
                            pontoon.getValue().setAlarm_description("Inactive unaccepted");

                            try {
                                isUpdateBlue = updateAlarm(pontoon.getValue()).get();
                                if (isUpdateBlue) pontoon.getValue().setInserted(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                        } // Calculate Blue Alarm *****************************************************************

                        // Alarm becomes archive *****************************************************************
                        if ((draftDifference < deflectionLimit) && (acknowledged == true)) {
                            boolean isUpdateArchive = false;

                            if (time_retrieved == null) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String time_retrieved2 = LocalDateTime.now(Clock.systemUTC()).format(formatter);
                                pontoon.getValue().setTime_retrieved(time_retrieved2);
                            }
                            pontoon.getValue().setAlarm_state(4);
                            pontoon.getValue().setAlarm_description("Archived Alarm");
                            pontoon.getValue().setAlarm_active(false);


                            try {
                                isUpdateArchive = updateArchiveAlarm(pontoon.getValue()).get();

                                if (isUpdateArchive) {
                                    System.out.println("Pontoon: " + id + " Alarm Became Archive");
                                    pontoon.getValue().setAlarm_state(0);
                                    pontoon.getValue().setAlarm_description(null);
                                    pontoon.getValue().setInserted(false);
                                    pontoon.getValue().setAcknowledged(false);
                                    pontoon.getValue().setTime_accepted(null);
                                    pontoon.getValue().setTime_retrieved(null);
                                    pontoon.getValue().setAlarm_description(null);
                                    pontoon.getValue().setAlarm_date(null);
                                    pontoon.getValue().setUpdateRed(true);
                                    pontoon.getValue().setUpdateBlue(true);
                                    pontoon.getValue().setAlarm_name(null);

                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                        } // Alarm becomes archive *****************************************************************


                    });//Db.pontoonMapData.entrySet().stream().forEach(pontoon ->
                    System.out.println("*********************************************** Dock master server ******************************************************");
                    // Alarms status ***************************************************

                    //Update pontoons table with refDraft and current draft
                    CompletableFuture.runAsync(() -> {
                        updatePontoonsRefDraft();
                    });
                    //Update pontoons table with refDraft and current draft



                }// if (TankLiveDataSubscription.tankSubscriptionData != null)
                //   Db.iteratePontoonMap();
            } // run

        }, 2000, 7000);
    }

}
