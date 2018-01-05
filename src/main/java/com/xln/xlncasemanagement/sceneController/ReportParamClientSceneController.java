/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.report.ReportHashMap;
import com.xln.xlncasemanagement.sql.SQLParty;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReportParamClientSceneController implements Initializable {

    Stage stage;
    public HashMap hash;
    
    @FXML private Label HeaderLabel;
    @FXML private ComboBox ClientComboBox;
    @FXML private Button CloseButton;
    @FXML private Button RunButton;
    ProgressBar progressBar;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup Client ComboBox
        StringConverter<PartyModel> converter = new StringConverter<PartyModel>() {
            @Override
            public String toString(PartyModel object) {
                return StringUtilities.buildPartyName(object);
            }

            @Override
            public PartyModel fromString(String string) {
                return null;
            }
        };
        ClientComboBox.setConverter(converter);
        
    }    
    
    public void setActive(Stage stagePassed, HashMap hashPassed){
        stage = stagePassed;
        hash = hashPassed;
        loadInformation();
    }
    
    private void loadInformation(){
        loadClientComboBox();
        if (Global.getCurrentClient() != null){
            ClientComboBox.getSelectionModel().select(Global.getCurrentClient());
        }
    }
    
    public void loadClientComboBox(){
        ClientComboBox.getItems().removeAll(ClientComboBox.getItems());    
        SQLParty.getActiveClients().forEach(item -> ClientComboBox.getItems().addAll(item));
    }
    
    @FXML
    private void runButtonAction() {
        PartyModel selectedClient = (PartyModel) ClientComboBox.getValue();
                
        hash = ReportHashMap.clientID(hash, selectedClient.getId());
        stage.close();
    }
    
    @FXML private void closeButtonAction() {
        hash = null;
        stage.close();
    }
    
    public HashMap getHash() {
        return hash;
    }

    public void setHash(HashMap hash) {
        this.hash = hash;
    }
    
}
