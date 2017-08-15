/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.report;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ReportModel;
import com.xln.xlncasemanagement.sql.DBConnection;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
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

    public static void generateDefaultInformation(ReportModel report, HashMap hash) {
        hash.put("current user", StringUtilities.buildUsersName(Global.getCurrentUser()));
        hash.put("mattertype", Global.getNewCaseType());
        hash.put("leadwording", Global.getLeadWording());
        generateReport(report, hash);
    }

    private static void generateReport(ReportModel report, HashMap hash) {
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
                DbUtils.closeQuietly(conn);
            }
        } else {
            fileNotFound();
        }
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
