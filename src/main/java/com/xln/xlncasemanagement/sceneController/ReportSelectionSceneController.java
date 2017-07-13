/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.ReportModel;
import com.xln.xlncasemanagement.sql.SQLReport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
        runReport();
    }    
        
    private void runReport(){
        ReportModel relation = (ReportModel) reportComboBox.getValue(); 
        
        //TODO run report
    }    
    
}
