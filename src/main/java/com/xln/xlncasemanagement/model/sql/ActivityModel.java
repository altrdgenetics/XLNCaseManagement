/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.sql;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author User
 */
public class ActivityModel {
    
    private int id;
    private boolean active;
    private int userID;
    private int activityTypeID;
    private int matterID;
    private Date dateOccurred;
    private BigDecimal duration;
    private BigDecimal rate;
    private BigDecimal total;
    private String description;
    private boolean billable;
    private boolean invoiced;
    private String fileName;
    private byte[] fileBlob;
    private String fileBlobHash;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getActivityTypeID() {
        return activityTypeID;
    }

    public void setActivityTypeID(int activityTypeID) {
        this.activityTypeID = activityTypeID;
    }

    public int getMatterID() {
        return matterID;
    }

    public void setMatterID(int matterID) {
        this.matterID = matterID;
    }

    public Date getDateOccurred() {
        return dateOccurred;
    }

    public void setDateOccurred(Date dateOccurred) {
        this.dateOccurred = dateOccurred;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

    public byte[] getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(byte[] fileBlob) {
        this.fileBlob = fileBlob;
    }

    public String getFileBlobHash() {
        return fileBlobHash;
    }

    public void setFileBlobHash(String fileBlobHash) {
        this.fileBlobHash = fileBlobHash;
    }
    
}
