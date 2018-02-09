/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class ClientTableModel {
    
    
    public ObjectProperty object = new SimpleObjectProperty(null);
    public StringProperty name = new SimpleStringProperty(null);
    public StringProperty address = new SimpleStringProperty(null);
    public StringProperty phoneNumber = new SimpleStringProperty(null);

    public ClientTableModel(Object object, String name, String address, String phoneNumber) {
        this.object = new SimpleObjectProperty(object);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }   
    
    public ObjectProperty getObject() {
        return object;
    }

    public void setObject(ObjectProperty object) {
        this.object = object;
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
