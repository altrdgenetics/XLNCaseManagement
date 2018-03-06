/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.model.sql.ConfigModel;
import com.xln.xlncasemanagement.util.DebugTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author User
 */
public class SQLConfig {
    
    public static ConfigModel getConfigByCompanyID(String key) {
        ConfigModel item = new ConfigModel();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT "
                + "maxusers, "
                + "dbtype, "
                + "dburl, "
                + "dbname, "
                + "dbusername, "
                + "dbpassword, "
                + "liteversion, "
                + "applicationtype, "
                + "contractenddate,"
                + "usessl, "
                + "certificate "
                + "FROM company WHERE companykey = ?";

        try {
            conn = DBConnection.connectToConfigDB();
            ps = conn.prepareStatement(sql);
            ps.setString(1, key);
            rs = ps.executeQuery();
            if (rs.first()) {
                item.setMaxusers(rs.getInt("maxusers"));
                item.setDbtype(rs.getString("dbtype"));
                item.setDburl(rs.getString("dburl"));
                item.setDbname(rs.getString("dbname"));
                item.setDbusername(rs.getString("dbusername"));
                item.setDbpassword(rs.getString("dbpassword"));
                item.setApplicationtype(rs.getString("applicationtype"));
                item.setLiteVersion(rs.getBoolean("liteversion"));
                item.setContractenddate(rs.getDate("contractenddate"));
                item.setUseSSL(rs.getBoolean("usessl"));
                item.setServerCertificate(rs.getString("certificate"));
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
    
}
