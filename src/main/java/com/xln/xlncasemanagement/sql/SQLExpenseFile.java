/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.FileUtilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
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
public class SQLExpenseFile {
    
    public static boolean insertExpenseFile(int expenseID, File fileUpload) {
        Connection conn = null;
        PreparedStatement ps = null;
        byte[] fileInBytes = FileUtilities.fileToBytes(fileUpload);

        String sql = "INSERT INTO table20 ("
                + "col02, " //expense ID
                + "col03, " //File Name
                + "col04, " //Byte Block
                + "col05 " //SHA1 Checksum
                + ") VALUES (";
                for(int i=0; i<3; i++){
                        sql += "?, ";   //01-03
                    }
                sql += "?)"; //04
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt   (1, expenseID);
            ps.setString(2, fileUpload.getName());
            ps.setBytes (3, fileInBytes);
            ps.setString(4, FileUtilities.generateFileCheckSum(new ByteArrayInputStream(fileInBytes)));
            
            ps.executeUpdate();

            ResultSet newRow = ps.getGeneratedKeys();
            if (newRow.first()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return false;
    }
    
    public static boolean verifyFileChecksum(int id){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT col04, col05 "
                    + "FROM table20 WHERE col01 = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                return FileUtilities.compareCheckSum(rs.getBytes("col04"), rs.getString("col05"));
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return false;
    }
    
    public static File openExpenseFile(int expenseID) {
        File itemFile = null;        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT * FROM table20 WHERE col02 = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, expenseID);
            rs = ps.executeQuery();
            if (rs.first()) {                
                if (rs.getBytes("col04") != null) {
                    String fileName = rs.getString("col03");
                    InputStream is = rs.getBinaryStream("col04");
                    String checkSum = rs.getString("col05");
                    if (FileUtilities.compareCheckSum(rs.getBytes("col04"), rs.getString("col05"))){
                        itemFile = FileUtilities.generateFileFromBlobData(is, fileName, checkSum);
                    }
                }
            }
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return itemFile;
    }
    
}
