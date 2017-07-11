/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.PartyNamePrefixModel;
import com.xln.xlncasemanagement.model.table.PartyTableModel;
import com.xln.xlncasemanagement.model.table.MaintenancePartyNamePrefixTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    
    @FXML Button CancelButton;
    @FXML Button AddEditButton;
    @FXML Label headerLabel;
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
    
    public void setActive(boolean maintenanceModePassed) {
        maintenanceMode = maintenanceModePassed;
        
        headerLabel.setText(maintenanceMode ? "Party List" : "Add Party To Case");
        AddEditButton.setText(maintenanceMode ? "Edit" : "Add To Case");
        
        if (maintenanceMode && Global.getCurrentUser().isAdminRights()){
            activeColumn.setVisible(true);
        }
        
    }
    
    @FXML private void addNewPartyButtonAction() {
        Global.getStageLauncher().detailedPartyAddEditScene(stage, maintenanceMode, null);
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
    
}
