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

/**
 * FXML Controller class
 *
 * @author User
 */
public class PartySearchSceneController implements Initializable {

    Stage stage;
    
    @FXML Button CancelButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setActive() {
        
    }
    
    @FXML private void addNewPartyButtonAction() {
        Global.getStageLauncher().detailedCasePartyAddEditScene(stage, null);
    }
    
    @FXML private void closeButtonAction() {
        stage.close();
    }
    
}
