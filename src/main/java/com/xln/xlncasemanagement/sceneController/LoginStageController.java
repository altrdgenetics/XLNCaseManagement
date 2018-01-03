/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.config.Password;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLUser;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.DebugTools;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginStageController implements Initializable {

    Stage stage;
    private double X, Y;
    private int attempts = 0;
    private ArrayList<String> userNames = new ArrayList();
        
    @FXML private Label HeaderLabel;
    @FXML private ImageView logoImage;
    @FXML private TextField UsernameTextField;
    @FXML private PasswordField PasswordTextField;
    @FXML private Button LoginButton;
    @FXML private Button CloseButton;
    
    @FXML protected void onRectanglePressed(MouseEvent event) {
        X = stage.getX() - event.getScreenX();
        Y = stage.getY() - event.getScreenY();
    }

    @FXML protected void onRectangleDragged(MouseEvent event) {
        stage.setX(event.getScreenX() + X);
        stage.setY(event.getScreenY() + Y);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
    }    
    
    public void setActive(Stage stagePassed) {
        Global.setMainStage(stage);
        stage = stagePassed;
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        if (Global.getCompanyInformation() != null){
            HeaderLabel.setText(Global.getCompanyInformation().getName() + " Login");
            logoImage.setImage(Global.getApplicationLogo());
        }
        UsernameTextField.requestFocus();
    }
    
    @FXML private void loginButtonAction() {
        userNames.add(UsernameTextField.getText().trim());
        
        if (verifyUser()){
            if (concurrentUsersLimitReached()){
                maxAllowedConnectionsMessage();
            } else {
                successfulLogin();
            }
        } else if (attempts >= Global.getMAX_ALLOWED_LOGIN_ATTEMPTS()){
            if (userNames.size() > 0){
                SQLUser.lockUserAccounts(userNames.toArray(new String[userNames.size()]));
            }
            maxAllowedAttemptsMessage();
            closeButtonAction();
        } else {
            attempts++;
            UsernameTextField.clear();
            PasswordTextField.clear();
            loginFailedMessage();
            UsernameTextField.requestFocus();
        }
    }
    
    @FXML private void closeButtonAction() {
        Platform.exit();
        System.exit(0);
    }
    
    private boolean concurrentUsersLimitReached(){
        return SQLUser.getCountOfCurrentLoggedInUsers() >= Global.getMAX_ALLOWED_CONNECTIONS();
    }
    
    private void successfulLogin(){
        if (Global.getCurrentUser().isPasswordReset()){
            Global.getStageLauncher().PasswordResetScene(stage, false);
        }
        try {
            Global.getCurrentUser().setLastLoginIP(InetAddress.getLocalHost().getHostAddress());
            Global.getCurrentUser().setLastLoginPCName(InetAddress.getLocalHost().getHostName());
            
            SQLUser.updateUserLocationByID(
                    Global.getCurrentUser().getId(), 
                    Global.getCurrentUser().getLastLoginPCName(), 
                    Global.getCurrentUser().getLastLoginIP()
            );

            SQLAudit.insertAudit("Logged In User " + Global.getCurrentUser().getUsername()
                    + " From " + Global.getCurrentUser().getLastLoginPCName()
                    + " (" + Global.getCurrentUser().getLastLoginIP() + ")");

        } catch (UnknownHostException ex) {
            DebugTools.HandleException(ex);
        }
        UsernameTextField.clear();
        PasswordTextField.clear();
        Global.getStageLauncher().mainStage();
        Global.getLoginStage().hide();
    }
        
    private void setListeners() {
        LoginButton.disableProperty().bind(Bindings.or(
                UsernameTextField.textProperty().isEmpty(),
                PasswordTextField.textProperty().isEmpty()));
    }
    
    private boolean verifyUser() {
        int valid = Password.validatePassword(
                UsernameTextField.getText().trim(),
                PasswordTextField.getText().trim());
        
        switch (valid) {
            case 0:
                // Valid Login
                DebugTools.HandleInfoPrintout("Login Success");
                return true;
            case 1:
                // Failed Authentication
                DebugTools.HandleInfoPrintout("Login Failed: Failed Authentication");
                return false;
            case 2:
                // No User Found
                DebugTools.HandleInfoPrintout("Login Failed: No User Found");
                return false;
            case 3:
                // Account Locked
                DebugTools.HandleInfoPrintout("Login Failed: Account Locked");
                accountLockedMessage();
                return false;
            case 4:
                // Account Locked
                DebugTools.HandleInfoPrintout("Login Failed: Account Logged In");
                accountLoggedInAlreadyMessage();
                return false;
            default:
                // Returned unknown Variable
                DebugTools.HandleInfoPrintout("Login Failed: Unknown Reason");
                return false;
        }
    }

    private void loginFailedMessage() {
        String timesRemaining = String.valueOf(Global.getMAX_ALLOWED_LOGIN_ATTEMPTS() - attempts);
        AlertDialog.StaticAlert(3, "Login Error",
                "Invalid Login",
                "The login credentials are incorrect, " + timesRemaining
                + " attempts remaining before application exits.");
    }
    
    private void accountLoggedInAlreadyMessage() {
        AlertDialog.StaticAlert(3, "Login Error",
                "Invalid Login",
                "The login credentials are currently in use elsewhere. "
                + "Please exit from that location to continue.");
    }

    private void maxAllowedAttemptsMessage() {
        AlertDialog.StaticAlert(3, "Login Error",
                "Max Allowed Login Attempts Reached.",
                "The user account is now locked and will require admin "
                + "assistance in unlocking it.");
    }

    private void accountLockedMessage() {
        AlertDialog.StaticAlert(3, "Login Error",
                "This Account Is Locked.",
                "The user account is locked and will require admin "
                + "assistance in unlocking it.");
    }

    private void maxAllowedConnectionsMessage() {
        AlertDialog.StaticAlert(3, "Login Error",
                "Max Allowed Login Connections Reached.",
                "The maxmimum amount of concurrent user account is reached, "
                + "reduce the number of connected users or contact support "
                + "to increase the number of concurrent users allowed.");
    }

}
