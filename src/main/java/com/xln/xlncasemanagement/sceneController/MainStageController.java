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
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainStageController implements Initializable {

    Stage stage;
    
    //Inject Sub-FXML------------------------------------------------
    @FXML private AnchorPane informationScene; // Inject tab content
    @FXML private InformationSceneController informationSceneController; // Inject controller
    @FXML private BorderPane partyScene; // Inject tab content
    @FXML private PartySceneController partySceneController; // Inject controller
    @FXML private BorderPane activityScene; // Inject tab content
    @FXML private ActivitySceneController activitySceneController; // Inject controller
    @FXML private BorderPane expensesScene; // Inject tab content
    @FXML private ExpensesSceneController expensesSceneController; // Inject controller
    @FXML private AnchorPane notesScene; // Inject tab content
    @FXML private NotesSceneController notesSceneController; // Inject controller
    
    //Tabs----------------------------------------------------------
    @FXML private TabPane mainTabPane;
    @FXML private Tab informationTab;
    @FXML private Tab partyTab;
    @FXML private Tab activityTab;
    @FXML private Tab expenseTab;
    @FXML private Tab noteTab;
    
    //Left Hand Side Buttons----------------------------------------
    @FXML private Button buttonOne;
    @FXML private Button buttonTwo;
    @FXML private Button buttonThree;
    @FXML private Button buttonFour;
    @FXML private Button buttonFive;
    @FXML private Button buttonSix;
    @FXML private Button buttonSeven;
    @FXML private Button buttonDelete;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
            onTabSelection();
        });
    }    
        
    public void loadDefaults(Stage stagePassed) {
        stage = stagePassed;
        stage.setTitle("Case Management");
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void onTabSelection(){
        Tab tabTitle = mainTabPane.getSelectionModel().getSelectedItem();
        
        if (tabTitle.equals(informationTab)){
            System.out.println("Selected Information Tab");
            setInformationTabButtons();
            informationSceneController.setActive();
        } else if (tabTitle.equals(partyTab)) {
            System.out.println("Selected Party Tab");
            setPartyTabButtons();
            partySceneController.setActive();
        } else if (tabTitle.equals(activityTab)) {
            System.out.println("Selected Activity Tab");
            setActivityTabButtons();
            activitySceneController.setActive();
        } else if (tabTitle.equals(expenseTab)) {
            System.out.println("Selected Expense Tab");
            setExpenseTabButtons();
            expensesSceneController.setActive();
        } else if (tabTitle.equals(noteTab)){
            System.out.println("Selected Note Tab");
            setNoteTabButtons();
            notesSceneController.setActive();
        }
    }

    private void setInformationTabButtons(){
        buttonOne.setText("");
        buttonTwo.setText("");
        buttonThree.setText("");
        buttonFour.setText("");
        buttonFive.setText("");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
    }

    private void setPartyTabButtons(){
        buttonOne.setText("");
        buttonTwo.setText("");
        buttonThree.setText("");
        buttonFour.setText("");
        buttonFive.setText("");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
    }

    private void setActivityTabButtons(){
        buttonOne.setText("");
        buttonTwo.setText("");
        buttonThree.setText("");
        buttonFour.setText("");
        buttonFive.setText("");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
    }

    private void setExpenseTabButtons(){
        buttonOne.setText("");
        buttonTwo.setText("");
        buttonThree.setText("");
        buttonFour.setText("");
        buttonFive.setText("");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
    }

    private void setNoteTabButtons(){
        buttonOne.setText("");
        buttonTwo.setText("");
        buttonThree.setText("");
        buttonFour.setText("");
        buttonFive.setText("");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
    }

    

}
