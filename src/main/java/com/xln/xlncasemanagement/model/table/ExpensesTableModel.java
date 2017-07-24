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
    public StringProperty user = new SimpleStringProperty(null);
    public StringProperty description = new SimpleStringProperty(null);
    public StringProperty cost = new SimpleStringProperty(null);
    public StringProperty receipt = new SimpleStringProperty(null);
    public BooleanProperty invoiced = new SimpleBooleanProperty(false);

    public ExpensesTableModel(Object object, String date, String user, String description, String cost, String receipt, boolean invoiced) {
        this.object = new SimpleObjectProperty(object);
        this.date = new SimpleStringProperty(date);
        this.user = new SimpleStringProperty(user);
        this.description = new SimpleStringProperty(description);
        this.cost = new SimpleStringProperty(cost);
        this.receipt = new SimpleStringProperty(receipt);
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

    public StringProperty getCost() {
        return cost;
    }

    public void setCost(StringProperty cost) {
        this.cost = cost;
    }

    public StringProperty getReceipt() {
        return receipt;
    }

    public void setReceipt(StringProperty receipt) {
        this.receipt = receipt;
    }

    public BooleanProperty getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(BooleanProperty invoiced) {
        this.invoiced = invoiced;
    }
    
}
