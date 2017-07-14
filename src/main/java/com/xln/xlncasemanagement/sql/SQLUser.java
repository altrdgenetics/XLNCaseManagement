/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.model.table.UserMaintanceTableModel;
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
                
                list.add(
                        new UserMaintanceTableModel(
                                item,
                                StringUtilities.buildUserName(item),
                                rs.getTimestamp("col12") == null ? "Never Signed In" : Global.getMmddyyyyhhmmssa().format(rs.getTimestamp("col12"))
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
    
}
