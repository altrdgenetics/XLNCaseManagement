/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.sql.SQLMatter;
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
        loadInformation();
    }

    public void mainPanelButtonFourAction() {
        updateMode = !updateMode;
        setEditableStatus(updateMode);
        
        if (updateMode) {
            notesArea.requestFocus();
        } else {
            saveInformation();
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
    
    private void loadInformation() {
        notesArea.setText("");
        if (Global.getCurrentMatter() != null) {
            Global.setCurrentMatter(SQLMatter.getMatterByID(Global.getCurrentMatter().getId()));
            notesArea.setText(Global.getCurrentMatter().getNote() == null
                    ? "" : Global.getCurrentMatter().getNote().trim());
        }
    }

    private void saveInformation(){
        Global.getCurrentMatter().setNote(notesArea.getText().trim().equals("") 
                ? null : notesArea.getText().trim());
        
        SQLMatter.updateMAtterNoteByID(Global.getCurrentMatter());
    }
}
