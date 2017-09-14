/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.config.Password;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLUser;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    @FXML private Button ResetPasswordButton;
    @FXML private CheckBox loggedInCheckBox;
    @FXML private CheckBox adminRightsCheckBox;
    @FXML private TextField userNameTextfield;
    @FXML private TextField firstNameTextfield;
    @FXML private TextField middleInitialTextfield;
    @FXML private TextField lastNameNameTextfield;
    @FXML private TextField phoneNumberTextfield;
    @FXML private TextField defaultRateTextField;
        
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
        } else {
            ResetPasswordButton.setVisible(false);
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(userNameTextfield.textProperty().isEmpty());
        defaultRateTextField.setTextFormatter(new TextFormatter<>(NumberFormatService.moneyMaskFormatter()));
        
        middleInitialTextfield.textProperty().addListener(
                (final ObservableValue<? extends String> v, final String ov, final String nv) -> {
            if (middleInitialTextfield.getText().length() > 1) {
                String s = middleInitialTextfield.getText().substring(0, 1);
                middleInitialTextfield.setText(s);
            }
        });
    }
    
    private void loadInformation(){
        userNameTextfield.setText(userObject.getUsername() == null ? "" : userObject.getUsername());
        firstNameTextfield.setText(userObject.getFirstName() == null ? "" : userObject.getFirstName());
        middleInitialTextfield.setText(userObject.getMiddleInitial() == null ? "" : userObject.getMiddleInitial());
        lastNameNameTextfield.setText(userObject.getLastName() == null ? "" : userObject.getLastName());
        phoneNumberTextfield.setText(userObject.getPhoneNumber() == null ? "" : NumberFormatService.convertStringToPhoneNumber(userObject.getPhoneNumber()));
        loggedInCheckBox.setSelected(userObject.isActiveLogin());
        adminRightsCheckBox.setSelected(userObject.isAdminRights());
        defaultRateTextField.setText(NumberFormatService.formatMoney(userObject.getDefaultRate()));
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
    
    @FXML private void onResetPasswordButtonAction() {        
        //Verify Reset
        boolean approved = AlertDialog.StaticAlert(
                1, 
                "Reset Password", 
                "Do You Wish To Reset This User's Password?", 
                "This is not reversable"
        );
        
        if (approved) {
            //Reset
            long passwordSalt = Password.generatePasswordSalt();
            String tempPassword = Password.generateTempPassword();

            SQLUser.updateUserPasswordByID(
                    userObject.getId(), 
                    Password.hashPassword(passwordSalt, tempPassword), 
                    passwordSalt,
                    true
            );

            AlertDialog.StaticAlert(
                    2,
                    "New Password",
                    "New Password: " + tempPassword,
                    "This is a temporary password that has been randomly generated "
                    + "and will require the user to reset on login."
            );
        }
    }

    private void insertUser() {
        String tempPassword = Password.generateTempPassword();
        long passwordSalt = Password.generatePasswordSalt();
        
        UserModel item = new UserModel();
        item.setFirstName(firstNameTextfield.getText().trim().equals("") ? null : firstNameTextfield.getText().trim());
        item.setMiddleInitial(middleInitialTextfield.getText().trim().equals("") ? null : middleInitialTextfield.getText().trim());
        item.setLastName(lastNameNameTextfield.getText().trim().equals("") ? null : lastNameNameTextfield.getText().trim());
        item.setPhoneNumber(phoneNumberTextfield.getText().trim().equals("") ? null : phoneNumberTextfield.getText().trim());
        item.setEmailAddress(null);
        item.setUsername(userNameTextfield.getText().trim().equals("") ? null : userNameTextfield.getText().trim());
        item.setActiveLogin(loggedInCheckBox.isSelected());
        item.setAdminRights(adminRightsCheckBox.isSelected());
        item.setDefaultRate(defaultRateTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(defaultRateTextField.getText().trim()));        
        item.setPassword(Password.hashPassword(passwordSalt, tempPassword));
        item.setPasswordSalt(passwordSalt);
        
        ///Insert USER
        int userID = SQLUser.insertUser(item);
        
        SQLAudit.insertAudit("Added User ID: " + userID);
        
        AlertDialog.StaticAlert(
                    2,
                    "New Password",
                    "New Password: " + tempPassword,
                    "This is a temporary password that has been randomly generated "
                    + "and will require the user to reset on login."
            );
    }
    
    private void updateUser() {
        UserModel item = new UserModel();
        item.setFirstName(firstNameTextfield.getText().trim().equals("") ? null : firstNameTextfield.getText().trim());
        item.setMiddleInitial(middleInitialTextfield.getText().trim().equals("") ? null : middleInitialTextfield.getText().trim());
        item.setLastName(lastNameNameTextfield.getText().trim().equals("") ? null : lastNameNameTextfield.getText().trim());
        item.setPhoneNumber(phoneNumberTextfield.getText().trim().equals("") ? null : phoneNumberTextfield.getText().trim());
        item.setEmailAddress(null);
        item.setUsername(userNameTextfield.getText().trim().equals("") ? null : userNameTextfield.getText().trim());
        item.setActiveLogin(loggedInCheckBox.isSelected());
        item.setAdminRights(adminRightsCheckBox.isSelected());
        item.setDefaultRate(defaultRateTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(defaultRateTextField.getText().trim()));
        item.setId(userObject.getId());
        
        SQLUser.updateUserByID(item);
        
        SQLAudit.insertAudit("Updated User ID: " + item.getId());
    }
        
}
