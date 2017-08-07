/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class MaintenanceReportParametersTableModel {
    
    public StringProperty id = new SimpleStringProperty(null);
    public StringProperty name = new SimpleStringProperty(null);

    public MaintenanceReportParametersTableModel(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty getId() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }
    
}
