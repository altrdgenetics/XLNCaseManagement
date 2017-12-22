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
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.TableObjects;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    @FXML Label emptyTableLabel;
    @FXML TextField searchTextField;
    @FXML private TableView<ActivityTableModel> activityTable;
    @FXML private TableColumn<ActivityTableModel, Object> objectColumn;
    @FXML private TableColumn<ActivityTableModel, String> dateColumn;
    @FXML private TableColumn<ActivityTableModel, String> hoursColumn;
    @FXML private TableColumn<ActivityTableModel, String> userColumn;
    @FXML private TableColumn<ActivityTableModel, String> descriptionColumn;
    @FXML private TableColumn<ActivityTableModel, String> fileColumn;
    @FXML private TableColumn<ActivityTableModel, Boolean> billableColumn;
    @FXML private TableColumn<ActivityTableModel, Boolean> invoicedColumn;

    List<TableColumn<ActivityTableModel, ?>> sortOrder;  
    
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
        emptyTableLabel.setText("No Activities For This " + Global.getNewCaseType());
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
                tableListener(event, cell.getIndex());
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
                tableListener(event, cell.getIndex());
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
                tableListener(event, cell.getIndex());
            });
            return cell;
        });
        userColumn.setStyle("-fx-alignment: CENTER;");
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
                tableListener(event, cell.getIndex());
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
                        //Insert Icon for File
                        setGraphic(TableObjects.fileIcon(item));
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                handleOpenFile(event, cell.getIndex());
            });

            return cell;
        });
        fileColumn.setStyle("-fx-alignment: CENTER;");
    }

    private void initializeBillableColumn() {
        billableColumn.setCellValueFactory(cellData -> cellData.getValue().getBillable());
        billableColumn.setCellFactory((TableColumn<ActivityTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                tableListener(event, cell.getIndex());
            });
            return cell;
        });
    }

    private void initializeInvoicedColumn() {
        invoicedColumn.setCellValueFactory(cellData -> cellData.getValue().getInvoiced());
        invoicedColumn.setCellFactory((TableColumn<ActivityTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                tableListener(event, cell.getIndex());
            });
            return cell;
        });
    }
    
    public void setActive() {
        search();
    }

    private void tableListener(MouseEvent event, int cellIndex) {
        if (cellIndex > -1 && cellIndex < activityTable.getItems().size()) {
            ActivityTableModel row = activityTable.getItems().get(cellIndex);

            if (row != null) {
                if (event.getClickCount() == 1) {
                    DebugTools.HandleInfoPrintout("Activity Table Single Click");
                    Global.getMainStageController().getButtonDelete().setDisable(false);
                } else if (event.getClickCount() >= 2) {
                    DebugTools.HandleInfoPrintout("Activity Table Double Click");
                    Global.getStageLauncher().detailedActivityAddEditScene(Global.getMainStage(), (ActivityModel) row.getObject().getValue());
                    search();
                }
            }
        }
    }
    
    @FXML private void search() {
        Platform.runLater(() -> {
            getSortedColumn();
            activityTable.getItems().clear();
            if (Global.getCurrentMatter() != null) {
                String[] searchParam = searchTextField.getText().trim().split(" ");
                ObservableList<ActivityTableModel> list = SQLActivity.searchActivity(searchParam, Global.getCurrentMatter().getId());
                loadTable(list);
            }
            setSortedColumn();
            activityTable.refresh();
        });
        activityTable.refresh();
    }

    private void getSortedColumn() {
        sortOrder = new ArrayList<>(activityTable.getSortOrder());
    }
    
    private void setSortedColumn() {
        if (sortOrder != null) {
            activityTable.getSortOrder().clear();
            activityTable.getSortOrder().addAll(sortOrder);
        }
    }
    
    private void loadTable(ObservableList<ActivityTableModel> list) {
        if (list != null) {
            activityTable.setItems(list);
        }
        activityTable.getSelectionModel().clearSelection();
        Global.getMainStageController().getButtonDelete().setDisable(true);
    }
    
    public void disableActivity(){
        ActivityTableModel row = activityTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            ActivityModel act = (ActivityModel) row.getObject().getValue();
            
            if (!act.isInvoiced()){
                SQLAudit.insertAudit("Deleted Activity ID: " + act.getId());

                SQLActiveStatus.setActive("table01", act.getId(), false);
                search();
            } else {
                AlertDialog.StaticAlert(2, "Removal Error",
                    "Unable To Remove Entry",
                    "The entry has been invoiced and is unable to be removed.");
            }
        }
    }

    private void handleOpenFile(MouseEvent event, int cellIndex) {
        if (cellIndex > -1 && cellIndex < activityTable.getItems().size() && event.getClickCount() >= 2) {
            ActivityTableModel row = activityTable.getItems().get(cellIndex);
            if (row != null) {
                DebugTools.HandleInfoPrintout("Clicked Icon Twice");
                ActivityModel item = (ActivityModel) row.getObject().getValue();
                SQLAudit.insertAudit("Opened File For Activity ID: " + item.getId());
                
                Global.getStageLauncher().retrieveFileLoadingScene(Global.getMainStage(), "Activity", item.getId());
                activityTable.getSelectionModel().clearSelection(cellIndex);
            }
        }
    }
    
}
