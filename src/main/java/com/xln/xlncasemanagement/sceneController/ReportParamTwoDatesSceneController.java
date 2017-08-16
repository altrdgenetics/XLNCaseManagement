/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReportParamTwoDatesSceneController implements Initializable {

    Stage stage;
    public HashMap hash;
    
    @FXML private DatePicker StartDateDatePicker;
    @FXML private DatePicker EndDateDatePicker;
    @FXML private Button CancelButton;
    @FXML private Button SelectButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SelectButton.disableProperty().bind(
                (StartDateDatePicker.valueProperty().isNull())
                        .or(EndDateDatePicker.valueProperty().isNull())
        );
    }    
    
    public void setActive(Stage stagePassed, HashMap hashPassed){
        stage = stagePassed;
        hash = hashPassed;
    }
    
    @FXML private void cancelButtonAction() {
        stage.close();
    }
    
    @FXML private void selectButtonAction() {
        
    }

    public HashMap getHash() {
        return hash;
    }

    public void setHash(HashMap hash) {
        this.hash = hash;
    }
    
}
