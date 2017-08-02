/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.sql.SQLTemplate;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceTemplateAddEditSceneController implements Initializable {

    Stage stage;
    TemplateModel templateObject;
    File fileSelection;
    
    @FXML private Label headerLabel;
    @FXML private TextField NameTextField;
    @FXML private Button TemplateFileButton;
    @FXML private Button DownloadFileButton;
    @FXML private TextArea DescriptionTextArea;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private ProgressBar progressBar;
    
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
                
        if (templateObject != null){
            TemplateFileButton.setText(title.replace("Add", "Update"));
            title = "Edit Template";
            buttonText = "Save";
            loadInformation();
        } else {
            TemplateFileButton.setText(title);
            DownloadFileButton.setVisible(false);
        }
        
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                (NameTextField.textProperty().isEmpty())
                .or(NameTextField.disabledProperty())
        );
    }
    
    private void loadInformation(){
        NameTextField.setText(templateObject.getName());
        DescriptionTextArea.setText(templateObject.getDescription());
    }
    
    @FXML private void handleClose() {
        stage.close();
    }
        
    @FXML private void saveButtonAction() {
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    setPanelDisabled(true);
                    progressBar.setVisible(true);
                });

                int keyID = -1;
                boolean success = true;

                if ("Save".equals(saveButton.getText().trim())) {
                    updateTemplate();
                    keyID = templateObject.getId();
                } else if ("Add".equals(saveButton.getText().trim())) {
                    keyID = insertTemplate();
                }

                if (fileSelection != null && keyID > 0) {
                    long lStartTime = System.currentTimeMillis(); 
                    success = SQLTemplate.insertTemplateFile(keyID, fileSelection);
                    long lEndTime = System.currentTimeMillis();
                    DebugTools.Printout("Saved File In: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }

                if (success) {
                    Platform.runLater(() -> {
                        stage.close();
                    });
                } else {
                    templateObject = SQLTemplate.geTemplateByID(keyID);
                    Platform.runLater(() -> {
                        AlertDialog.StaticAlert(4, "Save Error",
                                "Unable To Insert File",
                                "The file was not able to be saved to the database. "
                                        + "The rest of the information was saved properly.");
                        loadInformation();
                        progressBar.setVisible(false);
                        setPanelDisabled(false);
                    });
                }
            }
        }.start();
    }
    
    @FXML private void handleFileButtonAction(){
        fileSelection = fileChooser();
        
        if (fileSelection != null){
            TemplateFileButton.setText(fileSelection.getName());
        }
    }
    
    @FXML private void handleDownloadFileAction() {
        long lStartTime = System.currentTimeMillis();        
        File selectedFile = SQLTemplate.openTemplateFile(templateObject.getId());

        if (selectedFile != null) {
            try {
                Desktop.getDesktop().open(selectedFile);
            } catch (IOException ex) {
                AlertDialog.StaticAlert(4, "Save Error",
                    "Unable To Retrieve File",
                    "The file was not able to be opened. Please try again later.");
            }
        } else {
            AlertDialog.StaticAlert(4, "Save Error",
                    "Unable To Retrieve File",
                    "The file was not able to be opened. Please try again later.");
        }
        
        long lEndTime = System.currentTimeMillis();
        DebugTools.Printout("Opened File In: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
    }
    
    private File fileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("DOCX", "*.docx")
            );
        
         return fileChooser.showOpenDialog(stage);
    }
    
    private int insertTemplate() {
        templateObject = new TemplateModel();
        templateObject.setActive(true);
        templateObject.setName(NameTextField.getText().trim());
        templateObject.setDescription(DescriptionTextArea.getText().trim());
        
        return SQLTemplate.insertTemplate(templateObject);
    }
    
    private void updateTemplate() {
        templateObject.setName(NameTextField.getText().trim());
        templateObject.setDescription(DescriptionTextArea.getText().trim());
        SQLTemplate.updateTemplateByID(templateObject);
    }
        
    private void setPanelDisabled(boolean disabled) {
        closeButton.setDisable(disabled);
        NameTextField.setDisable(disabled);
        TemplateFileButton.setDisable(disabled);
        DownloadFileButton.setDisable(disabled);
        DescriptionTextArea.setDisable(disabled);
    }
    
}
