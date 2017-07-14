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
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author User
 */
public class InformationSceneController implements Initializable {

    boolean updateMode = false;
    
    @FXML DatePicker OpenDateDatePicker;
    @FXML DatePicker ClosedDateDatePicker;
    @FXML DatePicker WarrantyDateDatePicker;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

    public void setActive() {
        loadInformation();
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
