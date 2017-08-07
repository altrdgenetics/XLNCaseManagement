/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.sql;

/**
 *
 * @author User
 */
public class ReportParameterModel {
    
    private int id;
    private int reportID;
    private String reportParameter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getReportParameter() {
        return reportParameter;
    }

    public void setReportParameter(String reportParameter) {
        this.reportParameter = reportParameter;
    }
    
    
    
}
