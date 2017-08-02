/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.bookmarkProcessing.GenerateDocument;
import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.sql.SQLTemplate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LetterSelectionSceneController implements Initializable {

    Stage stage;
    
    @FXML ComboBox letterComboBox;
    @FXML Button closeButton;
    @FXML Button selectButton;
    @FXML TextArea descriptionTextArea;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectButton.disableProperty()
                .bind((letterComboBox.valueProperty().isNull()));
        
        //Setup ComboBox
        StringConverter<TemplateModel> converter = new StringConverter<TemplateModel>() {
            @Override
            public String toString(TemplateModel object) {
                return object.getName();
            }

            @Override
            public TemplateModel fromString(String string) {
                return null;
            }
        };
        letterComboBox.setConverter(converter);
    }    
    
    public void setActive(Stage stagePassed){
        stage = stagePassed;
        loadInforamtion();
    }
    
    private void loadInforamtion() {
        letterComboBox.getItems().removeAll(letterComboBox.getItems());
        SQLTemplate.getActiveTemplate().forEach(item -> letterComboBox.getItems().addAll(item));
    }
    
    @FXML private void comboBoxAction() {
        TemplateModel selectedItem = (TemplateModel) letterComboBox.getValue();
        if (selectedItem != null){
            descriptionTextArea.setText(selectedItem.getDescription() == null ? "" : selectedItem.getDescription().trim());
        }
    }
    
    @FXML private void selectButtonAction(){
        TemplateModel selectedItem = (TemplateModel) letterComboBox.getValue();
        TemplateModel template = SQLTemplate.geTemplateByID(selectedItem.getId());
        
        GenerateDocument.generateDocument(template, Global.getCurrentMatter());
    }
    
    @FXML private void handleClose() {
        stage.close();
    }
    
}
