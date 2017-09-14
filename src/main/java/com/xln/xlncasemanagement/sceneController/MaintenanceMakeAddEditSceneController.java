/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MakeModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLMake;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceMakeAddEditSceneController implements Initializable {

    Stage stage;
    MakeModel makeObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private Label makeLabel;
    @FXML
    private TextField makeTextField;
    @FXML
    private TextField websiteTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button closeButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
    }    
    
    public void setActive(Stage stagePassed, MakeModel makeObjectPassed){
        stage = stagePassed;
        makeObject = makeObjectPassed;
        makeLabel.setText(Global.getHeaderLabel2());
        String title = "Add " + Global.getHeaderLabel2().replace(":", "");
        String buttonText = "Add";
        
        if (makeObject != null){
            title = "Edit " + Global.getHeaderLabel2().replace(":", "");
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                makeTextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        makeTextField.setText(makeObject.getName());
        websiteTextField.setText(makeObject.getWebsite());
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            updateMake();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insertMake();
        }
        stage.close();
    }
    
    private void insertMake() {
        makeObject = new MakeModel();
        makeObject.setActive(true);
        makeObject.setName(makeTextField.getText().trim());
        makeObject.setWebsite(websiteTextField.getText().trim().equals("") ? null : websiteTextField.getText().trim());
        int id = SQLMake.insertMake(makeObject);
        System.out.println("New " + Global.getHeaderLabel2().replace(":", "") + " ID: " + id);
        SQLAudit.insertAudit("Added " + Global.getHeaderLabel2().replace(":", "") + " ID: " + id);
    }
    
    private void updateMake() {
        makeObject.setName(makeTextField.getText().trim());
        makeObject.setWebsite(websiteTextField.getText().trim().equals("") ? null : websiteTextField.getText().trim());
        SQLMake.updateMakeByID(makeObject);
        SQLAudit.insertAudit("Updated " + Global.getHeaderLabel2().replace(":", "") + " ID: " + makeObject.getId());
    }
}
