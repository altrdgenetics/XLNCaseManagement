/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.config;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.sql.DBCInfo;

/**
 *
 * @author User
 */
public class ConfigFile {
    
    public static void readConfigFile(){
        //TODO get Config File
        
        //If no config file show dialog to copy it to location or exit application
        //missingConfigDialog();
        
        
        //Read Config File
        
        //Company Info
        Global.setVersion("");
        Global.setCompanyID("");
        
        //DB Connection
        DBCInfo.setDBdriver("");
        DBCInfo.setDBurl("");
        DBCInfo.setDBname("");
        DBCInfo.setDBusername("");
        DBCInfo.setDBpassword("");
    }
    
    private static void missingConfigDialog() {
        //Show Popup with option to select file Location and attempt to read again
        
        
    }
    
}
