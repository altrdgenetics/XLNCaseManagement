/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.util.DebugTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                + "col05, "
                + "col06 "
                + ") VALUES ("
                + "?, " //1
                + "?, " //2
                + "?, " //3                
                + "?, " //4
                + "?)"; //5
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setInt    (2, item.getPartyID());
            ps.setInt    (3, item.getMatterTypeID());
            ps.setDate   (4, item.getOpenDate());
            ps.setDate   (5, item.getCloseDate());   
            ps.executeUpdate();

            ResultSet newRow = ps.getGeneratedKeys();
            if (newRow.next()) {
                return newRow.getInt(1);
            }
        } catch (SQLException ex) {
            DebugTools.Printout(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return 0;
    }
    
    public static List<MatterModel> getActiveMatters() {
        List<MatterModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT table15.*, table23.col03 AS matterName "
                + "FROM table15 "
                + "LEFT JOIN table23 ON table23.col01 = table15.col04 "
                + "WHERE table15.col02 = 1 AND table15.col03 = ? "
                + "ORDER BY table15.col05 DESC";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Global.getCurrentClient().getId());
            
            rs = ps.executeQuery();
            while (rs.next()) {
                MatterModel item = new MatterModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPartyID(rs.getInt("col03"));
                item.setMatterTypeID(rs.getInt("col04"));
                item.setMatterTypeName(rs.getString("matterName"));
                item.setOpenDate(rs.getDate("col05"));
                item.setCloseDate(rs.getDate("col06"));
                
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
    
    public static MatterModel getMatterByID(int id){
        MatterModel item = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT table15.*, table23.col03 AS matterName "
                + "FROM table15 "
                + "LEFT JOIN table23 ON table23.col01 = table15.col04 "
                + "WHERE table15.col03 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Global.getCurrentClient().getId());
            
            rs = ps.executeQuery();
            if (rs.first()) {
                item = new MatterModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setPartyID(rs.getInt("col03"));
                item.setMatterTypeID(rs.getInt("col04"));
                item.setMatterTypeName(rs.getString("matterName"));
                item.setOpenDate(rs.getDate("col05"));
                item.setCloseDate(rs.getDate("col06"));
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
