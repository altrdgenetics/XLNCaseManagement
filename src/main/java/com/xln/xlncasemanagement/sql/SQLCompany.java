/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.CompanyModel;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.FileUtilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.Image;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * This corresponds to Table9 in the database.
 * 
 * @author User
 */
public class SQLCompany {
    
    public static CompanyModel getCompanyInformation() {
        CompanyModel item = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT * "
                    + "FROM table09 "
                    + "LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.first()) {
                item = new CompanyModel();
                item.setId(rs.getInt("col01"));
                item.setName(rs.getString("col02"));
                item.setAddressOne(rs.getString("col03"));
                item.setAddressTwo(rs.getString("col04"));
                item.setAddressThree(rs.getString("col05"));
                item.setCity(rs.getString("col06"));
                item.setState(rs.getString("col07"));
                item.setZip(rs.getString("col08"));
                item.setWebsite(rs.getString("col09"));
                item.setPhone(rs.getString("col10"));
                item.setFax(rs.getString("col11"));
                item.setEmail(rs.getString("col12"));
                
                if (rs.getBytes("col13") != null) {
                    Image img = new Image(new ByteArrayInputStream(rs.getBytes("col13")));
                    item.setLogo(img);
                }
                item.setLogoSH1(rs.getString("col14"));
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
        
    public static boolean updateCompanyInformation(CompanyModel item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB();

            String sql = "UPDATE table09 SET "
                    + "col02 = ?, " //Name
                    + "col03 = ?, " //Address1
                    + "col04 = ?, " //Address2
                    + "col05 = ?, " //Address3
                    + "col06 = ?, " //City
                    + "col07 = ?, " //State
                    + "col08 = ?, " //Zip
                    + "col09 = ?, " //Website
                    + "col10 = ?, " //Phone
                    + "col11 = ?, " //Fax
                    + "col12 = ? "  //Email
                    + "WHERE col01 = ?"; //ID

            ps = conn.prepareStatement(sql);            
            ps.setString(1, item.getName());
            ps.setString(2, item.getAddressOne());
            ps.setString(3, item.getAddressTwo());
            ps.setString(4, item.getAddressThree());
            ps.setString(5, item.getCity());
            ps.setString(6, item.getState());
            ps.setString(7, item.getZip());
            ps.setString(8, item.getWebsite());
            ps.setString(9, item.getPhone());
            ps.setString(10, item.getFax());
            ps.setString(11, item.getEmail());
            ps.setInt(12, item.getId());
            ps.executeUpdate();       
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
            return false;
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return true;
    }
    
    public static boolean updateCompanyLogo(File image) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.connectToDB();

            String sql = "UPDATE table09 SET col13 = ?, col14 = ? WHERE col01 = ?";

            ps = conn.prepareStatement(sql);
            
            byte[] imageFile = FileUtilities.companyLogoFileToBytes(image);
            
            ps.setBytes(1, imageFile);
            ps.setString(2, FileUtilities.generateFileCheckSum(new ByteArrayInputStream(imageFile)));
            ps.setInt(3, Global.getCompanyInformation().getId());
            ps.executeUpdate();       
            
            if (verifyImageChecksum()) { 
                DebugTools.HandleInfoPrintout("Image Inserted Successfully");
                return true;
            }
            
        } catch (SQLException ex) {
            DebugTools.HandleException(ex);
            return false;
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
        return true;
    }
    
    public static boolean verifyImageChecksum(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.connectToDB();
            String sql = "SELECT col13, col14 "
                    + "FROM table09 "
                    + "LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.first()) {
                return FileUtilities.compareCheckSum(rs.getBytes("col13"), rs.getString("col14"));
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
    
}
