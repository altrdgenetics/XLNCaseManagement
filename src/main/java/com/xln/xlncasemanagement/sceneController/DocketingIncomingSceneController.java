/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DocketingIncomingSceneController implements Initializable {

    Stage stage;
    
    @FXML Button CloseButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        
        Global.setIncomingDocketingStage(stage);
        stage.setOnCloseRequest((WindowEvent t) -> {
            stage.hide();
        });
    }
    
    @FXML private void closeButtonAction() {
        stage.hide();
    }
    
}
