/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ReportModel;
import com.xln.xlncasemanagement.model.table.MaintenanceReportParametersTableModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLReport;
import com.xln.xlncasemanagement.sql.SQLReportParameter;
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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceReportAddEditSceneController implements Initializable {

    Stage stage;
    ReportModel reportObject;
    File fileSelection;
    @FXML private Button addParameterButton;
    @FXML private Button removeParameterButton;
    @FXML private Label headerLabel;
    @FXML private TextField NameTextField;
    @FXML private Button TemplateFileButton;
    @FXML private Button DownloadFileButton;
    @FXML private TextArea DescriptionTextArea;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private ProgressBar progressBar;
    
    @FXML
    private TableView<MaintenanceReportParametersTableModel> paramTable;
    @FXML
    private TableColumn<MaintenanceReportParametersTableModel, String> iDColumn;
    @FXML
    private TableColumn<MaintenanceReportParametersTableModel, String> nameColumn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setListeners();
        setTable();
    }    
    
    private void setTable(){
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
    }
    
    public void setActive(Stage stagePassed, ReportModel reportObjectPassed){
        stage = stagePassed;
        reportObject = reportObjectPassed;
        String title = "Add Report";
        String buttonText = "Add";
                
        if (reportObject != null){
            reportObject = SQLReport.getReportByID(reportObject.getId());
            if (reportObject.getFileBlob() != null){
                TemplateFileButton.setText(title.replace("Add", "Update"));
            } else {
                TemplateFileButton.setText(title);
                DownloadFileButton.setVisible(false);
            }
            title = "Edit Report";
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
    
    @FXML
    private void tableListener(MouseEvent event) {
        MaintenanceReportParametersTableModel row = paramTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            removeParameterButton.setDisable(false);
        }
    }
    
    private void loadInformation(){
        NameTextField.setText(reportObject.getName() == null ? "" : reportObject.getName());
        DescriptionTextArea.setText(reportObject.getDescription() == null ? "" : reportObject.getDescription());
        loadParameterTable();
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
                    updateReport();
                    keyID = reportObject.getId();
                } else if ("Add".equals(saveButton.getText().trim())) {
                    keyID = insertReport();                    
                }

                SQLAudit.insertAudit(("Save".equals(saveButton.getText().trim()) 
                        ? "Updated" : "Added") + " Report ID: " + keyID);
                
                if (fileSelection != null && keyID > 0) {
                    long lStartTime = System.currentTimeMillis(); 
                    success = SQLReport.insertReportFile(keyID, fileSelection);
                    long lEndTime = System.currentTimeMillis();
                    DebugTools.Printout("Saved File In: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }

                if (success) {
                    Platform.runLater(() -> {
                        stage.close();
                    });
                } else {
                    reportObject = SQLReport.getReportByID(keyID);
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
        File selectedFile = SQLTemplate.openTemplateFile(reportObject.getId());

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
                new FileChooser.ExtensionFilter("Jasper", "*.jasper")
            );
        
         return fileChooser.showOpenDialog(stage);
    }
    
    private int insertReport() {
        reportObject = new ReportModel();
        reportObject.setActive(true);
        reportObject.setName(NameTextField.getText().trim());
        reportObject.setDescription(DescriptionTextArea.getText().trim().equals("") 
                ? null : DescriptionTextArea.getText().trim());
        
        return SQLReport.insertReport(reportObject);
    }
    
    private void updateReport() {
        reportObject.setName(NameTextField.getText().trim());
        reportObject.setDescription(DescriptionTextArea.getText().trim());
        SQLReport.updateReportByID(reportObject);
    }
        
    private void setPanelDisabled(boolean disabled) {
        closeButton.setDisable(disabled);
        NameTextField.setDisable(disabled);
        TemplateFileButton.setDisable(disabled);
        DownloadFileButton.setDisable(disabled);
        DescriptionTextArea.setDisable(disabled);
    }
        
    @FXML private void addParameterButton(){
        if (reportObject == null){
            int id = insertReport();
            reportObject = SQLReport.getReportByID(id);
            saveButton.setText("Save");
        }
        
        Global.getStageLauncher().ReportParameterScene(stage, reportObject.getId());
        loadParameterTable();
    }
    
    @FXML private void removeParameterButton() {
        MaintenanceReportParametersTableModel row = paramTable.getSelectionModel().getSelectedItem();

        if (row != null) {
            boolean approved = AlertDialog.StaticAlert(
                    1,
                    "Remove Parameter",
                    "Do You Wish To Remove This Parameter?",
                    ""
            );

            if (approved) {
                int parameterID = Integer.valueOf(row.getId().getValue());
                SQLReportParameter.deleteReportParameter(parameterID);
                loadParameterTable();
                SQLAudit.insertAudit("Removed Report Parameter ID: " + parameterID + " For Report ID: " + reportObject.getId());
            }
        }
    }

    private void loadParameterTable(){
        removeParameterButton.setDisable(true);
        
        ObservableList<MaintenanceReportParametersTableModel> list = SQLReportParameter.getParameterList(reportObject.getId());
        loadTable(list);
    }
    
    private void loadTable(ObservableList<MaintenanceReportParametersTableModel> list) {
        paramTable.getItems().removeAll();
        if (list != null) {
            paramTable.setItems(list);
        }
        paramTable.getSelectionModel().clearSelection();
    }
    
}
