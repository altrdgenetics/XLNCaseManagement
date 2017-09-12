/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.sql.SQLUser;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class UserPreferenceSceneController implements Initializable {

    Stage stage;
    
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private Button ResetPasswordButton;
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
    
    @FXML private void onCloseButtonAction() {
        stage.close();
    }
    
    @FXML private void onSaveButtonAction() {
        saveInformation();
    }
    
    @FXML private void onResetPasswordButtonAction() {
        Global.getStageLauncher().PasswordResetScene(stage, true);
    }
    
    public void setActive(Stage stagePassed){
        stage = stagePassed;
        loadInformation();
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
        userNameTextfield.setText(Global.getCurrentUser().getUsername() == null ? "" : Global.getCurrentUser().getUsername());
        firstNameTextfield.setText(Global.getCurrentUser().getFirstName() == null ? "" : Global.getCurrentUser().getFirstName());
        middleInitialTextfield.setText(Global.getCurrentUser().getMiddleInitial() == null ? "" : Global.getCurrentUser().getMiddleInitial());
        lastNameNameTextfield.setText(Global.getCurrentUser().getLastName() == null ? "" : Global.getCurrentUser().getLastName());
        phoneNumberTextfield.setText(Global.getCurrentUser().getPhoneNumber() == null ? "" : NumberFormatService.convertStringToPhoneNumber(Global.getCurrentUser().getPhoneNumber()));
        defaultRateTextField.setText(NumberFormatService.formatMoney(Global.getCurrentUser().getDefaultRate()));
    }
    
    private void saveInformation() {
        Global.getCurrentUser().setFirstName(firstNameTextfield.getText().trim().equals("") ? null : firstNameTextfield.getText().trim());
        Global.getCurrentUser().setMiddleInitial(middleInitialTextfield.getText().trim().equals("") ? null : middleInitialTextfield.getText().trim());
        Global.getCurrentUser().setLastName(lastNameNameTextfield.getText().trim().equals("") ? null : lastNameNameTextfield.getText().trim());
        Global.getCurrentUser().setPhoneNumber(phoneNumberTextfield.getText().trim().equals("") ? null : phoneNumberTextfield.getText().trim());
        Global.getCurrentUser().setEmailAddress(null);
        Global.getCurrentUser().setUsername(userNameTextfield.getText().trim().equals("") ? null : userNameTextfield.getText().trim());
        Global.getCurrentUser().setActiveLogin(true);
        Global.getCurrentUser().setDefaultRate(defaultRateTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(defaultRateTextField.getText().trim()));
        
        SQLUser.updateUserByID(Global.getCurrentUser());
    }
    
}
