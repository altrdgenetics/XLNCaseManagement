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
import com.xln.xlncasemanagement.sql.SQLReportParameter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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

    static Stage stage;
    
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
        runReportButton.disableProperty().bind(
                (reportComboBox.valueProperty().isNull())
                .or(reportComboBox.disabledProperty())
        );
        
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
    
    @FXML private void handleRunReportButton() {
        Platform.runLater(() -> {
            setPanelDisabled(true);
        });
        ReportModel selection = (ReportModel) reportComboBox.getValue(); 
        final ReportModel reportSelected = SQLReport.getReportByID(selection.getId());
        
        
        
        //Generate Information
        HashMap hash = new HashMap();
        hash = ReportHashMap.generateDefaultInformation(hash);
        
        //Generate Paramter Information
        List<String> parametersList = SQLReportParameter.getParameterListByReport(reportSelected.getId());
        
        for (String param : parametersList){
            hash = parameterSelection(hash, param);
            
            //Check if cancelled out of dialog
            if (hash == null){
                break;
            }
        }
        
        final HashMap completeHash = hash;
        
        new Thread() {
            @Override
            public void run() {
                if (completeHash != null) {
                    SQLAudit.insertAudit("Ran Report ID: " + reportSelected.getId());

                    //Run Report
                    GenerateReport.generateReport(reportSelected, completeHash);
                }
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
    
    public static HashMap parameterSelection(HashMap hash, String parameterRequested){
        
        if (parameterRequested.equals("Start / End Dates")){
            return Global.getStageLauncher().ReportParamTwoDatesScene(stage, hash);
        } else if (parameterRequested.equals("Client")){
            return Global.getStageLauncher().ReportParamClientScene(stage, hash);
        } else if (parameterRequested.equals(Global.getNewCaseType())) {
            return Global.getStageLauncher().ReportParamMatterScene(stage, hash);
        } else {
            return hash;
        }
    }
        
    private void setPanelDisabled(boolean disabled) {
        progressBar.setVisible(disabled);
        reportComboBox.setDisable(disabled);        
        descriptionTextArea.setDisable(disabled);
        closeButton.setDisable(disabled);
    }
    
}
