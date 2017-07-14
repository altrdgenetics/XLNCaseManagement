/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.ActivityModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DetailedActivitySceneController implements Initializable {

    Stage stage;
    ActivityModel activityObject;
    
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
    }    
    
    public void setActive(Stage stagePassed, ActivityModel activityObjectPassed){
        stage = stagePassed;
        activityObject = activityObjectPassed;
        String title = "Add Activity";
        String buttonText = "Add";
        
        if (activityObject != null){
            title = "Edit Activity";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
    }
    
    private void setListeners() {
        //TODO: Set DisableBinding
    }
    
    private void loadInformation(){
        //TODO
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            update();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insert();
        }
        stage.close();
    }
    
    private void insert() {
        //TODO
    }
    
    private void update() {
        //TODO
    }
}
