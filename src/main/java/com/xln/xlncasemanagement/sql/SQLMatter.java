/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLMatter {
    
    public static int insertNewMatter(MatterModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table15 ("
                + "col02, "
                + "col03, "
                + "col04, "
                + "col05 "
                + ") VALUES ("
                + "?, " //1
                + "?, " //2
                + "?, " //3   
                + "?)"; //4
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setInt    (2, item.getPartyID());
            ps.setInt    (3, item.getMatterTypeID());
            ps.setDate   (4, item.getOpenDate());
            ps.executeUpdate();

            ResultSet newRow = ps.getGeneratedKeys();
            if (newRow.next()) {
                return newRow.getInt(1);
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return 0;
    }
    
    public static List<MatterModel> getActiveMattersByClient(int client) {
        List<MatterModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT table15.*, table23.col03 AS matterName, table24.col03 AS makeName, table25.col04 AS modelName "
                + "FROM table15 "
                + "LEFT JOIN table23 ON table23.col01 = table15.col04 "
                + "LEFT JOIN table24 ON table24.col01 = table15.col09 "
                + "LEFT JOIN table25 ON table25.col01 = table15.col10 "
                + "WHERE table15.col02 = 1 AND table15.col03 = ? "
                + "ORDER BY table15.col05 DESC";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, client);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                MatterModel item = new MatterModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPartyID(rs.getInt("col03"));
                item.setMatterTypeID(rs.getInt("col04"));
                item.setMatterTypeName(rs.getInt("col04") == 0 ? Global.getLeadWording() : rs.getString("matterName"));
                item.setOpenDate(rs.getDate("col05"));
                item.setCloseDate(rs.getDate("col06"));
                item.setNote(rs.getString("col07"));
                item.setWarranty(rs.getDate("col08"));
                item.setMake(rs.getInt("col09"));
                item.setMakeName(rs.getString("makeName"));
                item.setModel(rs.getInt("col10"));
                item.setModelName(rs.getString("modelName"));
                item.setSerial(rs.getString("col11"));
                item.setBudget(rs.getBigDecimal("col12"));
                
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
    
    public static MatterModel getMatterByID(int id){
        MatterModel item = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT "
                + "table15.*, "
                + "table23.col03 AS matterName, "
                + "table24.col03 AS makeName, "
                + "table24.col04 AS makeWebsite, "
                + "table25.col04 AS modelName, "
                + "table25.col05 AS modelWebsite "
                + "FROM table15 "
                + "LEFT JOIN table23 ON table23.col01 = table15.col04 "
                + "LEFT JOIN table24 ON table24.col01 = table15.col09 "
                + "LEFT JOIN table25 ON table25.col01 = table15.col10 "
                + "WHERE table15.col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            if (rs.first()) {
                item = new MatterModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPartyID(rs.getInt("col03"));
                item.setMatterTypeID(rs.getInt("col04"));
                item.setMatterTypeName(rs.getInt("col04") == 0 ? Global.getLeadWording() : rs.getString("matterName"));
                item.setOpenDate(rs.getDate("col05"));
                item.setCloseDate(rs.getDate("col06"));
                item.setNote(rs.getString("col07"));
                item.setWarranty(rs.getDate("col08"));
                item.setMake(rs.getInt("col09"));
                item.setMakeName(rs.getString("makeName"));
                item.setMakeWebsite(rs.getString("makeWebsite"));
                item.setModel(rs.getInt("col10"));
                item.setModelName(rs.getString("modelName"));
                item.setModelWebsite(rs.getString("modelWebsite"));
                item.setSerial(rs.getString("col11"));
                item.setBudget(rs.getBigDecimal("col12"));
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
    
    public static void updateMatterTypeByID(int matterType, int matterID) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table15 SET col04 = ? WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, matterType);
            ps.setInt(2, matterID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void updateMatterInformationByID(MatterModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table15 SET "
                + "col05 = ?, " //01 - Open Date
                + "col06 = ?, " //02 - Close Date
                + "col08 = ?, " //03 - Warranty
                + "col09 = ?, " //04 - Make
                + "col10 = ?, " //05 - Model
                + "col11 = ? "  //06 - Serial
                + "WHERE col01 = ?"; //08
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setDate      (1, item.getOpenDate());
            ps.setDate      (2, item.getCloseDate());
            ps.setDate      (3, item.getWarranty());
            ps.setInt       (4, item.getMake());
            ps.setInt       (5, item.getModel());
            ps.setString    (6, item.getSerial());
            ps.setInt       (7, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void updateMatterPaymentByID(MatterModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table15 SET col12 = ? WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, item.getBudget());
            ps.setInt       (2, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public static void updateMatterNoteByID(MatterModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table15 SET col07 = ? WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setString(1, item.getNote());
            ps.setInt   (2, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static HashMap getSummaryByMatterID(int id) {
        HashMap hm = new HashMap();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT "
                + "activity.totalActivityHour, "
                + "activity.billedActivityHour, "
                + "activity.unBilledActivityHour, "
                + "activity.totalActivityAmount, "
                + "activity.billedActivityAmount, "
                + "activity.unBilledActivityAmount, "
                + "expense.totalExpenseAmount, "
                + "expense.billedExpenseAmount, "
                + "expense.unBilledExpenseAmount "
                + "FROM "
                + "(SELECT "
                + "IFNULL(SUM(table01.col09), 0) AS totalActivityAmount, "
                + "IFNULL(SUM(IF(table01.col12 = 1, table01.col09, 0)), 0) as billedActivityAmount, "
                + "IFNULL(SUM(IF(table01.col12 = 0, table01.col09, 0)), 0) as unBilledActivityAmount, "
                + "IFNULL(SUM(table01.col07), 0) AS totalActivityHour,  "
                + "IFNULL(SUM(IF(table01.col12 = 1, table01.col07, 0)), 0) as billedActivityHour,  "
                + "IFNULL(SUM(IF(table01.col12 = 0, table01.col07, 0)), 0) as unBilledActivityHour "
                + "FROM table01 "
                + "WHERE table01.col02 = 1 "
                + "AND table01.col11 = 1 "
                + "AND table01.col05 = ? "
                + ") AS activity "
                + "INNER JOIN "
                + "(SELECT "
                + "IFNULL(SUM(table13.col08), 0) AS totalExpenseAmount, "
                + "IFNULL(SUM(IF(table13.col09 = 1, table13.col08, 0)), 0) as billedExpenseAmount, "
                + "IFNULL(SUM(IF(table13.col09 = 0, table13.col08, 0)), 0) as unBilledExpenseAmount "
                + "FROM table13 "
                + "WHERE table13.col02 = 1 "
                + "AND table13.col05 = ? "
                + ") AS expense";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                hm.put("totalExpenseAmount", NumberFormatService.formatMoney(rs.getBigDecimal("totalExpenseAmount")));
                hm.put("billedExpenseAmount", NumberFormatService.formatMoney(rs.getBigDecimal("billedExpenseAmount")));
                hm.put("unBilledExpenseAmount", NumberFormatService.formatMoney(rs.getBigDecimal("unBilledExpenseAmount")));
                hm.put("totalActivityAmount", NumberFormatService.formatMoney(rs.getBigDecimal("totalActivityAmount")));
                hm.put("billedActivityAmount", NumberFormatService.formatMoney(rs.getBigDecimal("billedActivityAmount")));
                hm.put("unbilledActivityAmount", NumberFormatService.formatMoney(rs.getBigDecimal("unbilledActivityAmount")));
                hm.put("totalActivityHour", rs.getString("totalActivityHour"));
                hm.put("billedActivityHour", rs.getString("billedActivityHour"));
                hm.put("unBilledActivityHour", rs.getString("unBilledActivityHour"));
                String total = NumberFormatService.formatMoney(
                        rs.getBigDecimal("totalExpenseAmount").add(rs.getBigDecimal("totalActivityAmount")));
                String unbilled = NumberFormatService.formatMoney(
                        rs.getBigDecimal("billedExpenseAmount").add(rs.getBigDecimal("billedActivityAmount")));
                String billed = NumberFormatService.formatMoney(
                        rs.getBigDecimal("unBilledExpenseAmount").add(rs.getBigDecimal("unbilledActivityAmount")));
                
                hm.put("totalTotalAmount", total);
                hm.put("totalBilledAmount", unbilled);
                hm.put("totalUnBilledAmount", billed);
            } else {
                hm.put("totalExpenseAmount", "");
                hm.put("billedExpenseAmount", "");
                hm.put("unBilledExpenseAmount", "");
                hm.put("totalActivityHour", "");
                hm.put("billedActivityHour", "");
                hm.put("unBilledActivityHour", "");
                hm.put("totalTotalAmount", "");
                hm.put("totalBilledAmount", "");
                hm.put("totalUnBilledAmount", "");
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return hm;
    }
    
}
