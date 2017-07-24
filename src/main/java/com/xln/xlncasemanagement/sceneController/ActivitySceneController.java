package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.model.table.ActivityTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.TableObjects;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ActivitySceneController implements Initializable {

    @FXML TextField searchTextField;
    @FXML private TableView<ActivityTableModel> activityTable;
    @FXML private TableColumn<ActivityTableModel, Object> objectColumn;
    @FXML private TableColumn<ActivityTableModel, String> dateColumn;
    @FXML private TableColumn<ActivityTableModel, String> hoursColumn;
    @FXML private TableColumn<ActivityTableModel, String> userColumn;
    @FXML private TableColumn<ActivityTableModel, String> descriptionColumn;
    @FXML private TableColumn<ActivityTableModel, String> fileColumn;
    @FXML private TableColumn<ActivityTableModel, Boolean> invoicedColumn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup Table
        initializeTable();
    }

    private void initializeTable() {
        initializeObjectColumn();
        initializeDateColumn();
        initializeHoursColumn();
        initializeUserColumn();
        initializeDescriptionColumn();
        initializeFileColumn();
        initializeBillableColumn();
        initializeInvoicedColumn();
    }
    
    private void initializeObjectColumn() {
        objectColumn.setCellValueFactory(cellData -> cellData.getValue().getObject());
    }

    private void initializeDateColumn() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
        dateColumn.setCellFactory((TableColumn<ActivityTableModel, String> param) -> {
            TableCell<ActivityTableModel, String> cell = new TableCell<ActivityTableModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        setText(item);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    tableListener(cell.getIndex());
                }
            });
            return cell;
        });
        dateColumn.setStyle("-fx-alignment: CENTER;");
    }
    
    private void initializeHoursColumn() {
        hoursColumn.setCellValueFactory(cellData -> cellData.getValue().getHours());
        hoursColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        hoursColumn.setCellFactory((TableColumn<ActivityTableModel, String> param) -> {
            TableCell<ActivityTableModel, String> cell = new TableCell<ActivityTableModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        setText(item);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    tableListener(cell.getIndex());
                }
            });
            return cell;
        });
    }

    private void initializeUserColumn() {
        userColumn.setCellValueFactory(cellData -> cellData.getValue().getUser());
        userColumn.setCellFactory((TableColumn<ActivityTableModel, String> param) -> {
            TableCell<ActivityTableModel, String> cell = new TableCell<ActivityTableModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        setText(item);
                    }
                }
            };
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    tableListener(cell.getIndex());
                }
            });
            return cell;
        });
    }

    private void initializeDescriptionColumn() {
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescription());
        descriptionColumn.setCellFactory((TableColumn<ActivityTableModel, String> param) -> {
            TableCell<ActivityTableModel, String> cell = new TableCell<ActivityTableModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        setText(item);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    tableListener(cell.getIndex());
                }
            });
            return cell;
        });
    }

    private void initializeFileColumn() {
        fileColumn.setCellValueFactory(cellData -> cellData.getValue().getFile());
        // SETTING THE CELL FACTORY FOR THE RATINGS COLUMN         
        fileColumn.setCellFactory((TableColumn<ActivityTableModel, String> param) -> {
            TableCell<ActivityTableModel, String> cell = new TableCell<ActivityTableModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {

                        // Insert View Button To Table
                        //setGraphic(TableObjects.viewButton());
                        //
                        //Insert Icon for File
                        setGraphic(TableObjects.fileIcon(item));
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    handleOpenFile(cell.getIndex());
                }
            });

            return cell;
        });
        fileColumn.setStyle("-fx-alignment: CENTER;");
    }
    
    private void initializeBillableColumn() {
        invoicedColumn.setCellValueFactory(cellData -> cellData.getValue().getBillable());
        invoicedColumn.setCellFactory((TableColumn<ActivityTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    tableListener(cell.getIndex());
                }
            });
            return cell;
        });
    }

    private void initializeInvoicedColumn() {
        invoicedColumn.setCellValueFactory(cellData -> cellData.getValue().getInvoiced());
        invoicedColumn.setCellFactory((TableColumn<ActivityTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1 && event.getClickCount() >= 2) {
                    tableListener(cell.getIndex());
                }
            });
            return cell;
        });
    }
    
    public void setActive() {
        search();
    }

    private void tableListener(int cellIndex) {
        ActivityTableModel row = activityTable.getItems().get(cellIndex);

        if (row != null) {
            DebugTools.Printout("Activity Table Double Click");
            Global.getStageLauncher().detailedActivityAddEditScene(Global.getMainStage(), (ActivityModel) row.getObject().getValue());
            search();
        }
    }
    
    @FXML private void search(){
        if (Global.getCurrentMatter() != null){
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<ActivityTableModel> list = SQLActivity.searchActivity(searchParam, Global.getCurrentMatter().getId());
        loadTable(list);
        }
    }
    
    private void loadTable(ObservableList<ActivityTableModel> list) {
        activityTable.getItems().removeAll(activityTable.getItems());
        if (list != null) {
            activityTable.setItems(list);
        }
        activityTable.getSelectionModel().clearSelection();
        Global.getMainStageController().getButtonDelete().setDisable(true);
    }
    
    public void disableActivity(){
        ActivityTableModel row = activityTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            ActivityModel party = (ActivityModel) row.getObject().getValue();
            
            SQLActiveStatus.setActive("table01", party.getId(), false);
            search();
        }
    }
    
    private void handleOpenFile(int cellIndex) {
        ActivityTableModel row = activityTable.getItems().get(cellIndex);
        if (row != null) {
            DebugTools.Printout("Clicked Icon Twice");
        }
    }
    
}
