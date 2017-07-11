/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.PartyNamePrefixModel;
import com.xln.xlncasemanagement.sql.SQLPartyNamePrefix;
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
public class MaintenancePartyNamePrefixAddEditSceneController implements Initializable {

    Stage stage;
    PartyNamePrefixModel partyNamePrefixObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private TextField partyNamePrefixtextField;
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
    
    public void setActive(Stage stagePassed, PartyNamePrefixModel partyNamePrefixObjectPassed){
        stage = stagePassed;
        partyNamePrefixObject = partyNamePrefixObjectPassed;
        String title = "Add Prefix";
        String buttonText = "Add";
        
        if (partyNamePrefixObject != null){
            title = "Edit Prefix";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                partyNamePrefixtextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        partyNamePrefixtextField.setText(partyNamePrefixObject.getPrefix());
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            updatePrefix();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insertPrefix();
        }
        stage.close();
    }
    
    private void insertPrefix() {
        partyNamePrefixObject = new PartyNamePrefixModel();
        partyNamePrefixObject.setActive(true);
        partyNamePrefixObject.setPrefix(partyNamePrefixtextField.getText().trim());
        int id = SQLPartyNamePrefix.insertPartyNamePrefix(partyNamePrefixObject);
        System.out.println("New Part Name Prefix ID: " + id);
    }
    
    private void updatePrefix() {
        partyNamePrefixObject.setPrefix(partyNamePrefixtextField.getText().trim());
        SQLPartyNamePrefix.updatePartyNamePrefixByID(partyNamePrefixObject);
    }
}
