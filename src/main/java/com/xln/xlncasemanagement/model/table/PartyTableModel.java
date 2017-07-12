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
public class PartyTableModel {
    
    
    public ObjectProperty object = new SimpleObjectProperty(null);
    public BooleanProperty active = new SimpleBooleanProperty(false);
    public StringProperty name = new SimpleStringProperty(null);
    public StringProperty address = new SimpleStringProperty(null);
    public StringProperty phoneNumber = new SimpleStringProperty(null);

    public PartyTableModel(Object object, boolean active, String name, String address, String phoneNumber) {
        this.object = new SimpleObjectProperty(object);
        this.active = new SimpleBooleanProperty(active);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }
    
    //Checkmark Properties
    public BooleanProperty checkedProperty() {
        return this.active;
    }

    public java.lang.Boolean getChecked() {
        return this.checkedProperty().get();
    }

    public void setChecked(final java.lang.Boolean active) {
        this.checkedProperty().set(active);
    }
    
    
    //Getters and Setters
    
    
    public ObjectProperty getObject() {
        return object;
    }

    public void setObject(ObjectProperty object) {
        this.object = object;
    }

    public BooleanProperty getActive() {
        return active;
    }

    public void setActive(BooleanProperty active) {
        this.active = active;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty getAddress() {
        return address;
    }

    public void setAddress(StringProperty address) {
        this.address = address;
    }

    public StringProperty getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringProperty phoneNumber) {
        this.phoneNumber = phoneNumber;
    }    
    
}
