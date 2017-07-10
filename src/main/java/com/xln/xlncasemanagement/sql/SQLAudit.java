/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityTypeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLAudit {
    
    public static int insertActivityType(String action) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table03 ("
                + "col02, " //01
                + "col03, " //02
                + "col04, " //03
                + "col05, " //04
                + "col06 "  //05
                + ") VALUES (";
                for(int i=0; i<4; i++){
                        sql += "?, ";   //01-04
                    }
                sql += "?)"; //05
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setString(2, Global.getCurrentUser().getUsername());
            ps.setString(3, Global.getCurrentUser().getLastLoginPCName());
            ps.setString(4, Global.getCurrentUser().getLastLoginIP());
            ps.setString(5, action == null ? "MISSING ACTION" : action);
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
