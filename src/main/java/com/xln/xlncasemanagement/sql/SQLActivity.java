/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.model.table.ActivityTableModel;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String sql = "SELECT table01.*, table02.col03 AS activityType, table22.col03 as firstName, "
                + "table22.col04 as middleName, table22.col05 as lastName, table22.col08 as userName "
                + "FROM table01 "
                + "LEFT JOIN table02 ON table01.col04 = table02.col01 "
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
        sql += " AND table01.col05 = ?";
        
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
                item.setDuration(rs.getDouble("col"));
                item.setDescription(rs.getString("col07"));
                item.setFileName(rs.getString("col09"));
                item.setBillable(rs.getBoolean("col"));
                item.setInvoiced(rs.getBoolean("col10"));

                list.add(
                        new ActivityTableModel(
                                item,  //Object
                                Global.getMmddyyyy().format(rs.getDate("col06")), //Date
                                String.valueOf(rs.getDouble("col08")), //Hours
                                StringUtilities.buildName(rs.getString("firstName"), rs.getString("middleName"), rs.getString("lastName")), //user
                                rs.getString("activityType") + (rs.getString("col07") == null ? "" : " - " + rs.getString("col07")), //Description
                                rs.getString("col09"), //File
                                rs.getBoolean("col10"), //Invoiced
                                rs.getBoolean("col10") //Invoiced
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
    
}