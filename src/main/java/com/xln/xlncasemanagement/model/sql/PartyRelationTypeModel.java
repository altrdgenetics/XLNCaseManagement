/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.sql;

/**
 *
 * @author User
 */
public class PartyRelationTypeModel {
    
    private int id;
    private boolean active;
    private String partyRelationType;

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

    public String getPartyRelationType() {
        return partyRelationType;
    }

    public void setPartyRelationType(String partyRelationType) {
        this.partyRelationType = partyRelationType;
    }
    
    
    
}
