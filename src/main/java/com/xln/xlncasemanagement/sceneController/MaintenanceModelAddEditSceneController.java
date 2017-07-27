/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.ActivityTypeModel;
import com.xln.xlncasemanagement.model.sql.ModelModel;
import com.xln.xlncasemanagement.sql.SQLActivityType;
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
public class MaintenanceModelAddEditSceneController implements Initializable {

    Stage stage;
    ModelModel modelObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private TextField activityTypeTextField;
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
    
    public void setActive(Stage stagePassed, ModelModel modelObjectPassed){
        stage = stagePassed;
        modelObject = modelObjectPassed;
        String title = "Add Activity Type";
        String buttonText = "Add";
        
        if (modelObject != null){
            title = "Edit Activity Type";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                activityTypeTextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        activityTypeTextField.setText(modelObject.getName());
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
        modelObject = new ModelModel();
        modelObject.setActive(true);
        modelObject.setName(activityTypeTextField.getText().trim());
        //int id = SQLActivityType.insertActivityType(modelObject);
        //System.out.println("New Activity Type ID: " + id);
    }
    
    private void updateCompany() {
        modelObject.setName(activityTypeTextField.getText().trim());
        //SQLActivityType.updateActivityTypeByID(modelObject);
    }
}
