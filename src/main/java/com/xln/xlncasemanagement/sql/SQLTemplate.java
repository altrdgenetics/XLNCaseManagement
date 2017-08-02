/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.model.table.MaintenanceTemplateTableModel;
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
public class SQLTemplate {
    
    public static ObservableList<MaintenanceTemplateTableModel> searchTemplates(String[] param) {
        ObservableList<MaintenanceTemplateTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM table10 WHERE ";
        if (param.length > 0) {

            for (int i = 0; i < param.length; i++) {
                if (i > 0) {
                    sql += " AND ";
                }
                sql += " CONCAT("          
                        + "IFNULL(table10.col03,''), "
                        + "IFNULL(table10.col04,'') "
                        + ") LIKE ? ";
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
                TemplateModel item = new TemplateModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setName(rs.getString("col03"));
                item.setDescription(rs.getString("col04"));

                list.add(
                        new MaintenanceTemplateTableModel(
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
    
}
