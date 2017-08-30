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
            case "Legal":
                Legal();
                break;
            case "Computer Repair":
                ComputerRepair();
                break;
            case "3":
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
        //Matter Type
        Global.setNewCaseType("Matter");
        Global.setLeadWording("Potential Client");
        
        //Type for the New Button
        Global.setButtonLabel1("New " + Global.getNewCaseType());
        Global.setButtonLabel2("Incoming");
        Global.setButtonLabel3("Outgoing");
        
        //Header Labels
        Global.setHeaderLabel1(Global.getNewCaseType() + ":");
        Global.setHeaderLabel2("Jurisdiction:");
        Global.setHeaderLabel3("Judge:");
        Global.setHeaderLabel4("Case Number:");
        Global.setHeaderLabel5("");
        
        //Information Labels
        Global.setInformationLabel1("Statue of Limitations:");
        Global.setInformationLabel2("Trust Fund:");
        Global.setInformationLabel3(Global.getHeaderLabel2());
        Global.setInformationLabel4(Global.getHeaderLabel3());
        Global.setInformationLabel5(Global.getHeaderLabel4());
    }
    
    /**
     * Labels for Computer Repair
     */
    private static void ComputerRepair(){
        //Matter Type
        Global.setNewCaseType("Service");
        Global.setLeadWording("Potential Client");
        
        //Type for the New Button
        Global.setButtonLabel1("New " + Global.getNewCaseType());
        Global.setButtonLabel2("");
        Global.setButtonLabel3("");
        
        //Header Labels
        Global.setHeaderLabel1(Global.getNewCaseType() + ":");
        Global.setHeaderLabel2("Make:");
        Global.setHeaderLabel3("Model:");
        Global.setHeaderLabel4("Serial:");
        Global.setHeaderLabel5("");
        
        //Information Labels
        Global.setInformationLabel1("Warranty:");
        Global.setInformationLabel2("Estimate:");
        Global.setInformationLabel3(Global.getHeaderLabel2());
        Global.setInformationLabel4(Global.getHeaderLabel3());
        Global.setInformationLabel5(Global.getHeaderLabel4());
    }
        
    /**
     * Labels for Marketing Firm
     */
    private static void MarketingFirm(){
        //Matter Type
        Global.setNewCaseType("Project");
        Global.setLeadWording("Potential Client");
        
        //Type for the New Button
        Global.setButtonLabel1("New " + Global.getNewCaseType());
        Global.setButtonLabel2("");
        Global.setButtonLabel3("");
        
        //Header Labels
        Global.setHeaderLabel1(Global.getNewCaseType() + ":");
        Global.setHeaderLabel2("");
        Global.setHeaderLabel3("");
        Global.setHeaderLabel4("");
        Global.setHeaderLabel5("");
        
        //Information Labels
        Global.setInformationLabel1("Due Date:");
        Global.setInformationLabel2("Budget:");
        Global.setInformationLabel3(Global.getHeaderLabel2());
        Global.setInformationLabel4(Global.getHeaderLabel3());
        Global.setInformationLabel5(Global.getHeaderLabel4());
    }
    
}
