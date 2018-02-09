/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.ClientTableModel;
import com.xln.xlncasemanagement.sql.SQLParty;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ClientSearchSceneController implements Initializable {

    Stage stage;
    public PartyModel party;
    
    @FXML private TextField searchTextField;
    @FXML private Button AddEditButton;
    @FXML private TableView<ClientTableModel> searchTable;
    @FXML private TableColumn<ClientTableModel, Object> objectColumn;
    @FXML private TableColumn<ClientTableModel, String> nameColumn;
    @FXML private TableColumn<ClientTableModel, String> addressColumn;
    @FXML private TableColumn<ClientTableModel, String> phoneNumberColumn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup Table
        objectColumn.setCellValueFactory(cellData -> cellData.getValue().getObject()); 
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        
        //Add/Edit Button Listener
        AddEditButton.disableProperty().bind(Bindings.isEmpty(searchTable.getSelectionModel().getSelectedItems()));
    }    
    
    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        search();
    }
    
    @FXML private void addNewPartyButtonAction() {
        Global.getStageLauncher().detailedPartyAddEditScene(stage, true, null);
        search();
    }
    
    @FXML private void closeButtonAction() {
        stage.close();
    }
    
    @FXML
    private void tableListener(MouseEvent event) {
        ClientTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                party = (PartyModel) row.getObject().getValue();
                stage.close();
            }
        }
    }
    
    @FXML private void addEditButtonAction(){
        ClientTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            party = (PartyModel) row.getObject().getValue();
            stage.close();
        }
    }
    
    @FXML private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<ClientTableModel> list = SQLParty.searchActiveClients(searchParam);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<ClientTableModel> list) {
        searchTable.getItems().removeAll();
        if (list != null) {
            searchTable.setItems(list);
        }
        searchTable.getSelectionModel().clearSelection();
    }

    public PartyModel getParty() {
        return party;
    }
    
}
