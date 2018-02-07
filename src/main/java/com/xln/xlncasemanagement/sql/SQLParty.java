/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.PartyTableModel;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
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
 * table16 in the database
 * @author User
 */
public class SQLParty {
    
    public static ObservableList<PartyTableModel> searchParty(String[] param, boolean maintenanceMode) {
        ObservableList<PartyTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table16 WHERE ";
        if (param.length > 0) {

            for (int i = 0; i < param.length; i++) {
                if (i > 0) {
                    sql += " AND";
                }
                sql += " CONCAT("          
                        + "IFNULL(col04,''), "
                        + "IFNULL(col06,''), "
                        + "IFNULL(col07,''), "
                        + "IFNULL(col08,''), "
                        + "IFNULL(col09,''), "
                        + "IFNULL(col10,''), "
                        + "IFNULL(col12,''), "
                        + "IFNULL(col13,'') "
                        + ") LIKE ?";
            }
        }
        if (!maintenanceMode || !Global.getCurrentUser().isAdminRights()){
            sql += param.length > 0 ? " AND " : " ";
            sql += " col02 = 1 ";
        }
        
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                PartyModel item = new PartyModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPrefix(rs.getString("col03"));
                item.setFirstName(rs.getString("col04"));
                item.setMiddleInitial(rs.getString("col05"));
                item.setLastName(rs.getString("col06"));
                item.setAddressOne(rs.getString("col07"));
                item.setAddressTwo(rs.getString("col08"));
                item.setAddressThree(rs.getString("col09"));
                item.setCity(rs.getString("col10"));
                item.setState(rs.getString("col11"));
                item.setZip(rs.getString("col12"));
                item.setPhoneOne(rs.getString("col13"));
                item.setPhoneTwo(rs.getString("col14"));
                item.setEmail(rs.getString("col15"));

                list.add(
                        new PartyTableModel(
                                item,
                                rs.getBoolean("col02"),
                                StringUtilities.buildPartyName(item),
                                StringUtilities.buildTableAddressBlock(item),
                                NumberFormatService.convertStringToPhoneNumber(rs.getString("col13"))
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
    
    public static int insertParty(PartyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table16 ("
                + "col02, " //01
                + "col03, " //02
                + "col04, " //03
                + "col05, " //04
                + "col06, " //05
                + "col07, " //06
                + "col08, " //07
                + "col09, " //08
                + "col10, " //09
                + "col11, " //10
                + "col12, " //11
                + "col13, " //12
                + "col14, " //13
                + "col15 "  //14
                + ") VALUES (";
                for(int i=0; i<13; i++){
                        sql += "?, ";   //01-13
                    }
                sql += "?)"; //14
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setString( 2, item.getPrefix());
            ps.setString( 3, item.getFirstName());
            ps.setString( 4, item.getMiddleInitial());
            ps.setString( 5, item.getLastName());
            ps.setString( 6, item.getAddressOne());
            ps.setString( 7, item.getAddressTwo());
            ps.setString( 8, item.getAddressThree());
            ps.setString( 9, item.getCity());
            ps.setString(10, item.getState());
            ps.setString(11, item.getZip());
            ps.setString(12, item.getPhoneOne());
            ps.setString(13, item.getPhoneTwo());
            ps.setString(14, item.getEmail());
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

    public static void updatePartyByID(PartyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table16 SET "
                + "col02 = ?, " //active
                + "col03 = ?, " //prefix
                + "col04 = ?, " //firstname
                + "col05 = ?, " //middleinitial
                + "col06 = ?, " //lastname
                + "col07 = ?, " //address1
                + "col08 = ?, " //address2
                + "col09 = ?, " //address3
                + "col10 = ?, " //city
                + "col11 = ?, " //state
                + "col12 = ?, " //zip
                + "col13 = ?, " //phone one
                + "col14 = ?, " //phone two
                + "col15 = ? " //email                
                + "WHERE col01 = ?"; //ID
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean( 1, item.isActive());
            ps.setString ( 2, item.getPrefix());
            ps.setString ( 3, item.getFirstName());
            ps.setString ( 4, item.getMiddleInitial());
            ps.setString ( 5, item.getLastName());
            ps.setString ( 6, item.getAddressOne());
            ps.setString ( 7, item.getAddressTwo());
            ps.setString ( 8, item.getAddressThree());
            ps.setString ( 9, item.getCity());
            ps.setString (10, item.getState());
            ps.setString (11, item.getZip());
            ps.setString (12, item.getPhoneOne());
            ps.setString (13, item.getPhoneTwo());
            ps.setString (14, item.getEmail());
            ps.setInt    (15, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static List<PartyModel> getActiveClients() {
        List<PartyModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT table15.col03 AS link, table16.* "
                + "FROM table16 "
                + "LEFT JOIN table15 ON table16.col01 = table15.col03 "
                + "WHERE table15.col02 = 1 "
                + "ORDER BY table16.col06 ASC";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PartyModel item = new PartyModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPrefix(rs.getString("col03"));
                item.setFirstName(rs.getString("col04"));
                item.setMiddleInitial(rs.getString("col05"));
                item.setLastName(rs.getString("col06"));
                item.setAddressOne(rs.getString("col07"));
                item.setAddressTwo(rs.getString("col08"));
                item.setAddressThree(rs.getString("col09"));
                item.setCity(rs.getString("col10"));
                item.setState(rs.getString("col11"));
                item.setZip(rs.getString("col12"));
                item.setPhoneOne(rs.getString("col13"));
                item.setPhoneTwo(rs.getString("col14"));
                item.setEmail(rs.getString("col15"));
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
    
    public static PartyModel getClientByID(int id) {
        PartyModel item = new PartyModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table16 WHERE table16.col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPrefix(rs.getString("col03"));
                item.setFirstName(rs.getString("col04"));
                item.setMiddleInitial(rs.getString("col05"));
                item.setLastName(rs.getString("col06"));
                item.setAddressOne(rs.getString("col07"));
                item.setAddressTwo(rs.getString("col08"));
                item.setAddressThree(rs.getString("col09"));
                item.setCity(rs.getString("col10"));
                item.setState(rs.getString("col11"));
                item.setZip(rs.getString("col12"));
                item.setPhoneOne(rs.getString("col13"));
                item.setPhoneTwo(rs.getString("col14"));
                item.setEmail(rs.getString("col15"));
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
