/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;


import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ReportModel;
import com.xln.xlncasemanagement.model.table.MaintenanceReportTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLReport;
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
public class MaintenanceReportSceneController implements Initializable {

    Stage stage;

    @FXML
    private TextField searchTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<MaintenanceReportTableModel> searchTable;
    @FXML
    private TableColumn<MaintenanceReportTableModel, Object> iDColumn;
    @FXML
    private TableColumn<MaintenanceReportTableModel, Boolean> activeColumn;
    @FXML
    private TableColumn<MaintenanceReportTableModel, String> nameColumn;
    @FXML
    private TableColumn<MaintenanceReportTableModel, String> descriptionColumn;
    
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
        activeColumn.setCellFactory((TableColumn<MaintenanceReportTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, ev -> {
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
        stage.setTitle("Report Maintenance");
        search();
    }

    private void checkboxlistener(int cellIndex) {
        MaintenanceReportTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            ReportModel item = (ReportModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table19", item.getId(), row.getChecked());
            SQLAudit.insertAudit("Changed Active Flag " + row.getChecked() 
                    + "For Report ID: " + item.getId());
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search(){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<MaintenanceReportTableModel> list = SQLReport.searchReports(searchParam);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<MaintenanceReportTableModel> list) {
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
        MaintenanceReportTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editButtonAction();                
            }
        }
    }

    @FXML
    private void addNewButtonAction() {
        Global.getStageLauncher().MaintenanceReportAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editButtonAction() {
        MaintenanceReportTableModel row = searchTable.getSelectionModel().getSelectedItem();
        Global.getStageLauncher().MaintenanceReportAddEditScene(stage, (ReportModel) row.getObject().getValue());
        search();
    }
    
}
