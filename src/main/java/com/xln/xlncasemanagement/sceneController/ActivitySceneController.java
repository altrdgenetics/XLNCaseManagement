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
import com.xln.xlncasemanagement.util.DebugTools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setActive() {
        search();
    }

    @FXML private void tableListener(MouseEvent event) {
        ActivityTableModel row = activityTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() == 1) {
                DebugTools.Printout("Party Table Single Click");
                Global.getMainStageController().getButtonDelete().setDisable(false);
                
                
            } else if (event.getClickCount() >= 2) {
                DebugTools.Printout("Party Table Double Click");
                //Global.getStageLauncher().detailedActivityAddEditScene(Global.getMainStage(), (ActivityModel) row.getObject().getValue());
                search();
            }
        }
    }
    
    @FXML private void search(){
//        String[] searchParam = searchTextField.getText().trim().split(" ");
//        ObservableList<ActivityTableModel> list = SQLCaseParty.searchParty(searchParam, Global.getCurrentMatter().getId());
//        loadTable(list);
    }
    
    private void loadTable(ObservableList<ActivityTableModel> list) {
        activityTable.getItems().removeAll();
        if (list != null) {
            activityTable.setItems(list);
        }
        activityTable.getSelectionModel().clearSelection();
        Global.getMainStageController().getButtonDelete().setDisable(true);
    }
    
    public void disableActivity(){
//        ActivityTableModel row = activityTable.getSelectionModel().getSelectedItem();
//
//        if (row != null) {
//            ActivityModel party = (ActivityModel) row.getObject().getValue();
//            
//            SQLActiveStatus.setActive("table01", party.getId(), false);
//            search();
//        }
    }
    
}
