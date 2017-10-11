/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.PartyNamePrefixModel;
import com.xln.xlncasemanagement.model.table.MaintenancePartyNamePrefixTableModel;
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
public class SQLPartyNamePrefix {
    
    public static ObservableList<MaintenancePartyNamePrefixTableModel> searchPartyNamePrefixs(String[] param) {
        ObservableList<MaintenancePartyNamePrefixTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table18 WHERE ";
        if (param.length > 0) {

            for (int i = 0; i < param.length; i++) {
                if (i > 0) {
                    sql += " AND";
                }
                sql += " col03 LIKE ?";
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
                PartyNamePrefixModel item = new PartyNamePrefixModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPrefix(rs.getString("col03"));

                list.add(
                        new MaintenancePartyNamePrefixTableModel(
                                item,
                                rs.getBoolean("col02"),
                                rs.getString("col03")
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
    
    public static int insertPartyNamePrefix(PartyNamePrefixModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table18 ("
                + "col02, "
                + "col03"
                + ") VALUES ("
                + "?, " //1
                + "?)";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setString(2, item.getPrefix());
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

    public static void updatePartyNamePrefixByID(PartyNamePrefixModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table18 SET "
                + "col02 = ?, "
                + "col03 = ? "
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getPrefix());
            ps.setInt    (3, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static List<PartyNamePrefixModel> getActivePartyNamePrefix() {
        List<PartyNamePrefixModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table18 WHERE col02 = 1";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PartyNamePrefixModel item = new PartyNamePrefixModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPrefix(rs.getString("col03"));
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
    
    
}
