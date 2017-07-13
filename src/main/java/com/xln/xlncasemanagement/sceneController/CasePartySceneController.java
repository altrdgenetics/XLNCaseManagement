package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.CasePartyTableModel;
import com.xln.xlncasemanagement.sql.SQLCaseParty;
import com.xln.xlncasemanagement.util.DebugTools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML TextField searchTextField;
    @FXML private TableView<CasePartyTableModel> partyTable;
    @FXML private TableColumn<CasePartyTableModel, Object> objectColumn;
    @FXML private TableColumn<CasePartyTableModel, String> relationColumn;
    @FXML private TableColumn<CasePartyTableModel, String> nameColumn;
    @FXML private TableColumn<CasePartyTableModel, String> addressColumn;
    @FXML private TableColumn<CasePartyTableModel, String> phoneNumberColumn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                DebugTools.Printout("Party Table Single Click");
                
            } else if (event.getClickCount() >= 2) {
                DebugTools.Printout("Party Table Double Click");
                Global.getStageLauncher().detailedPartyAddEditScene(Global.getMainStage(), false, (PartyModel) row.getObject().getValue());
                //TODO: RELOAD TABLE
            }
        }
    }
    
    public void setActive() {
        search();
    }
        
    @FXML private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<CasePartyTableModel> list = SQLCaseParty.searchParty(searchParam, Global.getCurrentMatter().getId());
        loadTable(list);
    }
    
    private void loadTable(ObservableList<CasePartyTableModel> list) {
        partyTable.getItems().removeAll();
        if (list != null) {
            partyTable.setItems(list);
        }
        partyTable.getSelectionModel().clearSelection();
    }
}
