/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.report.GenerateReport;
import com.xln.xlncasemanagement.report.ReportHashMap;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLExpense;
import com.xln.xlncasemanagement.sql.SQLMatter;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class BillingSceneController implements Initializable {

    Stage stage;
    boolean billingMode;
    
    @FXML private Label HeaderLabel;
    @FXML private ComboBox ClientComboBox;
    @FXML private ComboBox MatterComboBox;
    @FXML private Label MatterLabel;
    @FXML private Button CloseButton;
    @FXML private Button RunButton;
    
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
        
        //Setup Client ComboBox
        StringConverter<MatterModel> converter2 = new StringConverter<MatterModel>() {
            @Override
            public String toString(MatterModel object) {
                return object.getMatterTypeName() + " - " + Global.getMmddyyyy().format(object.getOpenDate());
            }

            @Override
            public MatterModel fromString(String string) {
                return null;
            }
        };
        MatterComboBox.setConverter(converter2);
        
        
        
        RunButton.disableProperty().bind(
                (ClientComboBox.valueProperty().isNull())
                        .or(MatterComboBox.valueProperty().isNull())
        );
        
        MatterComboBox.disableProperty().bind(
                (ClientComboBox.valueProperty().isNull())
                .or(ClientComboBox.disabledProperty())
        );
    }    
    
    public void setActive(Stage stagePassed, boolean billingModePassed){
        stage = stagePassed;
        billingMode = billingModePassed;
        
        MatterLabel.setText(Global.getNewCaseType() + ":");
        
        if (billingMode){
            HeaderLabel.setText("Billing Selection");
        } else {
            HeaderLabel.setText("Pre-Billing Selection");
        }
        loadInformation();
        
    }
    
    private void loadInformation(){
        loadClientComboBox();
        if (Global.getCurrentClient() != null){
            ClientComboBox.getSelectionModel().select(Global.getCurrentClient());
        }
        
        if (ClientComboBox.getSelectionModel().getSelectedItem() != null){
            loadMatterComboBox();
            if (Global.getCurrentMatter()!= null){
                MatterComboBox.getSelectionModel().select(Global.getCurrentMatter());
            }
        }
        
    }
    
    @FXML private void handleClientSelection(){
        loadMatterComboBox();
    }
    
    public void loadClientComboBox(){
        ClientComboBox.getItems().removeAll(ClientComboBox.getItems());    
        SQLParty.getActiveClients().forEach(item -> ClientComboBox.getItems().addAll(item));
    }
    
    public void loadMatterComboBox(){
        MatterComboBox.getItems().removeAll(MatterComboBox.getItems());
        if (ClientComboBox.getSelectionModel().getSelectedItem() != null){
            PartyModel client = (PartyModel) ClientComboBox.getValue();            
            SQLMatter.getActiveMattersByClient(client.getId())
                    .forEach(item -> MatterComboBox.getItems().addAll(item));
        }
        MatterComboBox.getSelectionModel().selectFirst();
    }
    
    @FXML private void runButtonAction() {
        MatterModel selectedMatter = (MatterModel) MatterComboBox.getValue();
        PartyModel selectedClient = (PartyModel) ClientComboBox.getValue();
        
        SQLAudit.insertAudit("Generating " + (billingMode ? "Bill" : "Pre-Bill")
                + " For MatterID: " + selectedMatter.getId());        
        
        //Generate Information
        HashMap hash = new HashMap();
        hash = ReportHashMap.generateDefaultInformation(hash);
        hash = ReportHashMap.matterID(hash, selectedMatter.getId());
        hash = ReportHashMap.billingSubReports(hash);
        
        GenerateReport.generateBill(selectedClient, selectedMatter, billingMode, hash);
        
        if (billingMode){
            markEntriesAsInvoiced(selectedMatter.getId());
        }
    }
    
    @FXML private void closeButtonAction() {
        Global.getMainStageController().onTabSelection();
        stage.close();
    }
    
    private void markEntriesAsInvoiced(int matterID) {
        SQLActivity.markMatterActivitesAsInvoiced(matterID);
        SQLExpense.markMatterExpensesAsInvoiced(matterID);
    }
    
}
