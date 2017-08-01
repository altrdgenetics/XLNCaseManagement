/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.model.table.UserMaintanceTableModel;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLUser {
    
    public static ObservableList<UserMaintanceTableModel> searchActiveUsers(String[] param) {
        ObservableList<UserMaintanceTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table22 WHERE col02 = 1";
        if (param.length > 0) {
            for (String param1 : param) {
                sql += " AND CONCAT("          
                        + "IFNULL(col03,''), "
                        + "IFNULL(col05,''), "
                        + "IFNULL(col08,'') "
                        + ") LIKE ?";
            }
        }
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                UserModel item = new UserModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setFirstName(rs.getString("col03"));
                item.setMiddleInitial(rs.getString("col04"));
                item.setLastName(rs.getString("col05"));
                item.setPhoneNumber(rs.getString("col06"));
                item.setEmailAddress(rs.getString("col07"));
                item.setUsername(rs.getString("col08"));
                item.setPassword(rs.getString("col09"));
                item.setPasswordSalt(rs.getString("col10"));
                item.setPasswordReset(rs.getString("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
                
                list.add(
                        new UserMaintanceTableModel(
                                item,
                                StringUtilities.buildUserName(item),
                                rs.getTimestamp("col12") == null 
                                        ? "Never Signed In" 
                                        : Global.getMmddyyyyhhmmssa().format(rs.getTimestamp("col12"))
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
        
    public static List<UserModel> getActiveUsers() {
        List<UserModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table22 WHERE col02 = 1";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                UserModel item = new UserModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setFirstName(rs.getString("col03"));
                item.setMiddleInitial(rs.getString("col04"));
                item.setLastName(rs.getString("col05"));
                item.setPhoneNumber(rs.getString("col06"));
                item.setEmailAddress(rs.getString("col07"));
                item.setUsername(rs.getString("col08"));
                item.setPassword(rs.getString("col09"));
                item.setPasswordSalt(rs.getString("col10"));
                item.setPasswordReset(rs.getString("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
                list.add(item);
            }
        } catch (SQLException ex) {
            DebugTools.Printout(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return list;
    }
    
    public static UserModel getUserByID(int id) {
        UserModel item = new UserModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table22 WHERE col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setFirstName(rs.getString("col03"));
                item.setMiddleInitial(rs.getString("col04"));
                item.setLastName(rs.getString("col05"));
                item.setPhoneNumber(rs.getString("col06"));
                item.setEmailAddress(rs.getString("col07"));
                item.setUsername(rs.getString("col08"));
                item.setPassword(rs.getString("col09"));
                item.setPasswordSalt(rs.getString("col10"));
                item.setPasswordReset(rs.getString("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
            }
        } catch (SQLException ex) {
            DebugTools.Printout(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return item;
    }
    
    public static void updateUserByID(UserModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table22 SET "
                + "col03 = ?, " //01 - FirstName
                + "col04 = ?, " //02 - MiddleInitial
                + "col05 = ?, " //03 - LastName
                + "col06 = ?, " //04 - Phone
                + "col07 = ?, " //05 - Email
                + "col08 = ?, " //06 - Username
                + "col16 = ?, " //07 - activeLogin
                + "col17 = ?, " //08 - AdminRights
                + "col18 = ? "  //09 - DefaultRate
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setString    ( 1, item.getFirstName());
            ps.setString    ( 2, item.getMiddleInitial());
            ps.setString    ( 3, item.getLastName());
            ps.setString    ( 4, item.getPhoneNumber());
            ps.setString    ( 5, item.getEmailAddress());
            ps.setString    ( 6, item.getUsername());
            ps.setBoolean   ( 7, item.isActiveLogin());
            ps.setBoolean   ( 8, item.isAdminRights());
            ps.setBigDecimal( 9, item.getDefaultRate());
            ps.setInt       (10, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
