/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.MatterModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            ps.setDate   (3, item.getOpenDate());
            ps.setDate   (4, item.getCloseDate());
                        
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
