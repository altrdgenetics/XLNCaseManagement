/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.sql.SQLTemplate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceTemplateAddEditSceneController implements Initializable {

    Stage stage;
    TemplateModel templateObject;
    
    @FXML private Label headerLabel;
    @FXML private TextField NameTextField;
    @FXML private Button TemplateFileButton;
    @FXML private Button DownloadFileButton;
    @FXML private TextArea DescriptionTextArea;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
    }    
    
    public void setActive(Stage stagePassed, TemplateModel templateObjectPassed){
        stage = stagePassed;
        templateObject = templateObjectPassed;
        String title = "Add Template";
        String buttonText = "Add";
        TemplateFileButton.setText(title);
        
        if (templateObject != null){
            TemplateFileButton.setText(title.replace("Add", "Update"));
            title = "Edit Template";
            buttonText = "Save";
            loadInformation();
        }
        
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                NameTextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        NameTextField.setText(templateObject.getName());
        DescriptionTextArea.setText(templateObject.getDescription());
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            updateCompany();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insertCompany();
        }
        stage.close();
    }
    
    private void insertCompany() {
        templateObject = new TemplateModel();
        templateObject.setActive(true);
        templateObject.setName(NameTextField.getText().trim());
        templateObject.setDescription(DescriptionTextArea.getText().trim());
        
//        int id = SQLActivityType.insertActivityType(templateObject);
//        System.out.println("New Activity Type ID: " + id);
    }
    
    private void updateCompany() {
        templateObject.setName(NameTextField.getText().trim());
        templateObject.setDescription(DescriptionTextArea.getText().trim());
//        SQLActivityType.updateActivityTypeByID(templateObject);
    }
}
