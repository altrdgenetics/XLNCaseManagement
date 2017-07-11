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
public class MaintenanceSceneController implements Initializable {

    Stage stage;
    @FXML private Button CloseButton;
    @FXML private Button ActivityTypeButton;
    @FXML private Button CompanyButton;
    @FXML private Button ExpenseTypeButton;
    @FXML private Button JudgeButton;
    @FXML private Button JurisdictionButton;
    @FXML private Button MatterTypeButton;
    @FXML private Button PartyButton;
    @FXML private Button PartyNamePreFixButton;
    @FXML private Button PartyRelationTypeButton;
    @FXML private Button UserButton;
    
    
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
        MatterTypeButton.setText(Global.getNewCaseType() + " Type");
    }
    
    @FXML private void closeButtonAction() {
        stage.close();
    }
    
    @FXML private void activityTypeButtonAction() {
        Global.getStageLauncher().MaintenanceActivityTypeScene(stage);
    }
    
    @FXML private void companyButtonAction() {
        Global.getStageLauncher().MaintenanceCompanyScene(stage);
    }
    
    @FXML private void expenseTypeButtonAction() {
        Global.getStageLauncher().MaintenanceExpenseTypeScene(stage);
    }
    
    @FXML private void judgeButtonAction() {
        
    }
    
    @FXML private void jurisdictionButtonAction() {
        
    }
    
    @FXML private void matterTypeButtonAction() {
        Global.getStageLauncher().MaintenanceMatterTypeScene(stage);
    }
    
    @FXML private void partyButtonAction() {
        
    }
    
    @FXML private void partyNamePreFixButtonAction() {
        
    }
    
    @FXML private void partyRelationTypeButtonAction() {
        
    }
    
    @FXML private void userButtonAction() {
        
    }
    
}
