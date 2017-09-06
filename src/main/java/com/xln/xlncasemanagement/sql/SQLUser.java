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
import java.sql.Statement;
import java.sql.Timestamp;
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
                item.setPasswordSalt(rs.getLong("col10"));
                item.setPasswordReset(rs.getBoolean("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
                item.setAccountLocked(rs.getBoolean("col19"));
                
                list.add(
                        new UserMaintanceTableModel(
                                item,
                                StringUtilities.buildUserNameWithUserName(item),
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
                item.setPasswordSalt(rs.getLong("col10"));
                item.setPasswordReset(rs.getBoolean("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
                item.setAccountLocked(rs.getBoolean("col19"));
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
                item.setPasswordSalt(rs.getLong("col10"));
                item.setPasswordReset(rs.getBoolean("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
                item.setAccountLocked(rs.getBoolean("col19"));
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
    
    public static UserModel getUserByUserName(String UserName) {
        UserModel item = new UserModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table22 WHERE col08 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setString(1, UserName);
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
                item.setPasswordSalt(rs.getLong("col10"));
                item.setPasswordReset(rs.getBoolean("col11"));
                item.setLastLoginDateTime(rs.getTimestamp("col12"));
                item.setLastLoginPCName(rs.getString("col13"));
                item.setLastLoginIP(rs.getString("col14"));
                item.setLastMatterID(rs.getInt("col15"));
                item.setActiveLogin(rs.getBoolean("col16"));
                item.setAdminRights(rs.getBoolean("col17"));
                item.setDefaultRate(rs.getBigDecimal("col18"));
                item.setAccountLocked(rs.getBoolean("col19"));
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
        
    public static void updateUserPasswordByID(int id, String password, long passwordSalt, boolean passReset) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table22 SET "
                + "col09 = ?, " //01 - password
                + "col10 = ?, " //02 - salt     
                + "col11 = ?, " //03 - password Reset 
                + "col19 = ? "  //04 - Account Lock
                + "WHERE col01 = ?"; //04 - User ID
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setString (1, password);
            ps.setLong   (2, passwordSalt);
            ps.setBoolean(3, passReset);
            ps.setBoolean(4, false);
            ps.setInt    (5, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void removeUserActiveLoginStatus(int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table22 SET col16 = ? WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setInt    (2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void updateUserLocationByID(int id, String pcName, String pcIP) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table22 SET "
                + "col13 = ?, "  //01 - PC Name
                + "col14 = ?, "  //02 - PC IP  
                + "col12 = ?, "  //03 - Login Date/Time
                + "col16 = ? "   //04 - Active Login
                + "WHERE col01 = ?"; //05 - User ID
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setString   (1, pcName);
            ps.setString   (2, pcIP);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setBoolean  (4, true);
            ps.setInt      (5, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static int insertUser(UserModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table22 ("
                + "col02, " // 01 - active
                + "col03, " // 02 - firstname
                + "col04, " // 03 - middleinitial
                + "col05, " // 04 - lastname
                + "col06, " // 05 - phone
                + "col07, " // 06 - email
                + "col08, " // 07 - username
                + "col09, " // 08 - password
                + "col10, " // 09 - passwordsalt
                + "col11, " // 10 - passwordreset
                + "col17, " // 11 - admin
                + "col18, " // 12 - default rate
                + "col19 "  // 13 - Account Lock
                + ") VALUES (";
                for(int i=0; i<12; i++){
                        sql += "?, ";   //01-12
                    }
                sql += "?)"; //13
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean   ( 1, true);
            ps.setString    ( 2, item.getFirstName());
            ps.setString    ( 3, item.getMiddleInitial());
            ps.setString    ( 4, item.getLastName());
            ps.setString    ( 5, item.getPhoneNumber());
            ps.setString    ( 6, item.getEmailAddress());
            ps.setString    ( 7, item.getUsername());
            ps.setString    ( 8, item.getPassword());
            ps.setLong      ( 9, item.getPasswordSalt());
            ps.setBoolean   (10, item.isPasswordReset());
            ps.setBoolean   (11, item.isAdminRights());
            ps.setBigDecimal(12, item.getDefaultRate());
            ps.setBoolean   (13, false);
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
    
    public static void lockUserAccounts(String[] lockedUsersList) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table22 SET "
                + "col19 = ? " //04 - Account Lock
                + "WHERE  ("; //04 - User ID

            for (int i = 0; i < lockedUsersList.length; i++) {
                if (i > 0) {
                    sql += " OR ";
                }
                sql += " col08 = ?";
            }
            sql += ")";
        
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            
            for (int i = 1; i < (lockedUsersList.length + 1); i++) {
                ps.setString(i, "%" + lockedUsersList[i].trim() + "%");
            }
            
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}
