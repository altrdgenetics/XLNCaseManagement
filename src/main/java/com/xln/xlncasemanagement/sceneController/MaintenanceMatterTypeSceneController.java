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
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLMatterType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    boolean maintenanceMode;
    MatterTypeModel matterType = null;

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

    public void setActive(Stage stagePassed, boolean maintenanceModePassed) {
        stage = stagePassed;
        maintenanceMode = maintenanceModePassed;
        
        
        
        stage.setTitle((maintenanceMode 
                ? Global.getNewCaseType().replace(":", "") + " Type Maintenance" 
                : "Select " + Global.getNewCaseType().replace(":", "") + " Type"));
        headerLabel.setText((maintenanceMode
                ? Global.getNewCaseType().replace(":", "") + " Type Maintenance" 
                : "Select " + Global.getNewCaseType().replace(":", "")+ " Type"));
        editButton.setText(maintenanceMode ? "Edit" : "Select");
        if (!maintenanceMode){
            activeColumn.setVisible(false);
        }
        nameColumn.setText(Global.getNewCaseType() + " Type");
        emptyTableLabel.setText("No " + Global.getNewCaseType() + " Types");
        search();
    }

    private void checkboxlistener(int cellIndex) {
        MaintenanceMatterTypeTableModel row = searchTable.getItems().get(cellIndex);
        if (row != null) {
            MatterTypeModel item = (MatterTypeModel) row.getObject().getValue();
            SQLActiveStatus.setActive("table23", item.getId(), row.getChecked());
            SQLAudit.insertAudit("Changed Active Flag " + row.getChecked() 
                    + "For " + Global.getNewCaseType().replace(":", "") + " ID: " + item.getId());
            
            searchTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void search() {
        String[] searchParam = searchTextField.getText().trim().split(" ");
        ObservableList<MaintenanceMatterTypeTableModel> list = SQLMatterType.searchMatterTypes(searchParam);

        //Add Potential Client Matter Type
        if (!maintenanceMode) {
            MatterTypeModel item = new MatterTypeModel();
            item.setId(0);
            item.setActive(true);
            item.setMatterType(Global.getLeadWording());

            list.add(
                    new MaintenanceMatterTypeTableModel(
                            item,
                            true,
                            Global.getLeadWording()
                    ));
        }
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
                editButtonAction();                
            }
        }
    }

    @FXML
    private void addNewButtonAction() {
        Global.getStageLauncher().MaintenanceMatterTypeAddEditScene(stage, null);
        search();
    }
    
    @FXML
    private void editButtonAction() {
        MaintenanceMatterTypeTableModel row = searchTable.getSelectionModel().getSelectedItem();
        
        if (row != null) {
            MatterTypeModel selected = (MatterTypeModel) row.getObject().getValue();
            
            if (maintenanceMode) {
                Global.getStageLauncher().MaintenanceMatterTypeAddEditScene(stage, selected);
                search();
            } else {
                matterType = selected;
                stage.close();
            }
        }        
        search();
    }

    public MatterTypeModel getMatterType() {
        return matterType;
    }

    public void setMatterType(MatterTypeModel matterType) {
        this.matterType = matterType;
    }
    
}
