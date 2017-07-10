/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.util.DebugTools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NotesSceneController implements Initializable {

    boolean updateMode = false;
    
    @FXML
    private TextArea notesArea;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setActive() {
        DebugTools.Printout("Set Notes Tab Active");
    }

    public void mainPanelButtonFourAction() {
        updateMode = !updateMode;
        setEditableStatus(updateMode);
        
        if (updateMode) {
            notesArea.requestFocus();
        } else {
            DebugTools.Printout("Saved Information");
        }
    }

    public void mainPanelButtonDeleteAction() {
        updateMode = !updateMode;
        DebugTools.Printout("Reverted Information (Cancel Button Action)");
        notesArea.setEditable(false);
    }
    
    private void setEditableStatus(boolean editable){
        notesArea.setEditable(editable);
    }    
    
    public boolean isUpdateMode() {
        return updateMode;
    }
}
