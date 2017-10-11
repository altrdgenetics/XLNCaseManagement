/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.ModelModel;
import com.xln.xlncasemanagement.model.table.MaintenanceModelTableModel;
import com.xln.xlncasemanagement.util.DebugTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLModel {
    
    public static ObservableList<MaintenanceModelTableModel> searchModels(String[] param, int makeID) {
        ObservableList<MaintenanceModelTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT table25.*, table24.col03 AS makeName "
                + "FROM table25 "
                + "LEFT JOIN table24 ON table25.col03 = table24.col01 "
                + "WHERE ";
        if (param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                if (i > 0) {
                    sql += " AND";
                }
                sql += " CONCAT("          
                        + "IFNULL(table24.col03,''), " // Make
                        + "IFNULL(table25.col04,'') "  // Model
                        + ") LIKE ? ";
            }
        }
        if (param.length == 0 && makeID > 0){
            sql += " table25.col03 = " + makeID + " AND table25.col02 = 1";
        } else if (param.length > 0 && makeID > 0){
            sql += " AND table25.col03 = " + makeID + " AND table25.col02 = 1";
        }
        
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                ModelModel item = new ModelModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setMakeID(rs.getInt("col03"));
                item.setMakeName("makeName");
                item.setName(rs.getString("col04"));
                item.setWebsite(rs.getString("col05"));

                list.add(
                        new MaintenanceModelTableModel(
                                item,
                                rs.getBoolean("col02"),
                                rs.getString("makeName"),
                                rs.getString("col04"),
                                rs.getString("col05")                                
                        ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
        return list;
    }
    
    public static int insertModel(ModelModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table25 ("
                + "col02, "
                + "col03, "
                + "col04, "
                + "col05 "
                + ") VALUES ("
                + "?, " //1
                + "?, " //2
                + "?, " //3
                + "?)"; //4
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setInt    (2, item.getMakeID());
            ps.setString (3, item.getName());
            ps.setString (4, item.getWebsite());
            ps.executeUpdate();

            ResultSet newRow = ps.getGeneratedKeys();
            if (newRow.next()) {
                return newRow.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return 0;
    }

    public static void updateModelByID(ModelModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table25 SET "
                + "col02 = ?, "
                + "col03 = ?, "
                + "col04 = ?, "
                + "col05 = ? "
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, item.isActive());
            ps.setInt    (2, item.getMakeID());
            ps.setString (3, item.getName());
            ps.setString (4, item.getWebsite());
            ps.setInt    (5, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static List<ModelModel> getActiveModel() {
        List<ModelModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table25 WHERE col02 = 1";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelModel item = new ModelModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setMakeID(rs.getInt("col03"));
                item.setName(rs.getString("col04"));
                item.setWebsite(rs.getString("col05"));
                list.add(item);
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
    public static ModelModel geModelByID(int id) {
        ModelModel item = new ModelModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table25 WHERE col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setMakeID(rs.getInt("col03"));
                item.setName(rs.getString("col04"));
                item.setWebsite(rs.getString("col05"));
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return item;
    }
    
}
