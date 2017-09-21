/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.report;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author User
 */
public class ReportHashMap {
    
    public static HashMap generateDefaultInformation(HashMap hash) {
        hash.put("current user", StringUtilities.buildUsersName(Global.getCurrentUser()));
        hash.put("mattertype", Global.getNewCaseType());
        hash.put("leadwording", Global.getLeadWording());
        hash.put("limitwording", Global.getInformationLabel1().replaceAll(":", "").trim());
        return hash;
    }
    
    public static HashMap matterID(HashMap hash, int matterID) {
        hash.put("matterid", matterID);
        return hash;
    }
    
    public static HashMap clientID(HashMap hash, int clientID) {
        hash.put("clientid", clientID);
        return hash;
    }
    
    public static HashMap startDateEndDate(HashMap hash, Date startDate, Date endDate) {
        hash.put("startDate", startDate);
        hash.put("endDate", endDate);
        return hash;
    }
    
    public static HashMap billingSubReports(HashMap hash){
        JasperReport activityReport = null;
        JasperReport expensesReport = null;
        
        //Get Files
        InputStream activityStream = ReportHashMap.class.getResourceAsStream("/jasper/Activities_Subreport.jrxml");
        InputStream expenseStream = ReportHashMap.class.getResourceAsStream("/jasper/Expenses_Subreport.jrxml");
        
        //Convert Stream to JasperReport
        try {
            activityReport = (JasperReport) JasperCompileManager.compileReport(activityStream);
            expensesReport = (JasperReport) JasperCompileManager.compileReport(expenseStream);
            activityStream.close();
            expenseStream.close();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportHashMap.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        //Place SubReport into HashMap
        if (activityReport != null) {
            hash.put("activitySubReport", activityReport);
        }
        if (expensesReport != null) {
            hash.put("expensesSubReport", expensesReport);
        }
        return hash;
    }
    
}
