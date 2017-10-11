package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.CasePartyTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLCaseParty;
import com.xln.xlncasemanagement.util.DebugTools;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CasePartySceneController implements Initializable {

    @FXML Label emptyTableLabel;
    @FXML TextField searchTextField;
    @FXML private TableView<CasePartyTableModel> partyTable;
    @FXML private TableColumn<CasePartyTableModel, Object> objectColumn;
    @FXML private TableColumn<CasePartyTableModel, String> relationColumn;
    @FXML private TableColumn<CasePartyTableModel, String> nameColumn;
    @FXML private TableColumn<CasePartyTableModel, String> addressColumn;
    @FXML private TableColumn<CasePartyTableModel, String> phoneNumberColumn;
    
    List<TableColumn<CasePartyTableModel, ?>> sortOrder; 
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emptyTableLabel.setText("No Parties For This " + Global.getNewCaseType());

        //Setup Table
        objectColumn.setCellValueFactory(cellData -> cellData.getValue().getObject()); 
        relationColumn.setCellValueFactory(cellData -> cellData.getValue().getRelation());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
    }    
    
    @FXML
    private void tableListener(MouseEvent event) {
        CasePartyTableModel row = partyTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() == 1) {
                DebugTools.HandleInfoPrintout("Party Table Single Click");
                Global.getMainStageController().getButtonDelete().setDisable(false);                
            } else if (event.getClickCount() >= 2) {
                DebugTools.HandleInfoPrintout("Party Table Double Click");
                Global.getStageLauncher().detailedPartyAddEditScene(Global.getMainStage(), false, (PartyModel) row.getObject().getValue());
                
                if (row.getRelation().get().equals("Client")){
                    SQLCaseParty.updateHeaderPhoneAndEmail(Global.getCurrentMatter().getId());
                }
                search();
            }
        }
    }
    
    public void setActive() {
        search();
    }

    @FXML private void search() {
        Platform.runLater(() -> {
            getSortedColumn();
            partyTable.getItems().clear();
            if (Global.getCurrentMatter() != null) {
                String[] searchParam = searchTextField.getText().trim().split(" ");
                ObservableList<CasePartyTableModel> list = SQLCaseParty.searchParty(searchParam, Global.getCurrentMatter().getId());
                loadTable(list);
            }
            setSortedColumn();
            partyTable.refresh();
        });
    }

    private void loadTable(ObservableList<CasePartyTableModel> list) {
        if (list != null) {
            partyTable.setItems(list);
        }
        partyTable.getSelectionModel().clearSelection();
        Global.getMainStageController().getButtonDelete().setDisable(true);
    }
    
    public void disableContact(){
        CasePartyTableModel row = partyTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            PartyModel party = (PartyModel) row.getObject().getValue();
            
            SQLAudit.insertAudit("Deleted Case Party ID: " + party.getId());
            
            SQLActiveStatus.setActive("table04", party.getId(), false);
            search();
        }
    }
    
    private void getSortedColumn() {
        sortOrder = new ArrayList<>(partyTable.getSortOrder());
    }
    
    private void setSortedColumn() {
        if (sortOrder != null) {
            partyTable.getSortOrder().clear();
            partyTable.getSortOrder().addAll(sortOrder);
        }
    }
    
}
