/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.util.DebugTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLActiveStatus {
        
    public static boolean setActive(String table, int id, boolean active) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB();

            String sql = "UPDATE " + table + " SET col02 = ? WHERE col01 = ?";

            ps = conn.prepareStatement(sql);            
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            ps.executeUpdate();       
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
            return false;
        } finally {
            DebugTools.HandleInfoPrintout("Updated " + table + ": " + id + " active status to " + active);
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return true;
    }
    
}
