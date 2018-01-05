/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ReportParameterModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLReportParameter;
import com.xln.xlncasemanagement.util.AlertDialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceReportParametersSceneController implements Initializable {

    Stage stage;
    int reportID;

    @FXML
    Label headerLabel;
    @FXML
    Label comboBoxLabel;
    @FXML
    ComboBox reportComboBox;
    @FXML
    Button addButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButton.disableProperty().bind(
                reportComboBox.valueProperty().isNull()
        );
    }

    public void setActive(Stage stagePassed, int reportIDPassed) {
        stage = stagePassed;
        reportID = reportIDPassed;
        String title = "Select Report";
        stage.setTitle(title);
        loadComboBox();
    }

    private void loadComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Start / End Dates",
                "Client",
                Global.getNewCaseType()
        );
        
        //Set Items In Combobox
        reportComboBox.getItems().addAll(options);
    }

    @FXML
    private void handleCancelButton() {
        stage.close();
    }

    @FXML
    private void handleAddButton() {
        boolean exists = SQLReportParameter.checkExistingParameter(reportID, reportComboBox.getValue().toString());

        if (exists) {
            AlertDialog.StaticAlert(4, "Save Error", "Parameter Exists",
                    "The selected parameter already exists for this report. "
                            + "Please select a different parameter.");
        } else {
            saveParameter();
            stage.close();
        }
    }

    private void saveParameter() {
        ReportParameterModel item = new ReportParameterModel();
        item.setReportID(reportID);
        item.setReportParameter(reportComboBox.getValue().toString());
        SQLReportParameter.insertReportParameter(item);
        
        SQLAudit.insertAudit("Added Report Parameter: " 
                + reportComboBox.getValue().toString() + " To Report ID: " + reportID);
    }

}
