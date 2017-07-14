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
public class ExpensesTableModel {
    
    public ObjectProperty object = new SimpleObjectProperty(null);
    public StringProperty date = new SimpleStringProperty(null);
    public StringProperty description = new SimpleStringProperty(null);
    public StringProperty cost = new SimpleStringProperty(null);
    public ObjectProperty receipt = new SimpleObjectProperty(null);
    public BooleanProperty invoiced = new SimpleBooleanProperty(false);

    public ExpensesTableModel(Object object, String date, String description, String cost, Object receipt, boolean invoiced) {
        this.object = new SimpleObjectProperty(object);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.cost = new SimpleStringProperty(cost);
        this.receipt = new SimpleObjectProperty(receipt);
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

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(StringProperty description) {
        this.description = description;
    }

    public StringProperty getCost() {
        return cost;
    }

    public void setCost(StringProperty cost) {
        this.cost = cost;
    }

    public ObjectProperty getReceipt() {
        return receipt;
    }

    public void setReceipt(ObjectProperty receipt) {
        this.receipt = receipt;
    }

    public BooleanProperty getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(BooleanProperty invoiced) {
        this.invoiced = invoiced;
    }
    
    
    
}
