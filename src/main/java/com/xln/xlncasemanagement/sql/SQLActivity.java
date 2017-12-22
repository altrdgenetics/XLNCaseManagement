/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.model.table.ActivityTableModel;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLActivity {
    
    public static ObservableList<ActivityTableModel> searchActivity(String[] param, int matterID) {
        ObservableList<ActivityTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT table01.*, table11.col03 AS fileName, IFNULL(table02.col03, 'SYSTEM') AS activityType, "
                + "table22.col03 AS firstName, table22.col04 AS middleName, table22.col05 AS lastName, "
                + "table22.col08 AS userName "
                + "FROM table01 "
                + "LEFT JOIN table02 ON table01.col04 = table02.col01 "
                + "LEFT JOIN table11 ON table01.col01 = table11.col02 "
                + "LEFT JOIN table22 ON table01.col03 = table22.col01 "
                + "WHERE table01.col02 = 1 ";
        if (param.length > 0) {
            for (String param1 : param) {
                sql += " AND CONCAT("          
                        + "IFNULL(table01.col08,''), " // description
                        + "IFNULL(table02.col03,''), " // activity type
                        + "IFNULL(table22.col03,''), " // first name
                        + "IFNULL(table22.col05,''), " // last name
                        + "IFNULL(table22.col08,'') "  // username
                        + ") LIKE ? ";
            }
        }
        sql += " AND table01.col05 = ? ORDER BY table01.col06 DESC";
        
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ps.setInt(param.length + 1, matterID);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                ActivityModel item = new ActivityModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setUserID(rs.getInt("col03"));
                item.setActivityTypeID(rs.getInt("col04"));
                item.setMatterID(rs.getInt("col05"));
                item.setDateOccurred(rs.getDate("col06"));
                item.setDuration(rs.getBigDecimal("col07"));
                item.setRate(rs.getBigDecimal("col08"));
                item.setTotal(rs.getBigDecimal("col09"));
                item.setDescription(rs.getString("col10"));
                item.setBillable(rs.getBoolean("col11"));
                item.setInvoiced(rs.getBoolean("col12"));
                item.setFileName(rs.getString("fileName"));
                
                list.add(
                        new ActivityTableModel(
                                item,  //Object
                                Global.getMmddyyyy().format(rs.getDate("col06")), //Date
                                String.valueOf(rs.getBigDecimal("col07")), //Hours
                                StringUtilities.buildName(rs.getString("firstName"), rs.getString("middleName"), rs.getString("lastName")), //user
                                rs.getString("activityType") + (rs.getString("col10") == null ? "" : " - " + rs.getString("col10")), //Description
                                rs.getString("fileName"), //File
                                rs.getBoolean("col11"), //billable
                                rs.getBoolean("col12") //Invoiced
                        )
                );
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
    
    public static ActivityModel getActivityByID(int id) {
        ActivityModel item = new ActivityModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table01 WHERE col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setUserID(rs.getInt("col03"));
                item.setActivityTypeID(rs.getInt("col04"));
                item.setMatterID(rs.getInt("col05"));
                item.setDateOccurred(rs.getDate("col06"));
                item.setDuration(rs.getBigDecimal("col07"));
                item.setRate(rs.getBigDecimal("col08"));
                item.setTotal(rs.getBigDecimal("col09"));
                item.setDescription(rs.getString("col10"));
                item.setBillable(rs.getBoolean("col11"));
                item.setInvoiced(rs.getBoolean("col12"));
                item.setFileName(rs.getString("col13"));
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
    
    public static void updateActivityByID(ActivityModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table01 SET "
                + "col03 = ?, " //01 userID
                + "col04 = ?, " //02 activityTypeID
                + "col06 = ?, " //03 dateOccurred
                + "col07 = ?, " //04 duration
                + "col08 = ?, " //05 rate
                + "col09 = ?, " //06 total
                + "col10 = ?, " //07 description
                + "col11 = ? "  //08 billable
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt       (1, item.getUserID());
            ps.setInt       (2, item.getActivityTypeID());
            ps.setDate      (3, item.getDateOccurred());
            ps.setBigDecimal(4, item.getDuration());
            ps.setBigDecimal(5, item.getRate());
            ps.setBigDecimal(6, item.getTotal());
            ps.setString    (7, item.getDescription());
            ps.setBoolean   (8, item.isBillable());
            ps.setInt       (9, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static int insertActivity(ActivityModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table01 ("
                + "col02, " //01 active
                + "col03, " //02 user ID
                + "col04, " //03 activity type ID
                + "col05, " //04 matter ID
                + "col06, " //05 date Occurred
                + "col07, " //06 duration
                + "col08, " //07 rate
                + "col09, " //08 total
                + "col10, " //09 description
                + "col11, " //10 billable
                + "col12 "  //11 invoiced
                + ") VALUES (";
                for(int i=0; i<10; i++){
                        sql += "?, ";   //01-10
                    }
                sql += "?)"; //11
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean   ( 1, item.isActive());
            ps.setInt       ( 2, item.getUserID());
            ps.setInt       ( 3, item.getActivityTypeID());
            ps.setInt       ( 4, item.getMatterID());
            ps.setDate      ( 5, item.getDateOccurred());
            ps.setBigDecimal( 6, item.getDuration());
            ps.setBigDecimal( 7, item.getRate());
            ps.setBigDecimal( 8, item.getTotal());
            ps.setString    ( 9, item.getDescription());
            ps.setBoolean   (10, item.isBillable());
            ps.setBoolean   (11, item.isInvoiced());
            ps.executeUpdate();

            ResultSet newRow = ps.getGeneratedKeys();
            if (newRow.first()) {
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
    
    public static BigDecimal getLastRate(int userID, int matterID){
        BigDecimal value = BigDecimal.ZERO;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT col08 FROM table01 WHERE col03 = ? AND col05 = ? AND col08 != 0.00 ORDER BY col06 DESC LIMIT 1";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setInt(2, matterID);
            rs = ps.executeQuery();
            if (rs.first()) {
                value = rs.getBigDecimal("col08");
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return value;
    }
        
    public static void markMatterActivitesAsInvoiced(int matterID) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table01 SET "
                + "col12       = 1 " // Set Invoiced
                + "WHERE col02 = 1 " // Active
                + "AND col12   = 0 " // Not Invoiced
                + "AND col05   = ?"; // MatterID
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, matterID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
