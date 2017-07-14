/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.ExpenseModel;
import com.xln.xlncasemanagement.model.table.ExpensesTableModel;
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
public class SQLExpense {
    
    public static ObservableList<ExpensesTableModel> searchExpenses(String[] param, int matterID) {
        ObservableList<ExpensesTableModel> list = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT table04.*, table17.col03 AS relationName "
                + "FROM table04 "
                + "LEFT JOIN table17 ON table04.col03 = table17.col01 "
                + "WHERE table04.col02 = 1 ";
        if (param.length > 0) {
            for (String param1 : param) {
                sql += " AND CONCAT("          
                        + "IFNULL(table04.col06,''), "
                        + "IFNULL(table04.col08,''), "
                        + "IFNULL(table04.col09,''), "
                        + "IFNULL(table04.col10,''), "
                        + "IFNULL(table04.col11,''), "
                        + "IFNULL(table04.col12,''), "
                        + "IFNULL(table04.col14,''), "
                        + "IFNULL(table04.col15,'') "
                        + ") LIKE ? ";
            }
        }
        sql += " AND table04.col18 = ?";
        
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ps.setInt(param.length + 1, matterID);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                ExpenseModel item = new ExpenseModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setUserID(0);
                item.setExpenseType(0);
                item.setExpenseTypeName("");
                item.setDateOccurred(null);
                item.setDescription("");
                item.setCost(0);
                item.setFileName("");
                item.setInvoiced(false);

//                list.add(
//                        new ExpensesTableModel(
//                                item,  //Object
//                                "date", //Date
//                                "", //Description
//                                "", //Cost
//                                null, //File
//                                false//Invoiced?
//                        )
//                );
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
