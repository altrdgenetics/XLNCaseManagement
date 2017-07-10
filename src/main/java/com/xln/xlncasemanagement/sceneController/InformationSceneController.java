package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setActive() {
        DebugTools.Printout("Set Information Tab Active");
    }
        
    public void mainPanelButtonFourAction() {
        updateMode = !updateMode;
        setEditableStatus(updateMode);
        
        if (updateMode) {
            OpenDateDatePicker.requestFocus();
        } else {
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

    

}
