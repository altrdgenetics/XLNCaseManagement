/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.model.sql.ExpenseModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.sceneController.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author User
 */
public class StageLauncher {

    public void loginScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginStage.fxml"));
            Global.setRoot((Parent) loader.load());

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(Global.getApplicationLogo());
            Scene scene = new Scene(Global.getRoot());
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            Global.getRoot().setStyle(
                      "-fx-border-insets: 10;"
                    + "-fx-background-insets: 10;"
                    + "-fx-border-color: gray;"
                    + "-fx-border-style: solid;"
                    + "-fx-border-width: 0.25;"
                    + "-fx-effect: dropshadow(gaussian, rgba(100, 100, 100, 0.5), 20, 0.25, 0, 0);"
            );

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
 
    public void docketingIncomingScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DocketingIncomingScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DocketingIncomingSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void docketingOutgoingScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DocketingOutgoingScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DocketingOutgoingSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detailedCasePartyAddEditScene(Stage stagePassed, PartyModel itemPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedCasePartyScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedCasePartySceneController controller = loader.getController();
            controller.setActive(itemPassed);

            stage.setResizable(false);
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detailedExpenseAddEditScene(Stage stagePassed, ExpenseModel itemPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedExpenseScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedExpenseSceneController controller = loader.getController();
            controller.setActive(itemPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detailedActivityAddEditScene(Stage stagePassed, ActivityModel itemPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedActivityScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedActivitySceneController controller = loader.getController();
            controller.setActive(itemPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void partySearchScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PartySearchScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            PartySearchSceneController controller = loader.getController();
            controller.setActive();

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceCompanyScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceCompanyScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceCompanySceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
