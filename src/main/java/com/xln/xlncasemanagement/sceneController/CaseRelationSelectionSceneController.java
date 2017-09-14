/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.PartyRelationTypeModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLCaseParty;
import com.xln.xlncasemanagement.sql.SQLPartyRelationType;
import java.net.URL;
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
public class CaseRelationSelectionSceneController implements Initializable {

    Stage stage;
    PartyModel party;
    
    @FXML Label headerLabel;
    @FXML Label comboBoxLabel;
    @FXML ComboBox relationTypeComboBox;
    @FXML Button saveButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saveButton.disableProperty().bind(relationTypeComboBox.valueProperty().isNull());
        
        //Setup ComboBox
        StringConverter<PartyRelationTypeModel> converter = new StringConverter<PartyRelationTypeModel>() {
            @Override
            public String toString(PartyRelationTypeModel object) {
                return object.getPartyRelationType();
            }

            @Override
            public PartyRelationTypeModel fromString(String string) {
                return null;
            }
        };
        relationTypeComboBox.setConverter(converter);
    }    
    
    public void setActive(Stage stagePassed, PartyModel partyModel){
        stage = stagePassed;
        party = partyModel;
        String title = "Select Relation Type";
        stage.setTitle(title);
        loadComboBox();
    }
    
    private void loadComboBox() {
        for (PartyRelationTypeModel item : SQLPartyRelationType.getActivePartyRelationType()){
            relationTypeComboBox.getItems().addAll(item);
        }
    }
    
    @FXML private void handleCancelButton(){
        stage.close();
    }
    
    @FXML private void handleCreateCaseButton(){
        insertParty();
        refreshMainWindow();
        stage.close();
    }    
        
    private void insertParty(){
        PartyRelationTypeModel relation = (PartyRelationTypeModel) relationTypeComboBox.getValue(); 
        
        party.setMatterID(Global.getCurrentMatter().getId());
        party.setRelationID(relation.getId());
        party.setPartyID(party.getId());
        
        SQLAudit.insertAudit("Added PartyID: " + party.getPartyID() + " To MatterID: "  + Global.getCurrentMatter().getId());
        SQLCaseParty.insertCaseParty(party);
    }
    
    private void refreshMainWindow(){
        Global.getMainStageController().getCasePartySceneController().setActive();
    }
    
    
}
