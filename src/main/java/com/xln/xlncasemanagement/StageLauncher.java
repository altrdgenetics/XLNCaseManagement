/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.sceneController.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author User
 */
public class StageLauncher {
    
    public void loginScene(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginStage.fxml"));
            Global.setRoot((Parent) loader.load());
                        
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(Global.getApplicationLogo());
            Scene scene = new Scene(Global.getRoot());
            stage.setScene(scene);

            LoginStageController controller = loader.getController();
            controller.setActive(stage);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mainStage() {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainStage.fxml"));
            Global.setRoot((Parent) loader.load());
            
            stage.initStyle(StageStyle.DECORATED);
            stage.getIcons().add(Global.getApplicationLogo());
            Scene scene = new Scene(Global.getRoot());
            stage.setScene(scene);
                     
            MainStageController controller = loader.getController();
            controller.setActive(stage);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void detailedCallAddEditStage(Stage stagePassed, PartyModel casePartyPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedCasePartyScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedCasePartySceneController controller = loader.getController();
            controller.setActive(casePartyPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
