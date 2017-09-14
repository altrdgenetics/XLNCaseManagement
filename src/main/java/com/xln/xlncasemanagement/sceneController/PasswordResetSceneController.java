/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.config.Password;
import static com.xln.xlncasemanagement.config.Password.hashPassword;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLUser;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.FileUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PasswordResetSceneController implements Initializable {

    Stage stage;
    boolean preferences;
    
    @FXML PasswordField CurrentPasswordField;
    @FXML PasswordField NewPasswordField;
    @FXML PasswordField ReEnterNewPasswordField;
    @FXML Button ExitButton;
    @FXML Button UpdateButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setBindings();
    }    
    
    private void setBindings() {
        UpdateButton.disableProperty().bind(
                (CurrentPasswordField.lengthProperty().lessThanOrEqualTo(3))
                .or(NewPasswordField.lengthProperty().lessThanOrEqualTo(3))
                .or(ReEnterNewPasswordField.lengthProperty().lessThanOrEqualTo(3))
        );
    }
    
    public void setActive(Stage stagePassed, boolean preferencesPassed){
        stage = stagePassed;
        preferences = preferencesPassed;
        
        if (preferences){
            ExitButton.setText("Cancel");
        }
    }
    
    @FXML private void onUpdateButtonAction() {
        if (!CurrentPasswordField.getText().equals(NewPasswordField.getText())) {
            if (verifyExistingPassword()) {
                if (verifyPasswordMatch()) {
                    if (verifyPasswordRequirement()) {
                        updatePassword();
                        passwordUpdated();
                        SQLAudit.insertAudit("Changed Password For User ID: " + Global.getCurrentUser().getId());
                        
                        stage.close();
                    } else {
                        failedRequirementsMessage();
                    }
                } else {
                    passwordMisMatchMessage();
                }
            } else {
                badOriginalPasswordMessage();
            }
        } else {
            newPasswordExistsMessage();
        }
    }
    
    @FXML private void onExitButtonAction() {
        if (preferences) {
            stage.close();
        } else {
            FileUtilities.cleanTempLocation();
            SQLUser.removeUserActiveLoginStatus(Global.getCurrentUser().getId());
            Platform.exit();
            System.exit(0);
        }
    }
    
    private boolean verifyExistingPassword() {
        return hashPassword(
                Global.getCurrentUser().getPasswordSalt(),
                CurrentPasswordField.getText().trim())
                .equals(Global.getCurrentUser().getPassword());
    }
    
    private boolean verifyPasswordMatch(){
        return NewPasswordField.getText().trim().equals(ReEnterNewPasswordField.getText().trim());
    }
    
    /**
     * Check to see if there is enough variation in password to meet
     * requirements of complexity, looks for uppercase, lowercase, number, and
     * symbol
     *
     * @return
     */
    private boolean verifyPasswordRequirement() {
        String password = NewPasswordField.getText().trim();
        char ch;
        boolean numberFlag = false;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean symbolFlag = false;
        int total = 0;

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                ch = password.charAt(i);
                if (Character.isDigit(ch)) {
                    numberFlag = true;
                } else if (Character.isUpperCase(ch)) {
                    capitalFlag = true;
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true;
                } else if (!Character.isLetterOrDigit(ch)) {
                    symbolFlag = true;
                }
            }

            if (numberFlag) {
                total++;
            }
            if (capitalFlag) {
                total++;
            }
            if (lowerCaseFlag) {
                total++;
            }
            if (symbolFlag) {
                total++;
            }
            return total >= 3;
        }
        return false;
    }

    private void updatePassword() {
        long passwordSalt = Password.generatePasswordSalt();
        String newPassword = Password.hashPassword(passwordSalt, NewPasswordField.getText().trim());

        SQLUser.updateUserPasswordByID(
                Global.getCurrentUser().getId(),
                newPassword,
                passwordSalt,
                false
        );
    }

    private void badOriginalPasswordMessage() {
        AlertDialog.StaticAlert(3, "Mismatch Error",
                "Invalid Password",
                "The original credentials are incorrect.");
        clearFields();
    }
    
    private void newPasswordExistsMessage() {
        AlertDialog.StaticAlert(3, "Mismatch Error",
                "Invalid Password",
                "The new credientials can not be the same as the current credentials.");
        clearFields();
    }
    
    private void passwordMisMatchMessage() {
        AlertDialog.StaticAlert(3, "MisMatch Error",
                "New Password MisMatch",
                "Please re-enter the passwords.");
        clearFields();
    }

    private void failedRequirementsMessage() {
        AlertDialog.StaticAlert(3, "Password Error",
                "The new password does not meet the complexity requirements",
                "Your password must:\n\n"
                + "    Be at least 8 characters in length.\n\n"
                + "    Contain at least 3 of the following character types:\n"
                + "    Lowercase letter\n"
                + "    Uppercase letter\n"
                + "    special character (!@#$%^&*)\n"
                + "    number (0–9)");
        clearFields();
    }
    
    private void passwordUpdated(){
        AlertDialog.StaticAlert(2, "Password Changed",
                "Password Updated",
                "The password has been updated successfully.");
    }
    
    private void clearFields() {
        CurrentPasswordField.clear();
        NewPasswordField.clear();
        ReEnterNewPasswordField.clear();
    }
    
}
