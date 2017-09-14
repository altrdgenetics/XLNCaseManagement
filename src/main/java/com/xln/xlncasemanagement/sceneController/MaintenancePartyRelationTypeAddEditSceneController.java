/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.PartyRelationTypeModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLPartyRelationType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenancePartyRelationTypeAddEditSceneController implements Initializable {

    Stage stage;
    PartyRelationTypeModel partyRelationTypeObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private TextField partyRelationTypetextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
    }    
    
    public void setActive(Stage stagePassed, PartyRelationTypeModel partyRelationTypeObjectPassed){
        stage = stagePassed;
        partyRelationTypeObject = partyRelationTypeObjectPassed;
        String title = "Add Party Relation Type";
        String buttonText = "Add";
        
        if (partyRelationTypeObject != null){
            title = "Edit Party Relation Type";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                partyRelationTypetextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        partyRelationTypetextField.setText(partyRelationTypeObject.getPartyRelationType());
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            updateCompany();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insertCompany();
        }
        stage.close();
    }
    
    private void insertCompany() {
        partyRelationTypeObject = new PartyRelationTypeModel();
        partyRelationTypeObject.setActive(true);
        partyRelationTypeObject.setPartyRelationType(partyRelationTypetextField.getText().trim());
        int id = SQLPartyRelationType.insertPartyRelationType(partyRelationTypeObject);
        System.out.println("New Party Relation Type ID: " + id);
        SQLAudit.insertAudit("Added Party Relation Type ID: " + id);
    }
    
    private void updateCompany() {
        partyRelationTypeObject.setPartyRelationType(partyRelationTypetextField.getText().trim());
        SQLPartyRelationType.updatePartyRelationTypeByID(partyRelationTypeObject);
        SQLAudit.insertAudit("Updated Party Relation Type ID: " + partyRelationTypeObject.getId());
    }
}
