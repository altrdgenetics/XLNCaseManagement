/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.report;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.ReportModel;
import com.xln.xlncasemanagement.sql.DBConnection;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.sql.SQLActivityFile;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.FileUtilities;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author User
 */
public class GenerateReport {

    public static void generateReport(ReportModel report, HashMap hash) {
        long lStartTime = System.currentTimeMillis();
        Connection conn = null;

        ByteArrayInputStream bis = new ByteArrayInputStream(report.getFileBlob());
        
        if (report.getFileBlob() != null) {
            try {
                String fileName = report.getFileName().substring(0, report.getFileName().lastIndexOf("."));
                if (fileName.length() > 50) {
                    fileName = StringUtils.left(fileName.trim(), 50).trim() + "_" + System.currentTimeMillis();
                } else {
                    fileName = fileName + "_" + System.currentTimeMillis();
                }

                String pdfFileName = Global.getTempDirectory() + fileName + ".pdf";

                conn = DBConnection.connectToDB();
                JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(bis, hash, conn);
                try {
                    JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
                } catch (JRException e) {
                    fileAlreadyOpenError();
                }
                File recentReport = new File(pdfFileName);
                if (recentReport.exists()) {
                    try {
                        Desktop.getDesktop().open(recentReport);
                    } catch (IOException ex) {
                        errorGeneratingReport();
                    }
                    long lEndTime = System.currentTimeMillis();
                    System.out.println("Report Generation Time: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }
            } catch (JRException ex) {
                errorGeneratingReport();
            } finally {
                try {
                    bis.close();
                } catch (IOException ex) {
                    DebugTools.HandleException(ex);
                }
                DbUtils.closeQuietly(conn);
            }
        } else {
            fileNotFound();
        }
    }
    
    public static void generateBill(PartyModel client, MatterModel matter, boolean bill, HashMap hash) {
        long lStartTime = System.currentTimeMillis();
        Connection conn = null;
        InputStream is = null;
        String fileName = StringUtils.left(((bill ? "Bill_" : "PreBill_") 
                + StringUtilities.buildName(client.getFirstName(), "", client.getLastName()) 
                + "_" + matter.getMatterTypeName().trim()).trim(), 30).trim().replaceAll(" ", "_") + "_" + System.currentTimeMillis();
        
        is = GenerateReport.class.getResourceAsStream("/jasper/" + (bill ? "Bill.jasper" : "PreBill.jasper"));
        
        if (is != null) {
            try {
                byte[] pdfFileStream = null;
                
                String pdfFileName = Global.getTempDirectory() + fileName + ".pdf";

                conn = DBConnection.connectToDB();
                JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(is, hash, conn);
                try {
                    pdfFileStream = JasperExportManager.exportReportToPdf(jprint);
                } catch (JRException e) {
                    fileAlreadyOpenError();
                }
                if (bill && pdfFileStream != null){
                    addBillingActivity(matter.getId(), pdfFileStream, fileName + ".pdf");
                }
                
                File recentReport = FileUtilities.generateFileFromByte(pdfFileStream, pdfFileName);
                if (recentReport.exists()) {
                    try {
                        Desktop.getDesktop().open(recentReport);
                    } catch (IOException ex) {
                        errorGeneratingReport();
                        DebugTools.HandleException(ex);
                    }
                    long lEndTime = System.currentTimeMillis();
                    System.out.println("Report Generation Time: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }
            } catch (JRException ex) {
                errorGeneratingReport();
                DebugTools.HandleException(ex);
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    DebugTools.HandleException(ex);
                }
                DbUtils.closeQuietly(conn);
            }
        } else {
            fileNotFound();
        }
    }
    
    private static void addBillingActivity(int matterID, byte[] pdfFileStream, String fileName){
        ActivityModel item = new ActivityModel();
        item.setActive(true);
        item.setUserID(Global.getCurrentUser().getId());
        item.setActivityTypeID(0);
        item.setMatterID(matterID);
        item.setDateOccurred(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        item.setDuration(BigDecimal.ZERO);
        item.setRate(BigDecimal.ZERO);
        item.setTotal(BigDecimal.ZERO);
        item.setDescription("Bill Generated");        
        item.setBillable(false);
        item.setInvoiced(true);
        
        int activityID = SQLActivity.insertActivity(item);
        
        SQLActivityFile.insertActivityFile(activityID, pdfFileStream, fileName);
    }
    
    private static void fileAlreadyOpenError() {
        AlertDialog.StaticAlert(4, "Generate Error",
                            "Unable To Generate Report",
                            "An old version of this report is currently open elsewhere. "
                            + "Please ensure it is closed before continuing.");
    }
    
    private static void errorGeneratingReport() {
        AlertDialog.StaticAlert(4, "Generate Error",
                        "Unable To Generate Report",
                        "There was an issue generating this report.");
    }
    
    private static void fileNotFound() {
        AlertDialog.StaticAlert(4, "Load Error",
                    "Unable To Find Report",
                    "There was an issue retreiving this report. "
                    + "Please try again later.");
    }
        
}
