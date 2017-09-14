/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ReportModel;
import com.xln.xlncasemanagement.report.GenerateReport;
import com.xln.xlncasemanagement.report.ReportHashMap;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLReport;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReportSelectionSceneController implements Initializable {

    Stage stage;
    
    @FXML Label headerLabel;
    @FXML Label comboBoxLabel;
    @FXML ComboBox reportComboBox;
    @FXML Button runReportButton;
    @FXML Button closeButton;
    @FXML TextArea descriptionTextArea;
    @FXML ProgressBar progressBar;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runReportButton.disableProperty().bind(reportComboBox.valueProperty().isNull());
        
        //Setup ComboBox
        StringConverter<ReportModel> converter = new StringConverter<ReportModel>() {
            @Override
            public String toString(ReportModel object) {
                return object.getName();
            }

            @Override
            public ReportModel fromString(String string) {
                return null;
            }
        };
        reportComboBox.setConverter(converter);
    }    
    
    public void setActive(Stage stagePassed){
        stage = stagePassed;
        String title = "Select Report";
        stage.setTitle(title);
        loadComboBox();
    }
    
    private void loadComboBox() {
        for (ReportModel item : SQLReport.getActiveReports()){
            reportComboBox.getItems().addAll(item);
        }
    }
    
    @FXML private void handleCancelButton(){
        stage.close();
    }
    
    @FXML private void handleRunReportButton(){
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    setPanelDisabled(true);
                });
                runReport();
                setPanelDisabled(false);
            }
        }.start();
    }    
        
    @FXML private void comboBoxAction() {
        ReportModel selectedItem = (ReportModel) reportComboBox.getValue();
        if (selectedItem != null){
            descriptionTextArea.setText(selectedItem.getDescription() == null ? "" : selectedItem.getDescription().trim());
        }
    }
    
    private void runReport(){
        ReportModel selection = (ReportModel) reportComboBox.getValue(); 
        ReportModel reportSelected = SQLReport.getReportByID(selection.getId());
        
        SQLAudit.insertAudit("Ran Report ID: " + reportSelected.getId());
        
        //Generate Information
        HashMap hash = new HashMap();
        hash = ReportHashMap.generateDefaultInformation(hash);
        
        //Run Report
        GenerateReport.generateReport(reportSelected, hash);
    }    
    
    private void setPanelDisabled(boolean disabled) {
        progressBar.setVisible(disabled);
        reportComboBox.setDisable(disabled);        
        descriptionTextArea.setDisable(disabled);
        closeButton.setDisable(disabled);
    }
    
}
