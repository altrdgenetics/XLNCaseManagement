/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.table.PartyTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLParty;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PartySearchSceneController implements Initializable {

    Stage stage;
    boolean maintenanceMode;
    boolean newMatter;
    
    @FXML private TextField searchTextField;
    @FXML private Button CancelButton;
    @FXML private Button AddEditButton;
    @FXML private Label headerLabel;
    @FXML private TableView<PartyTableModel> searchTable;
    @FXML private TableColumn<PartyTableModel, Object> objectColumn;
    @FXML private TableColumn<PartyTableModel, Boolean> activeColumn;
    @FXML private TableColumn<PartyTableModel, String> nameColumn;
    @FXML private TableColumn<PartyTableModel, String> addressColumn;
    @FXML private TableColumn<PartyTableModel, String> phoneNumberColumn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup Table
        objectColumn.setCellValueFactory(cellData -> cellData.getValue().getObject()); 
        activeColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());
        activeColumn.setCellFactory((TableColumn<PartyTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1) {
                    checkboxlistener(cell.getIndex());
                }
            });
            return cell;
        });
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());
        
        //Add/Edit Button Listener
        AddEditButton.disableProperty().bind(Bindings.isEmpty(searchTable.getSelectionModel().getSelectedItems()));
    }    
    
    public void setActive(Stage stagePassed, boolean maintenanceModePassed, boolean newMatterPassed) {
        stage = stagePassed;
        maintenanceMode = maintenanceModePassed;
        newMatter = newMatterPassed;
        
        headerLabel.setText(maintenanceMode ? "Contact List" : "Add Party To Case");
        AddEditButton.setText(maintenanceMode ? "Edit" : "Add To Case");
        
        if (newMatter){
            AddEditButton.setText("Select");
        }
        
        if (maintenanceMode && Global.getCurrentUser().isAdminRights()){
            activeColumn.setVisible(true);
        }
        search();
    }
    
    @FXML private void addNewPartyButtonAction() {
        Global.getStageLauncher().detailedPartyAddEditScene(stage, maintenanceMode, null);
        search();
    }
    
    @FXML private void closeButtonAction() {
        stage.close();
    }
    
    private void checkboxlistener(int cellIndex) {
        PartyTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            String table = maintenanceMode ? "table16" : "table04"; //16 - Party | 04 - Case Party
                
            PartyModel item = (PartyModel) row.getObject().getValue();
            SQLActiveStatus.setActive(table, item.getId(), row.getChecked());
            searchTable.getSelectionModel().clearSelection();
        }
    }
    
    @FXML
    private void tableListener(MouseEvent event) {
        PartyTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editParty((PartyModel) row.getObject().getValue());                
            }
        }
    }
    
    @FXML private void addEditButtonAction(){
        PartyTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (newMatter){
                newCaseCreation((PartyModel) row.getObject().getValue());
            }else {
                if (maintenanceMode){
                    editParty((PartyModel) row.getObject().getValue());
                } else {
                    //TODO: Add to Case
                }
            }
        }
    }
    
    @FXML private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<PartyTableModel> list = SQLParty.searchParty(searchParam, maintenanceMode);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<PartyTableModel> list) {
        searchTable.getItems().removeAll();
        if (list != null) {
            searchTable.setItems(list);
        }
        searchTable.getSelectionModel().clearSelection();
    }
    
    private void editParty(PartyModel party){
        Global.getStageLauncher().detailedPartyAddEditScene(stage, maintenanceMode, party);
        search();
    }
    
    private void newCaseCreation(PartyModel party){
        Global.getStageLauncher().NewMatterCaseTypeSelectionScene(stage, party);
        stage.close();
    }
    
}
