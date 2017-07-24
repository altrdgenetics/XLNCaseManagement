/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ExpenseModel;
import com.xln.xlncasemanagement.model.table.ExpensesTableModel;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        String sql = "SELECT table13.*, table14.col03 AS expenseType, table22.col03 as firstName, "
                + "table22.col04 as middleName, table22.col05 as lastName, table22.col08 as userName "
                + "FROM table13 "
                + "LEFT JOIN table14 ON table13.col04 = table14.col01 "
                + "LEFT JOIN table22 ON table13.col03 = table22.col01 "
                + "WHERE table13.col02 = 1 ";
        if (param.length > 0) {
            for (String param1 : param) {
                sql += " AND CONCAT("          
                        + "IFNULL(table13.col07,''), " // description
                        + "IFNULL(table14.col03,''), " // expense type
                        + "IFNULL(table22.col03,''), " // first name
                        + "IFNULL(table22.col05,''), " // last name
                        + "IFNULL(table22.col08,'') "  // username
                        + ") LIKE ? ";
            }
        }
        sql += " AND table13.col05 = ?";
        
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
                item.setUserID(rs.getInt("col03"));
                item.setExpenseType(rs.getInt("col04"));
                item.setExpenseTypeName(rs.getString("expenseType"));
                item.setMatterID(rs.getInt("col05"));
                item.setDateOccurred(rs.getDate("col06"));
                item.setDescription(rs.getString("col07"));
                item.setCost(rs.getDouble("col08"));
                item.setFileName(rs.getString("col09"));
                item.setInvoiced(rs.getBoolean("col10"));

                list.add(
                        new ExpensesTableModel(
                                item,  //Object
                                Global.getMmddyyyy().format(rs.getDate("col06")), //Date
                                StringUtilities.buildName(rs.getString("firstName"), rs.getString("middleName"), rs.getString("lastName")), //user
                                rs.getString("expenseType") + (rs.getString("col07") == null ? "" : " - " + rs.getString("col07")), //Description
                                rs.getDouble("col08") == 0 ? "N/A" : NumberFormatService.formatMoney(rs.getDouble("col08")), //Cost
                                rs.getString("col09"), //File
                                rs.getBoolean("col10") //Invoiced
                        )
                );
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
    
    public static int insertExpense(ExpenseModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table13 ("
                + "col02, " //active
                + "col03, " //user ID
                + "col04, " //expense type ID
                + "col05, " //matter ID
                + "col06, " //date Occurred
                + "col07, " //description
                + "col08, " //cost
                + "col09, " //file name
                + "col10 "  //invoiced
                + ") VALUES (";
                for(int i=0; i<8; i++){
                        sql += "?, ";   //01-08
                    }
                sql += "?)"; //09
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setInt    (2, item.getUserID());
            ps.setInt    (3, item.getExpenseType());
            ps.setInt    (4, item.getMatterID());
            ps.setDate   (5, item.getDateOccurred());
            ps.setString (6, item.getDescription());
            ps.setDouble (7, item.getCost());
            ps.setString (8, item.getFileName());
            ps.setBoolean(9, item.isInvoiced());
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
    
}