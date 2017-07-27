package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.util.DebugTools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class InformationSceneController implements Initializable {

    boolean updateMode = false;
    
    //LEFT SIDE OF PANEL
    @FXML DatePicker OpenDateDatePicker;
    @FXML DatePicker ClosedDateDatePicker;
    @FXML Label Label1;
    @FXML DatePicker WarrantyDateDatePicker;
    @FXML Label Label2;
    @FXML TextField Label2TextField;
    @FXML Label Label3;
    @FXML ComboBox Label3ComboBox;
    @FXML Label Label4;
    @FXML ComboBox Label4ComboBox;
    @FXML Label Label5;
    @FXML TextField Label5TextField;
        
    //RIGHT SIDE OF PANEL
    @FXML TextField TotalHoursTextField;
    @FXML TextField BilledHoursTextField;
    @FXML TextField UnBilledHoursTextField;
    @FXML TextField TotalExpensesTextField;
    @FXML TextField BilledExpensesTextField;
    @FXML TextField UnBilledExpensesTextField;
    @FXML TextField TotalCostTextField;
    @FXML TextField BalanceTextField;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSelectorsHiddenOnNonEdit();        
    }

    private void setSelectorsHiddenOnNonEdit(){
        OpenDateDatePicker.setOnMouseClicked(e -> {
            if (!OpenDateDatePicker.isEditable() && OpenDateDatePicker.isShowing()) {
                OpenDateDatePicker.hide();
            }
        });
        ClosedDateDatePicker.setOnMouseClicked(e -> {
            if (!ClosedDateDatePicker.isEditable() && ClosedDateDatePicker.isShowing()) {
                ClosedDateDatePicker.hide();
            }
        });
        WarrantyDateDatePicker.setOnMouseClicked(e -> {
            if (!WarrantyDateDatePicker.isEditable() && WarrantyDateDatePicker.isShowing()) {
                WarrantyDateDatePicker.hide();
            }
        });
        Label3ComboBox.setOnMouseClicked(e -> {
            if (!Label3ComboBox.isEditable() && Label3ComboBox.isShowing()) {
                Label3ComboBox.hide();
            }
        });
        Label4ComboBox.setOnMouseClicked(e -> {
            if (!Label4ComboBox.isEditable() && Label4ComboBox.isShowing()) {
                Label4ComboBox.hide();
            }
        });
    }
    
    public void setActive() {
        setVersionInformation();
        loadInformation();
    }
        
    private void setVersionInformation(){
        Label1.setText(Global.getInformationLabel1());
        Label2.setText(Global.getInformationLabel2());
        Label3.setText(Global.getInformationLabel3());
        Label4.setText(Global.getInformationLabel4());
        Label5.setText(Global.getInformationLabel5());
    }
    
    public void mainPanelButtonFourAction() {
        updateMode = !updateMode;
        setEditableStatus(updateMode);
        
        if (updateMode) {
            loadInformation();
            OpenDateDatePicker.requestFocus();
        } else {
            saveInformation();
            DebugTools.Printout("Saved Information");
        }
    }
        
    public void mainPanelButtonDeleteAction() {
        updateMode = !updateMode;
        DebugTools.Printout("Reverted Information (Cancel Button Action)");
        setEditableStatus(false);
    }
    
    private void setEditableStatus(boolean editable){
        OpenDateDatePicker.setEditable(editable);
        ClosedDateDatePicker.setEditable(editable);
        WarrantyDateDatePicker.setEditable(editable);
        Label2TextField.setEditable(editable);
        Label3ComboBox.setEditable(editable);
        Label4ComboBox.setEditable(editable);
        Label5TextField.setEditable(editable);
    }

    public boolean isUpdateMode() {
        return updateMode;
    }

    private void loadInformation(){
        if (Global.getCurrentMatter() != null){
            Global.setCurrentMatter(SQLMatter.getMatterByID(Global.getCurrentMatter().getId()));
            
            OpenDateDatePicker.setValue(Global.getCurrentMatter().getOpenDate() == null 
                    ? null : Global.getCurrentMatter().getOpenDate().toLocalDate());
            ClosedDateDatePicker.setValue(Global.getCurrentMatter().getCloseDate() == null 
                    ? null : Global.getCurrentMatter().getCloseDate().toLocalDate());
        } else {
            clearWindow();
        }
    }

    public void clearWindow(){
        OpenDateDatePicker.setValue(null);
        ClosedDateDatePicker.setValue(null);
        WarrantyDateDatePicker.setValue(null);
    }
    
    private void saveInformation(){
        //Update Matter
        Global.getCurrentMatter().setOpenDate(OpenDateDatePicker.getValue() == null ? null : java.sql.Date.valueOf( OpenDateDatePicker.getValue() ));
        Global.getCurrentMatter().setCloseDate(ClosedDateDatePicker.getValue() == null ? null : java.sql.Date.valueOf( ClosedDateDatePicker.getValue() ));
        
        SQLMatter.updateMatterInformationByID(Global.getCurrentMatter());        
    }
    
}
