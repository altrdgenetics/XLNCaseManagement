/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.model.sql.ExpenseTypeModel;
import com.xln.xlncasemanagement.sql.SQLExpenseType;
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
public class MaintenanceExpenseTypeAddEditSceneController implements Initializable {

    Stage stage;
    ExpenseTypeModel expenseTypeObject;
    
    @FXML
    private Label headerLabel;
    @FXML
    private TextField expenseTypetextField;
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
    
    public void setActive(Stage stagePassed, ExpenseTypeModel expenseTypeObjectPassed){
        stage = stagePassed;
        expenseTypeObject = expenseTypeObjectPassed;
        String title = "Add Expense Type";
        String buttonText = "Add";
        
        if (expenseTypeObject != null){
            title = "Edit Expense Type";
            buttonText = "Save";
            loadInformation();
        }
        stage.setTitle(title);
        headerLabel.setText(title);
        saveButton.setText(buttonText);
        
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                expenseTypetextField.textProperty().isEmpty()
        );
    }
    
    private void loadInformation(){
        expenseTypetextField.setText(expenseTypeObject.getExpenseType());
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
        expenseTypeObject = new ExpenseTypeModel();
        expenseTypeObject.setActive(true);
        expenseTypeObject.setExpenseType(expenseTypetextField.getText().trim());
        int id = SQLExpenseType.insertExpenseType(expenseTypeObject);
        System.out.println("New Expense Type ID: " + id);
    }
    
    private void updateCompany() {
        expenseTypeObject.setExpenseType(expenseTypetextField.getText().trim());
        SQLExpenseType.updateExpenseTypeByID(expenseTypeObject);
    }
}
