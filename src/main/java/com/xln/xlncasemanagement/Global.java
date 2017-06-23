/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Global {
    
    private static Parent root;    
    private static Stage mainStage;
    private static StageLauncher stageLauncher;
    
    //list of all states -> can limit this down if client wants
    private static final String STATES[] = { "AL", "AK", "AS", "AZ", "AR", "CA", "CO",
        "CT", "DE", "DC", "FL", "FM", "GA", "GU", "HI", "ID", "IL", "IN", "IA",
        "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
        "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR",
        "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VT", "VA",
        "VI", "WA", "WV", "WI", "WY"};
    
    public static Parent getRoot() {
        return root;
    }

    public static void setRoot(Parent root) {
        Global.root = root;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        Global.mainStage = mainStage;
    }

    public static StageLauncher getStageLauncher() {
        return stageLauncher;
    }

    public static void setStageLauncher(StageLauncher stageLauncher) {
        Global.stageLauncher = stageLauncher;
    }

    public static String[] getSTATES() {
        return STATES;
    }
    
}
