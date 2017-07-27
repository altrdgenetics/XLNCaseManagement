/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;


import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MakeModel;
import com.xln.xlncasemanagement.model.table.MaintenanceMakeTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLMake;
import com.xln.xlncasemanagement.util.TableObjects;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
public class MaintenanceMakeSceneController implements Initializable {

    Stage stage;

    @FXML
    private Label headerLabel;
    @FXML
    private Label emptyTableLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<MaintenanceMakeTableModel> searchTable;
    @FXML
    private TableColumn<MaintenanceMakeTableModel, Object> iDColumn;
    @FXML
    private TableColumn<MaintenanceMakeTableModel, Boolean> activeColumn;
    @FXML
    private TableColumn<MaintenanceMakeTableModel, String> makeColumn;
    @FXML
    private TableColumn<MaintenanceMakeTableModel, String> websiteColumn;
    
    List<TableColumn<MaintenanceMakeTableModel, ?>> sortOrder;  

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
        activeColumn.setCellFactory((TableColumn<MaintenanceMakeTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1) {
                    checkboxlistener(cell.getIndex());
                }
            });
            return cell;
        });
        makeColumn.setCellValueFactory(cellData -> cellData.getValue().getMake());
        
        websiteColumn.setCellValueFactory(cellData -> cellData.getValue().getWebSite());
        // SETTING THE CELL FACTORY FOR THE RATINGS COLUMN         
        websiteColumn.setCellFactory((TableColumn<MaintenanceMakeTableModel, String> param) -> {
            TableCell<MaintenanceMakeTableModel, String> cell = new TableCell<MaintenanceMakeTableModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        //Insert Icon for File
                        setGraphic(TableObjects.websiteIcon(item));
                    }
                }
            };
            return cell;
        });
        websiteColumn.setStyle("-fx-alignment: CENTER;");
        
        //Edit Button Listener
        editButton.disableProperty().bind(Bindings.isEmpty(searchTable.getSelectionModel().getSelectedItems()));
    }

    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        stage.setTitle(Global.getHeaderLabel2().replace(":", "") + " Maintenance");
        headerLabel.setText(Global.getHeaderLabel2().replace(":", "") + " Maintenance");
        makeColumn.setText(Global.getHeaderLabel2().replace(":", ""));
        emptyTableLabel.setText("No " + Global.getHeaderLabel2().replace(":", ""));
        search();
    }

    private void checkboxlistener(int cellIndex) {
        MaintenanceMakeTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            MakeModel item = (MakeModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table24", item.getId(), row.getChecked());
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search() {
        Platform.runLater(() -> {
            getSortedColumn();
            searchTable.getItems().clear();
            String[] searchParam = searchTextField.getText().trim().split(" ");
            ObservableList<MaintenanceMakeTableModel> list = SQLMake.searchMakes(searchParam);
            loadTable(list);
            setSortedColumn();
            searchTable.refresh();
        });
    }

    private void getSortedColumn() {
        sortOrder = new ArrayList<>(searchTable.getSortOrder());
    }
    
    private void setSortedColumn() {
        if (sortOrder != null) {
            searchTable.getSortOrder().clear();
            searchTable.getSortOrder().addAll(sortOrder);
        }
    }    
    
    private void loadTable(ObservableList<MaintenanceMakeTableModel> list) {
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
        MaintenanceMakeTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editButtonAction();                
            }
        }
    }

    @FXML
    private void addNewButtonAction() {
        Global.getStageLauncher().MaintenanceMakeAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editButtonAction() {
        MaintenanceMakeTableModel row = searchTable.getSelectionModel().getSelectedItem();
        Global.getStageLauncher().MaintenanceMakeAddEditScene(stage, (MakeModel) row.getObject().getValue());
        search();
    }
    
}
