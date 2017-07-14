package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ExpenseModel;
import com.xln.xlncasemanagement.model.table.ExpensesTableModel;
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
public class ExpensesSceneController implements Initializable {

    @FXML TextField searchTextField;
    @FXML private TableView<ExpensesTableModel> expensesTable;
    @FXML private TableColumn<ExpensesTableModel, Object> objectColumn;
    
    /**
     * Initializes the controller class.
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
        ExpensesTableModel row = expensesTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            if (event.getClickCount() == 1) {
                DebugTools.Printout("Party Table Single Click");
                Global.getMainStageController().getButtonDelete().setDisable(false);
                
                
            } else if (event.getClickCount() >= 2) {
                DebugTools.Printout("Party Table Double Click");
                //Global.getStageLauncher().detailedExpenseAddEditScene(Global.getMainStage(), (ExpenseModel) row.getObject().getValue());
                search();
            }
        }
    }
    
    @FXML private void search(){
//        String[] searchParam = searchTextField.getText().trim().split(" ");
//        ObservableList<ActivityTableModel> list = SQLCaseParty.searchParty(searchParam, Global.getCurrentMatter().getId());
//        loadTable(list);
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
//        ExpensesTableModel row = expensesTable.getSelectionModel().getSelectedItem();
//
//        if (row != null) {
//            PartyModel party = (PartyModel) row.getObject().getValue();
//            
//            SQLActiveStatus.setActive("table13", party.getId(), false);
//            search();
//        }
    }
}
