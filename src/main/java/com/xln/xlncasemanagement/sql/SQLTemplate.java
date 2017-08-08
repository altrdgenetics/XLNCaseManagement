/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.model.table.MaintenanceTemplateTableModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        String sql = "SELECT col01, col02, col03, col04 FROM table10 WHERE ";
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
    
    public static int insertTemplate(TemplateModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO table10 ("
                + "col02, "
                + "col03, "
                + "col04 "
                + ") VALUES ("
                + "?, " //1
                + "?, " //2
                + "?)";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, item.isActive());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
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

    public static void updateTemplateByID(TemplateModel item) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "UPDATE table10 SET "
                + "col02 = ?, "
                + "col03 = ?, "
                + "col04 = ? "
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, item.isActive());
            ps.setString (2, item.getName());
            ps.setString (3, item.getDescription());
            ps.setInt    (4, item.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(conn);
        }
    }

    public static List<TemplateModel> getActiveTemplate() {
        List<TemplateModel> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT col01, col02, col03, col04 FROM table10 WHERE col02 = 1";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TemplateModel item = new TemplateModel();
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setName(rs.getString("col03"));
                item.setDescription(rs.getString("col04"));
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
    
    public static TemplateModel getTemplateByID(int id) {
        TemplateModel item = new TemplateModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM table10 WHERE col01 = ?";

        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                item.setId(rs.getInt("col01"));
                item.setActive(rs.getBoolean("col02"));
                item.setName(rs.getString("col03"));
                item.setDescription(rs.getString("col04"));
                item.setFileName(rs.getString("col05"));
                item.setFileBlob(rs.getBytes("col06"));
                item.setFileBlobHash(rs.getString("col07"));
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
    
    public static boolean insertTemplateFile(int templateID, File fileUpload) {
        
        Connection conn = null;
        PreparedStatement ps = null;
        byte[] fileInBytes = FileUtilities.fileToBytes(fileUpload);

        String sql = "UPDATE table10 SET "
                + "col05 = ?, " //File Name
                + "col06 = ?, " //Byte Block
                + "col07 = ? " //SHA1 Checksum
                + "WHERE col01 = ?";
        try {
            conn = DBConnection.connectToDB();
            ps = conn.prepareStatement(sql);
            ps.setString(1, fileUpload.getName());
            ps.setBytes (2, fileInBytes);
            ps.setString(3, FileUtilities.generateFileCheckSum(new ByteArrayInputStream(fileInBytes)));
            ps.setInt   (4, templateID);
            
            ps.executeUpdate();
            return true;
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
            String sql = "SELECT col06, col07 "
                    + "FROM table10 WHERE col01 = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                return FileUtilities.compareCheckSum(rs.getBytes("col06"), rs.getString("col07"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLExpense.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return false;
    }
    
    public static File openTemplateFile(int templateID) {
        File itemFile = null;        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT * FROM table10 WHERE col01 = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, templateID);
            rs = ps.executeQuery();
            if (rs.first()) {                
                if (rs.getBytes("col06") != null) {
                    String fileName = rs.getString("col05");
                    InputStream is = rs.getBinaryStream("col06");
                    String checkSum = rs.getString("col07");
                    if (FileUtilities.compareCheckSum(rs.getBytes("col06"), rs.getString("col07"))){
                        itemFile = FileUtilities.generateFileFromBlobData(is, fileName, checkSum);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLActivity.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return itemFile;
    }
       
}
