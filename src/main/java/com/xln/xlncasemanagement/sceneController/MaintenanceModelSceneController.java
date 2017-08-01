/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;


import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ModelModel;
import com.xln.xlncasemanagement.model.table.MaintenanceModelTableModel;
import com.xln.xlncasemanagement.sql.SQLActiveStatus;
import com.xln.xlncasemanagement.sql.SQLModel;
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
public class MaintenanceModelSceneController implements Initializable {

    Stage stage;
    boolean maintenanceMode;
    int makeID;
    ModelModel selectedModel  = null;

    @FXML
    private Label headerLabel;
    @FXML
    private Label emptyTableLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button clearButton;    
    @FXML
    private Button closeButton;
    @FXML
    private TableView<MaintenanceModelTableModel> searchTable;
    @FXML
    private TableColumn<MaintenanceModelTableModel, Object> iDColumn;
    @FXML
    private TableColumn<MaintenanceModelTableModel, Boolean> activeColumn;
    @FXML
    private TableColumn<MaintenanceModelTableModel, String> makeColumn;
    @FXML
    private TableColumn<MaintenanceModelTableModel, String> modelColumn;
    @FXML
    private TableColumn<MaintenanceModelTableModel, String> websiteColumn;
    
    List<TableColumn<MaintenanceModelTableModel, ?>> sortOrder;  

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
        activeColumn.setCellFactory((TableColumn<MaintenanceModelTableModel, Boolean> param) -> {
            CheckBoxTableCell cell = new CheckBoxTableCell<>();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (cell.getIndex() > -1) {
                    checkboxlistener(cell.getIndex());
                }
            });
            return cell;
        });
        makeColumn.setCellValueFactory(cellData -> cellData.getValue().getMake());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().getModel());
        
        websiteColumn.setCellValueFactory(cellData -> cellData.getValue().getWebSite());
        // SETTING THE CELL FACTORY FOR THE RATINGS COLUMN         
        websiteColumn.setCellFactory((TableColumn<MaintenanceModelTableModel, String> param) -> {
            TableCell<MaintenanceModelTableModel, String> cell = new TableCell<MaintenanceModelTableModel, String>() {
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

    public void setActive(Stage stagePassed, boolean maintenanceModePassed, int makePassed) {
        stage = stagePassed;
        maintenanceMode = maintenanceModePassed;
        makeID = makePassed;
        makeColumn.setText(Global.getHeaderLabel2().replace(":", ""));
        modelColumn.setText(Global.getHeaderLabel3().replace(":", ""));
        emptyTableLabel.setText("No " + Global.getHeaderLabel3().replace(":", ""));
        stage.setTitle((maintenanceMode) 
                ? Global.getHeaderLabel3().replace(":", "") + " Maintenance" 
                : "Select " + Global.getHeaderLabel3().replace(":", ""));
        headerLabel.setText((maintenanceMode) 
                ? Global.getHeaderLabel3().replace(":", "") + " Maintenance" 
                : "Select " + Global.getHeaderLabel3().replace(":", ""));
        
        editButton.setText(maintenanceMode ? "Edit" : "Select");
        if (!maintenanceMode){
            activeColumn.setVisible(false);
        } else {
            clearButton.setVisible(false);
        }
        search();
        
        
        
        
    }

    private void checkboxlistener(int cellIndex) {
        MaintenanceModelTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            ModelModel item = (ModelModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table25", item.getId(), row.getChecked());
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search() {
        Platform.runLater(() -> {
            getSortedColumn();
            searchTable.getItems().clear();
            String[] searchParam = searchTextField.getText().trim().split(" ");
            if (!maintenanceMode){
                makeColumn.setPrefWidth(297.5);
                modelColumn.setPrefWidth(297.5);
            }
            ObservableList<MaintenanceModelTableModel> list = SQLModel.searchModels(searchParam, makeID);
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
    
    private void loadTable(ObservableList<MaintenanceModelTableModel> list) {
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
        MaintenanceModelTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() >= 2) {
                editButtonAction();                
            }
        }
    }

    @FXML
    private void addNewButtonAction() {
        Global.getStageLauncher().MaintenanceModelAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editButtonAction() {
        MaintenanceModelTableModel row = searchTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            ModelModel selected = (ModelModel) row.getObject().getValue();
            
            if (maintenanceMode) {
                Global.getStageLauncher().MaintenanceModelAddEditScene(stage, (ModelModel) row.getObject().getValue());
                search();
            } else {
                selectedModel = selected;
                stage.close();
            }
        }
    }
    
    @FXML
    private void handleClear() {
        ModelModel item = new ModelModel();
        item.setId(0);
        item.setActive(false);
        item.setName("");
        item.setWebsite("");
        selectedModel = item;
        stage.close();
    }

    public ModelModel getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(ModelModel selectedModel) {
        this.selectedModel = selectedModel;
    }
        
}
