/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.sql.SQLExpense;
import com.xln.xlncasemanagement.util.DebugTools;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LoadingFileSceneController implements Initializable {

    Stage stage;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void getFile(Stage stagePassed, String type, int id){
        stage = stagePassed;
        
        new Thread() {
            @Override
            public void run() {
                retrieveFile(type, id);
                Platform.runLater(() -> {
                        Global.getMainStageController().disableEverythingForLoading(false);
                        stage.close();
                    });
            }
        }.start();   
    }
    
    
    private void retrieveFile(String type, int id) {
        File selectedFile = null;

        if (type.equals("Expense")) {
            selectedFile = SQLExpense.openExpenseFile(id);
        } else if (type.equals("Activity")) {
            selectedFile = SQLActivity.openActivityFile(id);
        }

        if (selectedFile != null) {
            try {
                Desktop.getDesktop().open(selectedFile);
            } catch (IOException ex) {
                Logger.getLogger(ExpensesSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
