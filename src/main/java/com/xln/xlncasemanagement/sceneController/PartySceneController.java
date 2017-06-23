package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.PartyTableModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PartySceneController implements Initializable {

    @FXML
    private TableView<PartyTableModel> partyTable;
    @FXML
    private TableColumn<PartyTableModel, Object> objectColumn;
    @FXML
    private TableColumn<PartyTableModel, String> relationColumn;
    @FXML
    private TableColumn<PartyTableModel, String> nameColumn;
    @FXML
    private TableColumn<PartyTableModel, String> addressColumn;
    @FXML
    private TableColumn<PartyTableModel, String> phoneNumberColumn;
    
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
    
    public void setActive() {
        System.out.println("Set Party Tab Active");
        loadMOCKTable();
    }
    
    @FXML
    private void tableListener(MouseEvent event) {
        PartyTableModel row = partyTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() == 1) {
                System.out.println("Party Table Single Click");
                
            } else if (event.getClickCount() >= 2) {
                System.out.println("Party Table Double Click");
                Global.getStageLauncher().detailedCallAddEditStage(Global.getMainStage(), (PartyModel) row.getObject().getValue());
                //TODO: RELOAD TABLE
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // DELETE AFTER CODE IS DEMOED
    
    
    private void loadMOCKTable() {
        PartyModel party = new PartyModel();
        party.setRelation(1);
        party.setPrefix("Mr.");
        party.setFirstName("John");
        party.setMiddleInitial("A.");
        party.setLastName("Smith");
        party.setAddressOne("123 High St");
        party.setAddressTwo("");
        party.setAddressThree("");
        party.setCity("Columbus");
        party.setState("OH");
        party.setZip("43215");
        party.setPhoneOne("(555) 555-5555 x123");
        party.setPhoneTwo("");
        party.setEmail("noReply@gmail.com");
        
        
        ObservableList<PartyTableModel> list = FXCollections.observableArrayList();
        list.add(
                        new PartyTableModel(
                                party, 
                                "Client", 
                                "John A. Smith", 
                                "123 High St, Columbus, OH 43215", 
                                "(555) 555-5555 x123"
                        ));
        
        partyTable.getItems().removeAll();
        if (list != null) {
            partyTable.setItems(list);
        }
        partyTable.getSelectionModel().clearSelection();
    }
    
}
