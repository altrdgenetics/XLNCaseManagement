/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.sql;

import java.sql.Date;

/**
 *
 * @author User
 */
public class MatterModel {
    
    private int id;
    private boolean active;
    private int partyID;
    private int matterTypeID;
    private String matterTypeName;
    private Date openDate;
    private Date closeDate;
    
    private String note;

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

    public int getPartyID() {
        return partyID;
    }

    public void setPartyID(int partyID) {
        this.partyID = partyID;
    }

    public int getMatterTypeID() {
        return matterTypeID;
    }

    public void setMatterTypeID(int matterTypeID) {
        this.matterTypeID = matterTypeID;
    }

    public String getMatterTypeName() {
        return matterTypeName;
    }

    public void setMatterTypeName(String matterTypeName) {
        this.matterTypeName = matterTypeName;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
