/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.MatterTypeModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.sql.SQLMatterType;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NewMatterCaseTypeSelectionSceneController implements Initializable {

    Stage stage;
    PartyModel party;
    
    @FXML Label headerLabel;
    @FXML Label comboBoxLabel;
    @FXML ComboBox matterTypeComboBox;
    @FXML Button saveButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saveButton.disableProperty().bind(matterTypeComboBox.valueProperty().isNull());
        
        //Setup ComboBox
        StringConverter<MatterTypeModel> converter = new StringConverter<MatterTypeModel>() {
            @Override
            public String toString(MatterTypeModel object) {
                return object.getMatterType();
            }

            @Override
            public MatterTypeModel fromString(String string) {
                return null;
            }
        };
        matterTypeComboBox.setConverter(converter);
    }    
    
    public void setActive(Stage stagePassed, PartyModel partyModel){
        stage = stagePassed;
        party = partyModel;
        String title = "Select " + Global.getNewCaseType() + " Type";
        stage.setTitle(title);
        headerLabel.setText(title);
        comboBoxLabel.setText(Global.getNewCaseType() + ":");
        loadComboBox();
    }
    
    private void loadComboBox() {
        for (MatterTypeModel item : SQLMatterType.getActiveMatterType()){
            matterTypeComboBox.getItems().addAll(item);
        }
    }
    
    @FXML private void handleCancelButton(){
        stage.close();
    }
    
    @FXML private void handleCreateCaseButton(){
        int newKeyID = insertMatter();
        insertPartyAsClient(newKeyID);
        
        refreshMainWindow(newKeyID);
        stage.close();
    }    
    
    private int insertMatter(){
        MatterTypeModel matter = (MatterTypeModel) matterTypeComboBox.getValue();  
        
        MatterModel item = new MatterModel();
        item.setActive(true);
        item.setPartyID(party.getId());
        item.setMatterTypeID(matter.getId());
        item.setOpenDate(new Date(System.currentTimeMillis()));
        item.setCloseDate(null);
        
        return SQLMatter.insertNewMatter(item);
    }
    
    private void insertPartyAsClient(int newKeyID){
        //TODO
    }
    
    private void refreshMainWindow(int newKeyID){
        Global.getMainStageController().loadClientComboBox();
        Global.getMainStageController().getClientField().setValue(party);
        Global.getMainStageController().getHeaderField1().setValue(SQLMatter.getMatterByID(newKeyID));
    }
    
}
