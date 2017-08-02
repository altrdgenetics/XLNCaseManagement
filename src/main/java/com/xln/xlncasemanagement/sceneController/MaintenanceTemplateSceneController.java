/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;


import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityTypeModel;
import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.model.table.MaintenanceTemplateTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLTemplate;
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
public class MaintenanceTemplateSceneController implements Initializable {

    Stage stage;

    @FXML
    private TextField searchTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<MaintenanceTemplateTableModel> searchTable;
    @FXML
    private TableColumn<MaintenanceTemplateTableModel, Object> iDColumn;
    @FXML
    private TableColumn<MaintenanceTemplateTableModel, Boolean> activeColumn;
    @FXML
    private TableColumn<MaintenanceTemplateTableModel, String> nameColumn;
    @FXML
    private TableColumn<MaintenanceTemplateTableModel, String> descriptionColumn;
    
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
        activeColumn.setCellFactory((TableColumn<MaintenanceTemplateTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1) {
                    checkboxlistener(cell.getIndex());
                }
            });
            return cell;
        });
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescription());
        
        //Edit Button Listener
        editButton.disableProperty().bind(Bindings.isEmpty(searchTable.getSelectionModel().getSelectedItems()));
    }

    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        stage.setTitle("Template Maintenance");
        search();
    }

    private void checkboxlistener(int cellIndex) {
        MaintenanceTemplateTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            ActivityTypeModel item = (ActivityTypeModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table02", item.getId(), row.getChecked());
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<MaintenanceTemplateTableModel> list = SQLTemplate.searchTemplates(searchParam);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<MaintenanceTemplateTableModel> list) {
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
        MaintenanceTemplateTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editButtonAction();                
            }
        }
    }

    @FXML
    private void addNewButtonAction() {
        Global.getStageLauncher().MaintenanceTemplateAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editButtonAction() {
        MaintenanceTemplateTableModel row = searchTable.getSelectionModel().getSelectedItem();
        Global.getStageLauncher().MaintenanceTemplateAddEditScene(stage, (TemplateModel) row.getObject().getValue());
        search();
    }
    
}
