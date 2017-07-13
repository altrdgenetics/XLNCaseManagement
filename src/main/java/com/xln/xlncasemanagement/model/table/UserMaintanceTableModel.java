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
public class UserMaintanceTableModel {
    public ObjectProperty object = new SimpleObjectProperty(null);
    public StringProperty name = new SimpleStringProperty(null);
    public StringProperty lastSignIn = new SimpleStringProperty(null);
    

    public UserMaintanceTableModel(Object object, String name, String lastSignIn) {
        this.object = new SimpleObjectProperty(object);
        this.name = new SimpleStringProperty(name);
        this.lastSignIn = new SimpleStringProperty(lastSignIn);
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

    public StringProperty getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(StringProperty lastSignIn) {
        this.lastSignIn = lastSignIn;
    }
    
    
}
