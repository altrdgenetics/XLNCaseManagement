/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.sceneController.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class StageLauncher {
    
    public void mainStage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainStage.fxml"));
            Global.setRoot((Parent) loader.load());
            
            Scene scene = new Scene(Global.getRoot());
            stage.setScene(scene);
                     
            MainStageController controller = loader.getController();
            controller.loadDefaults(stage);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
