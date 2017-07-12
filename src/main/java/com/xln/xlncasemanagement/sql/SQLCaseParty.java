/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.CasePartyTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbutils.DbUtils;

/**
 *table04 in the database
 * @author User
 */
public class SQLCaseParty {
    
    public static ObservableList<CasePartyTableModel> searchParty(String[] param, int matterID) {
        ObservableList<CasePartyTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table04 WHERE active = 1 AND col18 = ?";
        if (param.length > 0) {
            for (String param1 : param) {
                sql += " AND col03 LIKE ?";
            }
        }

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, matterID);

            for (int i = 1; i < param.length + 1; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                PartyModel item = new PartyModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                
                item.setRelationID(rs.getInt("col03"));
                item.setPartyID(rs.getInt("col04"));
                item.setRelationName("");
                
                item.setPrefix(rs.getString("col05"));
                item.setFirstName(rs.getString("col06"));
                item.setMiddleInitial(rs.getString("col07"));
                item.setLastName(rs.getString("col08"));
                item.setAddressOne(rs.getString("col09"));
                item.setAddressTwo(rs.getString("col10"));
                item.setAddressThree(rs.getString("col11"));
                item.setCity(rs.getString("col12"));
                item.setState(rs.getString("col13"));
                item.setZip(rs.getString("col14"));
                item.setPhoneOne(rs.getString("col15"));
                item.setPhoneTwo(rs.getString("col16"));
                item.setEmail(rs.getString("col17"));
                item.setMatterID(rs.getInt("col18"));

//                list.add(
//                        new CasePartyTableModel(
//                                item,
//                                rs.getBoolean("col02"),
//                                StringUtilities.buildPartyName(item),
//                                StringUtilities.buildTableAddressBlock(item),
//                                NumberFormatService.convertStringToPhoneNumber(rs.getString("col13"))
//                        ));
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
    
    public static int insertCaseParty(PartyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table04 ("
                + "col02, " //01
                + "col03, " //02
                + "col04, " //03
                + "col05, " //04
                + "col06, " //05
                + "col07, " //06
                + "col08, " //07
                + "col09, " //08
                + "col11, " //09
                + "col12, " //10
                + "col13, " //11
                + "col14, " //12
                + "col14, " //13
                + "col15, " //14
                + "col16, " //15
                + "col17, " //16
                + "col18 "  //17
                + ") VALUES (";
                for (int i = 0; i < 16; i++) {
                    sql += "?, ";   //01-16
                }
                sql += "?)"; //17
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean( 1, item.isActive());
            ps.setInt    ( 2, item.getRelationID());
            ps.setInt    ( 3, item.getPartyID());
            ps.setString ( 4, item.getPrefix());
            ps.setString ( 5, item.getFirstName());
            ps.setString ( 6, item.getMiddleInitial());
            ps.setString ( 7, item.getLastName());
            ps.setString ( 8, item.getAddressOne());
            ps.setString ( 9, item.getAddressTwo());
            ps.setString (10, item.getAddressThree());
            ps.setString (11, item.getCity());
            ps.setString (12, item.getState());
            ps.setString (13, item.getZip());
            ps.setString (14, item.getPhoneOne());
            ps.setString (15, item.getPhoneTwo());
            ps.setString (16, item.getEmail());
            ps.setInt    (17, item.getMatterID());
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

    public static void updateCasePartyByID(PartyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table04 SET "
                + "col02 = ?, " //active
                + "col03 = ?, " //party Type ID
                + "col04 = ?, " //party ID
                + "col05 = ?, " //prefix
                + "col06 = ?, " //firstname
                + "col07 = ?, " //middleinitial
                + "col08 = ?, " //lastname
                + "col09 = ?, " //address1
                + "col10 = ?, " //address2
                + "col11 = ?, " //address3
                + "col12 = ?, " //city
                + "col13 = ?, " //state
                + "col14 = ?, " //zip
                + "col15 = ?, " //phone one
                + "col16 = ?, " //phone two
                + "col17 = ?, " //email     
                + "col18 = ? "  //matterID
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean( 1, item.isActive());
            ps.setInt    ( 2, item.getRelationID());
            ps.setInt    ( 3, item.getPartyID());
            ps.setString ( 4, item.getPrefix());
            ps.setString ( 5, item.getFirstName());
            ps.setString ( 6, item.getMiddleInitial());
            ps.setString ( 7, item.getLastName());
            ps.setString ( 8, item.getAddressOne());
            ps.setString ( 9, item.getAddressTwo());
            ps.setString (10, item.getAddressThree());
            ps.setString (11, item.getCity());
            ps.setString (12, item.getState());
            ps.setString (13, item.getZip());
            ps.setString (14, item.getPhoneOne());
            ps.setString (15, item.getPhoneTwo());
            ps.setString (16, item.getEmail());
            ps.setInt    (17, item.getMatterID());
            ps.setInt    (18, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
}