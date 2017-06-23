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
        
}
