/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.ReportParameterModel;
import com.xln.xlncasemanagement.model.table.MaintenanceReportParametersTableModel;
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
public class SQLReportParameter {
    
    public static ObservableList<MaintenanceReportParametersTableModel> getParameterList(int reportID) {
        ObservableList<MaintenanceReportParametersTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table21 WHERE col02 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reportID);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(
                        new MaintenanceReportParametersTableModel(
                                rs.getString("col01"),
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
    
    public static int insertReportParameter(ReportParameterModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table21 ("
                + "col02, "
                + "col03 "
                + ") VALUES ("
                + "?, "
                + "?)";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, item.getReportID());
            ps.setString(2, item.getReportParameter());
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
    
    public static void deleteReportParameter(int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM table21 WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
    }
    
    public static boolean checkExistingParameter(int reportID, String parameter){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) AS total FROM table21 WHERE col02 = ? AND col03 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reportID);
            ps.setString(2, parameter);
            rs = ps.executeQuery();

            if (rs.first()) {
                if (rs.getInt("total") > 0){
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
        return false;
    }
    
    public static List<String> getParameterListByReport(int reportID) {
        List<String> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT col03 FROM table21 WHERE col02 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reportID);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("col03"));
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
