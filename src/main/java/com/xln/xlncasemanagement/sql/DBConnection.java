/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sql;

import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Andrew
 */
public class DBConnection {

    /**
     * Gets the connection for the default database. 
     * 
     * @return
     */
    public static Connection connectToDB() {
        Connection conn = null;
        int nbAttempts = 0;
        while (true) {
            try {
                Class.forName(DBCInfo.getDBdriver());
                conn = DriverManager.getConnection(DBCInfo.getDBurl() + DBCInfo.getDBname(), DBCInfo.getDBusername(), DBCInfo.getDBpassword());
                break;
            } catch (ClassNotFoundException | SQLException e) {
                nbAttempts++;
                if (nbAttempts > 0) {
                    DebugTools.Printout(StringUtilities.currentTime()
                            + " - Unable to connect to server. Trying again shortly.");
                }
                try {
                    DebugTools.Printout("Sleeping for: " + 3000 * nbAttempts + "ms");
                    Thread.sleep(3000 * nbAttempts);
                } catch (InterruptedException exi) {
                    System.err.println(exi.getMessage());
                }
            }
        }
        return conn;
    }
    
    /**
     * Gets the connection for backing up the database.
     * 
     * @return
     */
    public static Connection connectToConfigDB() {
        Connection conn = null;
        int nbAttempts = 0;
        while (true) {
            try {
                Class.forName(DBCInfo.getDBConfigDriver());
                conn = DriverManager.getConnection(DBCInfo.getDBConfigUrl() + DBCInfo.getDBConfigName(), DBCInfo.getDBConfigUsername(), DBCInfo.getDBConfigPassword());
                break;
            } catch (ClassNotFoundException | SQLException e) {
                nbAttempts++;
                if (nbAttempts > 0) {
                    DebugTools.Printout(StringUtilities.currentTime()
                            + " - Unable to connect to config server. Trying again shortly.");
                }
                try {
                    DebugTools.Printout("Sleeping for: " + 3000 * nbAttempts + "ms");
                    Thread.sleep(3000 * nbAttempts);
                } catch (InterruptedException exi) {
                    System.err.println(exi.getMessage());
                }
            }
        }
        return conn;
    }
    
}
