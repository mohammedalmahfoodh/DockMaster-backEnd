package com.kockumation.backEnd.dockMaster;

import com.kockumation.backEnd.dockMaster.model.PontoonDataForMap;
import com.kockumation.backEnd.dockMaster.model.TankDataForMap;
import com.kockumation.backEnd.global.Db;
import com.kockumation.backEnd.utilities.MySQLJDBCUtil;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InitiateDataBase {
    private ExecutorService executor;

    public InitiateDataBase() {
        executor = Executors.newSingleThreadExecutor();
    }

    // Check if data exists in Pontoons table *****************************************
    public Future<Boolean> checkIfDataExists() {
        String sql = "SELECT *  FROM pontoons WHERE pontoon_id=1 ";
        try (Connection conn = MySQLJDBCUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return executor.submit(() -> {
                    return true;
                });
            } else {
                //   System.out.println(rs);
                return executor.submit(() -> {
                    return false;
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }

    } // Check if data exists in Pontoons table *****************************************

    // Insert pontoons Into Pontoons table *****************************************
    public Future<Boolean> insertPontoonsIntoDB() {

        try {
            try (Connection conn = MySQLJDBCUtil.getConnection()) {
                String query = "INSERT INTO pontoons (pontoon_id,xCoordinate,deflectionLimit,offset,shipSide) VALUES (?,?,?,?,?);";

                for (Map.Entry<Integer, PontoonDataForMap> entry : Db.pontoonMapData.entrySet()) {
                    PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    preparedStmt.setInt(1, entry.getValue().getPontoon_id());
                    preparedStmt.setFloat(2, entry.getValue().getxCoordinate());
                    preparedStmt.setFloat(3, entry.getValue().getDeflectionLimit());
                    preparedStmt.setFloat(4, entry.getValue().getOffset());
                    preparedStmt.setString(5, entry.getValue().getShipSide());
                    int rowAffected = preparedStmt.executeUpdate();

                    // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                }
                System.out.println(" Pontoons configs inserted");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }

        return executor.submit(() -> {

            return true;
        });
    }// Insert pontoons Into Pontoons table *****************************************


    // Update pontoons table *****************************************
    public Future<Boolean> updatePontoonsIntoDB() {

        try {
            try (Connection conn = MySQLJDBCUtil.getConnection()) {
                String updatePontoons = "UPDATE pontoons set xCoordinate = ?,deflectionLimit = ?,offset =? ,shipSide = ? where pontoon_id = ?;";

                for (Map.Entry<Integer, PontoonDataForMap> entry : Db.pontoonMapData.entrySet()) {
                    PreparedStatement preparedStmt = conn.prepareStatement(updatePontoons, Statement.RETURN_GENERATED_KEYS);

                    preparedStmt.setFloat(1, entry.getValue().getxCoordinate());
                    preparedStmt.setFloat(2, entry.getValue().getDeflectionLimit());
                    preparedStmt.setFloat(3, entry.getValue().getOffset());
                    preparedStmt.setString(4, entry.getValue().getShipSide());
                    preparedStmt.setInt(5, entry.getValue().getPontoon_id());

                    int rowAffected = preparedStmt.executeUpdate();

                    // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
                System.out.println(" Pontoons configs inserted");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            return executor.submit(() -> {
                return false;
            });
        }

        return executor.submit(() -> {

            return true;
        });
    }// Update pontoons table *****************************************


}// Class InitiateDataBase
