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
    @FXML private Button MakeButton;
    @FXML private Button ModelButton;
    @FXML private Button MatterTypeButton;
    @FXML private Button PartyButton;
    @FXML private Button PartyNamePreFixButton;
    @FXML private Button PartyRelationTypeButton;
    @FXML private Button ReportButton;
    @FXML private Button TemplateButton;
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
        
        //Set Button Labels
        MakeButton.setText(Global.getHeaderLabel2().replace(":", ""));
        ModelButton.setText(Global.getHeaderLabel3().replace(":", ""));
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
    
    @FXML private void makeButtonAction() {
        Global.getStageLauncher().MaintenanceMakeScene(stage, true);
    }
    
    @FXML private void modelButtonAction() {
        Global.getStageLauncher().MaintenanceModelScene(stage, true, 0);
    }
    
    @FXML private void matterTypeButtonAction() {
        Global.getStageLauncher().MaintenanceMatterTypeScene(stage, true);
    }
    
    @FXML private void partyButtonAction() {
        Global.getStageLauncher().partySearchScene(stage, true, false);
    }
    
    @FXML private void partyNamePreFixButtonAction() {
        Global.getStageLauncher().MaintenancePartyNamePrefixScene(stage);
    }
    
    @FXML private void partyRelationTypeButtonAction() {
         Global.getStageLauncher().MaintenancePartyRelationTypeScene(stage);
    }
    
    @FXML private void reportButtonAction() {
        Global.getStageLauncher().MaintenanceReportScene(stage);
    }
    
    @FXML private void templateButtonAction() {
        Global.getStageLauncher().MaintenanceTemplateScene(stage);
    }
    
    @FXML private void userButtonAction() {
        Global.getStageLauncher().MaintenanceUserScene(stage);
    }
    
}
