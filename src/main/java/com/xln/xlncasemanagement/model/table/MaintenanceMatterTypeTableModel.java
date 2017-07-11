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
public class MaintenanceMatterTypeTableModel {
    
    public ObjectProperty object = new SimpleObjectProperty(null);
    public BooleanProperty active = new SimpleBooleanProperty(false);
    public StringProperty matterType = new SimpleStringProperty(null);

    public MaintenanceMatterTypeTableModel(Object object, boolean active, String matterType) {
        this.object = new SimpleObjectProperty(object);
        this.active = new SimpleBooleanProperty(active);
        this.matterType = new SimpleStringProperty(matterType);
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

    public StringProperty getMatterType() {
        return matterType;
    }

    public void setMatterType(StringProperty matterType) {
        this.matterType = matterType;
    }

}
