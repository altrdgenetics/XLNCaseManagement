package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ExpenseModel;
import com.xln.xlncasemanagement.model.table.ExpensesTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLExpense;
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
public class ExpensesSceneController implements Initializable {

    @FXML TextField searchTextField;
    @FXML private TableView<ExpensesTableModel> expensesTable;
    @FXML private TableColumn<ExpensesTableModel, Object> objectColumn;
    @FXML private TableColumn<ExpensesTableModel, String> dateColumn;
    @FXML private TableColumn<ExpensesTableModel, String> userColumn;
    @FXML private TableColumn<ExpensesTableModel, String> descriptionColumn;
    @FXML private TableColumn<ExpensesTableModel, String> costColumn;
    @FXML private TableColumn<ExpensesTableModel, String> recieptColumn;
    @FXML private TableColumn<ExpensesTableModel, Boolean> invoicedColumn;
        
    /**
     * Initializes the controller class.
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
        initializeUserColumn();
        initializeDescriptionColumn();
        initializeCostColumn();
        initializeReceiptColumn();
        initializeInvoicedColumn();
    }

    private void initializeObjectColumn() {
        objectColumn.setCellValueFactory(cellData -> cellData.getValue().getObject());
    }

    private void initializeDateColumn() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
        dateColumn.setCellFactory((TableColumn<ExpensesTableModel, String> param) -> {
            TableCell<ExpensesTableModel, String> cell = new TableCell<ExpensesTableModel, String>() {
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

    private void initializeUserColumn() {
        userColumn.setCellValueFactory(cellData -> cellData.getValue().getUser());
        userColumn.setCellFactory((TableColumn<ExpensesTableModel, String> param) -> {
            TableCell<ExpensesTableModel, String> cell = new TableCell<ExpensesTableModel, String>() {
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
        descriptionColumn.setCellFactory((TableColumn<ExpensesTableModel, String> param) -> {
            TableCell<ExpensesTableModel, String> cell = new TableCell<ExpensesTableModel, String>() {
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

    private void initializeCostColumn() {
        costColumn.setCellValueFactory(cellData -> cellData.getValue().getCost());
        costColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        costColumn.setCellFactory((TableColumn<ExpensesTableModel, String> param) -> {
            TableCell<ExpensesTableModel, String> cell = new TableCell<ExpensesTableModel, String>() {
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

    private void initializeReceiptColumn() {
        recieptColumn.setCellValueFactory(cellData -> cellData.getValue().getReceipt());
        // SETTING THE CELL FACTORY FOR THE RATINGS COLUMN         
        recieptColumn.setCellFactory((TableColumn<ExpensesTableModel, String> param) -> {
            TableCell<ExpensesTableModel, String> cell = new TableCell<ExpensesTableModel, String>() {
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
        recieptColumn.setStyle("-fx-alignment: CENTER;");
    }

    private void initializeInvoicedColumn() {
        invoicedColumn.setCellValueFactory(cellData -> cellData.getValue().getInvoiced());
        invoicedColumn.setCellFactory((TableColumn<ExpensesTableModel, Boolean> param) -> {
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
        ExpensesTableModel row = expensesTable.getItems().get(cellIndex);

        if (row != null) {
            DebugTools.Printout("Expense Table Double Click");
            Global.getStageLauncher().detailedExpenseAddEditScene(Global.getMainStage(), (ExpenseModel) row.getObject().getValue());
            search();
        }
    }

    @FXML private void search(){
        if (Global.getCurrentMatter() != null){
            String[] searchParam = searchTextField.getText().trim().split(" ");
            ObservableList<ExpensesTableModel> list = SQLExpense.searchExpenses(searchParam, Global.getCurrentMatter().getId());
            loadTable(list);
        }
    }
    
    private void loadTable(ObservableList<ExpensesTableModel> list) {
        expensesTable.getItems().removeAll();
        if (list != null) {
            expensesTable.setItems(list);
        }
        expensesTable.getSelectionModel().clearSelection();
        Global.getMainStageController().getButtonDelete().setDisable(true);
    }
    
    public void disableExpense(){
        ExpensesTableModel row = expensesTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            ExpenseModel item = (ExpenseModel) row.getObject().getValue();
            
            SQLActiveStatus.setActive("table13", item.getId(), false);
            search();
        }
    }
    
    private void handleOpenFile(int cellIndex) {
        ExpensesTableModel row = expensesTable.getItems().get(cellIndex);
        if (row != null) {
            DebugTools.Printout("Clicked Icon Twice");
        }
    }
}
