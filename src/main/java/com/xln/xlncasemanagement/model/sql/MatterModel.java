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
public class MatterModel {
    
    private int id;
    private boolean active;
    private int partyID;
    private int matterTypeID;
    private String matterTypeName;
    private Date openDate;
    private Date closeDate;
    private String note;
    private Date warranty;
    private int make;
    private String makeName;
    private String makeWebsite;
    private int model;
    private String modelName;
    private String modelWebsite;
    private String serial;
    private BigDecimal budget;

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

    public Date getWarranty() {
        return warranty;
    }

    public void setWarranty(Date warranty) {
        this.warranty = warranty;
    }

    public int getMake() {
        return make;
    }

    public void setMake(int make) {
        this.make = make;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getMakeWebsite() {
        return makeWebsite;
    }

    public void setMakeWebsite(String makeWebsite) {
        this.makeWebsite = makeWebsite;
    }

    public String getModelWebsite() {
        return modelWebsite;
    }

    public void setModelWebsite(String modelWebsite) {
        this.modelWebsite = modelWebsite;
    }
        
    
    
}
