/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class ActivityTableModel {
    
    public ObjectProperty object = new SimpleObjectProperty(null);
    public StringProperty date = new SimpleStringProperty(null);
    public StringProperty hours = new SimpleStringProperty(null);
    public StringProperty user = new SimpleStringProperty(null);
    public StringProperty description = new SimpleStringProperty(null);
    public StringProperty file = new SimpleStringProperty(null);
    public BooleanProperty billable = new SimpleBooleanProperty(false);
    public BooleanProperty invoiced = new SimpleBooleanProperty(false);

    public ActivityTableModel(Object object, String date, String hours, String user, String description, String file, boolean billable, boolean invoiced) {
        this.object = new SimpleObjectProperty(object);
        this.date = new SimpleStringProperty(date);
        this.hours = new SimpleStringProperty(hours);
        this.user = new SimpleStringProperty(user);
        this.description = new SimpleStringProperty(description);
        this.file = new SimpleStringProperty(file);
        this.billable = new SimpleBooleanProperty(billable);
        this.invoiced = new SimpleBooleanProperty(invoiced);
    }

    public ObjectProperty getObject() {
        return object;
    }

    public void setObject(ObjectProperty object) {
        this.object = object;
    }

    public StringProperty getDate() {
        return date;
    }

    public void setDate(StringProperty date) {
        this.date = date;
    }

    public StringProperty getHours() {
        return hours;
    }

    public void setHours(StringProperty hours) {
        this.hours = hours;
    }

    public StringProperty getUser() {
        return user;
    }

    public void setUser(StringProperty user) {
        this.user = user;
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(StringProperty description) {
        this.description = description;
    }

    public StringProperty getFile() {
        return file;
    }

    public void setFile(StringProperty file) {
        this.file = file;
    }

    public BooleanProperty getBillable() {
        return billable;
    }

    public void setBillable(BooleanProperty billable) {
        this.billable = billable;
    }
    
    public BooleanProperty getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(BooleanProperty invoiced) {
        this.invoiced = invoiced;
    }
    
}
