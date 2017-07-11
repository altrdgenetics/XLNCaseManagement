/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;


import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyRelationTypeModel;
import com.xln.xlncasemanagement.model.table.MaintenancePartyRelationTypeTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLPartyRelationType;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenancePartyRelationTypeSceneController implements Initializable {

    Stage stage;

    @FXML
    private TextField searchTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<MaintenancePartyRelationTypeTableModel> searchTable;
    @FXML
    private TableColumn<MaintenancePartyRelationTypeTableModel, Object> iDColumn;
    @FXML
    private TableColumn<MaintenancePartyRelationTypeTableModel, Boolean> activeColumn;
    @FXML
    private TableColumn<MaintenancePartyRelationTypeTableModel, String> nameColumn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup Table
        searchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().getObject());        
        activeColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());
        activeColumn.setCellFactory((TableColumn<MaintenancePartyRelationTypeTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1) {
                    checkboxlistener(cell.getIndex());
                }
            });
            return cell;
        });
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartyRelationType());
        
        //Edit Button Listener
        editButton.disableProperty().bind(Bindings.isEmpty(searchTable.getSelectionModel().getSelectedItems()));
    }

    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        stage.setTitle("Party Relation Type Maintenance");
        search();
    }

    private void checkboxlistener(int cellIndex) {
        MaintenancePartyRelationTypeTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            PartyRelationTypeModel item = (PartyRelationTypeModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table17", item.getId(), row.getChecked());
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<MaintenancePartyRelationTypeTableModel> list = SQLPartyRelationType.searchPartyRelationTypes(searchParam);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<MaintenancePartyRelationTypeTableModel> list) {
        searchTable.getItems().removeAll();
        if (list != null) {
            searchTable.setItems(list);
        }
        searchTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
    
    @FXML
    private void tableListener(MouseEvent event) {
        MaintenancePartyRelationTypeTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editButtonAction();                
            }
        }
    }

    @FXML
    private void addNewButtonAction() {
        Global.getStageLauncher().MaintenancePartyRelationTypeAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editButtonAction() {
        MaintenancePartyRelationTypeTableModel row = searchTable.getSelectionModel().getSelectedItem();
        Global.getStageLauncher().MaintenancePartyRelationTypeAddEditScene(stage, (PartyRelationTypeModel) row.getObject().getValue());
        search();
    }
    
}
