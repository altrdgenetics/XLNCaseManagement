/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import java.net.URL;
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
    private final int wrongUsername = 0;
    private final int wrongPassword = 1;
    private final int goodLogin = 2;
        
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
        //setListeners();
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
        Global.getStageLauncher().mainStage();
        stage.close();
    }
    
    @FXML private void closeButtonAction() {
        Platform.exit();
        System.exit(0);
    }
    
    private void setListeners() {
        LoginButton.disableProperty().bind(Bindings.or(
                UsernameTextField.textProperty().isEmpty(),
                PasswordTextField.textProperty().isEmpty()));
    }
}
