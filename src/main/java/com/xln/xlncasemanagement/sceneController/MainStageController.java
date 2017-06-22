/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainStageController implements Initializable {

    Stage stage;
    
    // Inject tab content.
    //@FXML private InformationTabScene informationTabScene;
    // Inject controller
    @FXML private InformationSceneController InformationSceneController;
    
    // Inject tab content.
    //@FXML private PartyTabScene partyTabScene;
    // Inject controller
    @FXML private PartySceneController PartySceneController;
    
    // Inject tab content.
    //@FXML private ActivityTabScene activityTabScene;
    // Inject controller
    @FXML private ActivitySceneController ActivitySceneController;

    // Inject tab content.
    //@FXML private ExpensesTabScene expensesTabScene;
    // Inject controller
    @FXML private ExpensesSceneController ExpensesSceneController;
    
    // Inject tab content.
    //@FXML private NotesTabScene notesTabScene;
    // Inject controller
    @FXML private NotesSceneController NotesSceneController;
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        
    public void loadDefaults(Stage stagePassed) {
        stage = stagePassed;
        stage.setTitle("Case Management");
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
                
    }
    
    
}
