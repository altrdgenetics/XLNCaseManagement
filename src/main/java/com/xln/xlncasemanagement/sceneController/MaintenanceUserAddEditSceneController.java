/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.util.NumberFormatService;
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
public class MaintenanceUserAddEditSceneController implements Initializable {

    Stage stage;
    UserModel userObject;
    
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private TextField userNameTextfield;
    @FXML private TextField firstNameTextfield;
    @FXML private TextField middleInitialTextfield;
    @FXML private TextField lastNameNameTextfield;
    @FXML private TextField phoneNumberTextfield;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
    }    
    
    public void setActive(Stage stagePassed, UserModel userObjectPassed){
        stage = stagePassed;
        userObject = userObjectPassed;
        String title = "Add User";
        String buttonText = "Add";
        
        if (userObject != null){
            title = "Edit User";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(userNameTextfield.textProperty().isEmpty());
    }
    
    private void loadInformation(){
        userNameTextfield.setText(userObject.getUsername() == null ? "" : userObject.getUsername());
        firstNameTextfield.setText(userObject.getFirstName() == null ? "" : userObject.getFirstName());
        middleInitialTextfield.setText(userObject.getMiddleInitial() == null ? "" : userObject.getMiddleInitial());
        lastNameNameTextfield.setText(userObject.getLastName() == null ? "" : userObject.getLastName());
        phoneNumberTextfield.setText(userObject.getPhoneNumber() == null ? "" : NumberFormatService.convertStringToPhoneNumber(userObject.getPhoneNumber()));
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            updateUser();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insertUser();
        }
        stage.close();
    }
    
    private void insertUser() {
        //TODO
    }
    
    private void updateUser() {
        //TODO
    }
}
