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
public class MaintenanceMakeTableModel {
    
    public ObjectProperty object = new SimpleObjectProperty(null);
    public BooleanProperty active = new SimpleBooleanProperty(false);
    public StringProperty make = new SimpleStringProperty(null);
    public StringProperty webSite = new SimpleStringProperty(null);

    public MaintenanceMakeTableModel(Object object, boolean active, String make, String webSite) {
        this.object = new SimpleObjectProperty(object);
        this.active = new SimpleBooleanProperty(active);
        this.make = new SimpleStringProperty(make);
        this.webSite = new SimpleStringProperty(webSite);
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

    public void setActive(SimpleBooleanProperty active) {
        this.active = active;
    }

    public StringProperty getMake() {
        return make;
    }

    public void setMake(StringProperty make) {
        this.make = make;
    }

    public StringProperty getWebSite() {
        return webSite;
    }

    public void setWebSite(StringProperty webSite) {
        this.webSite = webSite;
    }

    
}
