/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;


import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterTypeModel;
import com.xln.xlncasemanagement.model.table.MaintenanceMatterTypeTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLMatterType;
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
public class MaintenanceMatterTypeSceneController implements Initializable {

    Stage stage;

    @FXML
    private TextField searchTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<MaintenanceMatterTypeTableModel> searchTable;
    @FXML
    private TableColumn<MaintenanceMatterTypeTableModel, Object> iDColumn;
    @FXML
    private TableColumn<MaintenanceMatterTypeTableModel, Boolean> activeColumn;
    @FXML
    private TableColumn<MaintenanceMatterTypeTableModel, String> nameColumn;

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
        activeColumn.setCellFactory((TableColumn<MaintenanceMatterTypeTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1) {
                    checkboxlistener(cell.getIndex());
                }
            });
            return cell;
        });
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getMatterType());
        
        //Edit Button Listener
        editButton.disableProperty().bind(Bindings.isEmpty(searchTable.getSelectionModel().getSelectedItems()));
    }

    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        stage.setTitle("Matter Type Maintenance");
        search();
    }

    private void checkboxlistener(int cellIndex) {
        MaintenanceMatterTypeTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            MatterTypeModel item = (MatterTypeModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table23", item.getId(), row.getChecked());
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<MaintenanceMatterTypeTableModel> list = SQLMatterType.searchMatterTypes(searchParam);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<MaintenanceMatterTypeTableModel> list) {
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
        MaintenanceMatterTypeTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editCompanyButtonAction();                
            }
        }
    }

    @FXML
    private void addNewCompanyButtonAction() {
        Global.getStageLauncher().MaintenanceMatterTypeAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editCompanyButtonAction() {
        MaintenanceMatterTypeTableModel row = searchTable.getSelectionModel().getSelectedItem();
        Global.getStageLauncher().MaintenanceMatterTypeAddEditScene(stage, (MatterTypeModel) row.getObject().getValue());
        search();
    }
    
}
