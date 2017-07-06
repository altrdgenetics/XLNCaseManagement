/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import com.xln.xlncasemanagement.Global;

/**
 *
 * @author User
 */
public class LabelHashTables {
    
    public static void setGlobalLabels(String version) {
        switch (version) {
            case "1":
                Legal();
                break;
            case "2":
                ComputerRepair();
                break;
            default:
                break;
        }
    }
    
    /**
     * Labels For Legal Application
     */   
    private static void Legal(){
        //Type for the New Button
        Global.setButtonLabel1("New Matter");
        Global.setButtonLabel2("Incoming");
        Global.setButtonLabel3("Outgoing");
        
        //Header Labels
        Global.setHeaderLabel1("Matter:");
        Global.setHeaderLabel2("Jurisdiction:");
        Global.setHeaderLabel3("Judge:");
        Global.setHeaderLabel4("Case Number:");
        Global.setHeaderLabel5("");
        
        //Information Labels
        Global.setInformationLabel1("Statue of Limitations:");
        Global.setInformationLabel2("");
        Global.setInformationLabel3("");
        Global.setInformationLabel4("");
    }
    
    /**
     * Labels for Computer Repair
     */
    private static void ComputerRepair(){
        //Type for the New Button
        Global.setButtonLabel1("New Service");
        Global.setButtonLabel2("");
        Global.setButtonLabel3("");
        
        //Header Labels
        Global.setHeaderLabel1("Service:");
        Global.setHeaderLabel2("Make:");
        Global.setHeaderLabel3("Model:");
        Global.setHeaderLabel4("Serial:");
        Global.setHeaderLabel5("");
        
        //Information Labels
        Global.setInformationLabel1("Warranty:");
        Global.setInformationLabel2("");
        Global.setInformationLabel3("");
        Global.setInformationLabel4("");
    }
    
    
}
