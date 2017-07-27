/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.MakeModel;
import com.xln.xlncasemanagement.model.table.MaintenanceMakeTableModel;
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
public class SQLMake {
    
    public static ObservableList<MaintenanceMakeTableModel> searchMakes(String[] param) {
        ObservableList<MaintenanceMakeTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table24 WHERE ";
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
                MakeModel item = new MakeModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setName(rs.getString("col03"));
                item.setWebsite(rs.getString("col04"));

                list.add(
                        new MaintenanceMakeTableModel(
                                item,
                                rs.getBoolean("col02"),
                                rs.getString("col03"),
                                rs.getString("col04")
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
    
    public static int insertMake(MakeModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table24 ("
                + "col02, "
                + "col03, "
                + "col04 "
                + ") VALUES ("
                + "?, " //1
                + "?, " //2
                + "?)"; //3
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setString(2, item.getName());
            ps.setString(3, item.getWebsite());
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

    public static void updateMakeByID(MakeModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table24 SET "
                + "col02 = ?, "
                + "col03 = ?, "
                + "col04 = ? "
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getName());
            ps.setString (3, item.getWebsite());
            ps.setInt    (4, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static List<MakeModel> getActiveMake() {
        List<MakeModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table24 WHERE col02 = 1";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MakeModel item = new MakeModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setName(rs.getString("col03"));
                item.setWebsite(rs.getString("col04"));
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
    
    public static MakeModel geMakeByID(int id) {
        MakeModel item = new MakeModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table24 WHERE col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setName(rs.getString("col03"));
                item.setWebsite(rs.getString("col04"));
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
    
}
