/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterTypeModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLMatterType;
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
public class MaintenanceMatterTypeAddEditSceneController implements Initializable {

    Stage stage;
    MatterTypeModel matterTypeObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private TextField matterTypetextField;
    @FXML
    private Label typeLabel;
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
    
    public void setActive(Stage stagePassed, MatterTypeModel matterTypeObjectPassed){
        stage = stagePassed;
        matterTypeObject = matterTypeObjectPassed;
        String title = "Add " + Global.getNewCaseType() + " Type";
        String buttonText = "Add";
        
        typeLabel.setText(Global.getNewCaseType() + ":");
        
        if (matterTypeObject != null){
            title = "Edit " + Global.getNewCaseType() + " Type";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                matterTypetextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        matterTypetextField.setText(matterTypeObject.getMatterType());
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
        matterTypeObject = new MatterTypeModel();
        matterTypeObject.setActive(true);
        matterTypeObject.setMatterType(matterTypetextField.getText().trim());
        int id = SQLMatterType.insertMatterType(matterTypeObject);
        System.out.println("New " + Global.getNewCaseType() + " Type ID: " + id);
        SQLAudit.insertAudit("Added " + Global.getNewCaseType().replace(":", "") + " ID: " + id);
    }
    
    private void updateCompany() {
        matterTypeObject.setMatterType(matterTypetextField.getText().trim());
        SQLMatterType.updateMatterTypeByID(matterTypeObject);
        SQLAudit.insertAudit("Updated " + Global.getNewCaseType().replace(":", "") + " ID: " + matterTypeObject.getId());
    }
}
