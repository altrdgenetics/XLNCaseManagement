/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.CompanyModel;
import com.xln.xlncasemanagement.util.FileUtilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                item.setAddress1(rs.getString("col03"));
                item.setAddress2(rs.getString("col04"));
                item.setAddress3(rs.getString("col05"));
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
                System.out.println("Grabbed company info successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLCompany.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(rs);
        }
        return item;
    }
    
    
    
    /**
     * No Longer Used, test for insert of image.
     */
    @Deprecated
    public static void insertimage() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = DBConnection.connectToDB();

            String sql = "UPDATE table09 SET col13 = ? WHERE col01 = 1";

            ps = conn.prepareStatement(sql);
            
            ps.setBytes(1, FileUtilities.ImageFileToBytes(new File("C:\\Users\\User\\Desktop\\scale-40635_640.png")));
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLCompany.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Success");
            DbUtils.closeQuietly(conn);
            DbUtils.closeQuietly(ps);
        }
    }
    
}
