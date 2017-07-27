/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MakeModel;
import com.xln.xlncasemanagement.model.sql.ModelModel;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sql.SQLMake;
import com.xln.xlncasemanagement.sql.SQLModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceModelAddEditSceneController implements Initializable {

    Stage stage;
    ModelModel modelObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private Label MakeComboBoxLabel;
    @FXML
    private ComboBox MakeComboBox;
    @FXML
    private Label ModelTextFieldLabel;
    @FXML
    private TextField ModelTextField;
    @FXML
    private TextField WebsiteTextField;
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
        
        //Setup User ComboBox
        StringConverter<MakeModel> converter = new StringConverter<MakeModel>() {
            @Override
            public String toString(MakeModel object) {
                return object.getName();
            }

            @Override
            public MakeModel fromString(String string) {
                return null;
            }
        };
        MakeComboBox.setConverter(converter);
    }    
    
    public void setActive(Stage stagePassed, ModelModel modelObjectPassed){
        stage = stagePassed;
        modelObject = modelObjectPassed;
        MakeComboBoxLabel.setText(Global.getHeaderLabel2());
        ModelTextFieldLabel.setText(Global.getHeaderLabel3());
        String title = "Add " + Global.getHeaderLabel3().replace(":", "");
        String buttonText = "Add";
        loadMakeComboBox();
        
        
        if (modelObject != null){
            title = "Edit " + Global.getHeaderLabel3().replace(":", "");
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                (MakeComboBox.valueProperty().isNull())
                        .or(ModelTextField.textProperty().isEmpty())
        );
    }
    
    private void loadMakeComboBox() {
        MakeComboBox.getItems().removeAll(MakeComboBox.getItems());
        for (MakeModel item : SQLMake.getActiveMake()){
            MakeComboBox.getItems().addAll(item);
        }
    }
    
    private void loadInformation(){
        MakeModel user = SQLMake.getMakeByID(modelObject.getMakeID());
                
        MakeComboBox.setValue(user);
        ModelTextField.setText(modelObject.getName() == null ? "" : modelObject.getName());
        WebsiteTextField.setText(modelObject.getWebsite() == null ? "" : modelObject.getWebsite());
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        if ("Save".equals(saveButton.getText().trim())){
            updateModel();
        } else if ("Add".equals(saveButton.getText().trim())) {
            insertModel();
        }
        stage.close();
    }
    
    private void insertModel() {
        modelObject = new ModelModel();
        MakeModel make = (MakeModel) MakeComboBox.getValue();
        
        modelObject.setActive(true);
        modelObject.setMakeID(make.getId());
        modelObject.setName(ModelTextField.getText().trim());
        modelObject.setWebsite(WebsiteTextField.getText().trim().equals("") ? null : WebsiteTextField.getText().trim());
        int id = SQLModel.insertModel(modelObject);
        System.out.println("New " + Global.getHeaderLabel3().replace(":", "") + " ID: " + id);
    }
    
    private void updateModel() {
        MakeModel make = (MakeModel) MakeComboBox.getValue();
        
        modelObject.setMakeID(make.getId());
        modelObject.setName(ModelTextField.getText().trim());
        modelObject.setWebsite(WebsiteTextField.getText().trim().equals("") ? null : WebsiteTextField.getText().trim());
        SQLModel.updateModelByID(modelObject);
    }
}
