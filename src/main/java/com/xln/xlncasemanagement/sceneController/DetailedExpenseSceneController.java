/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ExpenseModel;
import com.xln.xlncasemanagement.model.sql.ExpenseTypeModel;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sql.SQLExpense;
import com.xln.xlncasemanagement.sql.SQLExpenseType;
import com.xln.xlncasemanagement.sql.SQLUser;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DetailedExpenseSceneController implements Initializable {

    Stage stage;
    ExpenseModel expenseObject;
    File imageSelection;
    
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private DatePicker expenseDateDatePicker;
    @FXML private ComboBox expenseTypeComboBox;
    @FXML private ComboBox userComboBox;
    @FXML private TextField costTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button receiptButton;
    @FXML private ProgressBar progressBar;
        
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressBar.setVisible(false);
        setListeners();
        setTextformatter();
        setComboBoxModel();        
    }    
    
    private void setComboBoxModel(){
        //Setup User ComboBox
        StringConverter<UserModel> converter = new StringConverter<UserModel>() {
            @Override
            public String toString(UserModel object) {
                return object.getFirstName() + " " + object.getLastName();
            }

            @Override
            public UserModel fromString(String string) {
                return null;
            }
        };
        userComboBox.setConverter(converter);
        
        //Setup Expense Type ComboBox
        StringConverter<ExpenseTypeModel> converter2 = new StringConverter<ExpenseTypeModel>() {
            @Override
            public String toString(ExpenseTypeModel object) {
                return object.getExpenseType();
            }

            @Override
            public ExpenseTypeModel fromString(String string) {
                return null;
            }
        };
        expenseTypeComboBox.setConverter(converter2);
    }
    
    private void setTextformatter() {
        costTextField.setTextFormatter(new TextFormatter<>(NumberFormatService.moneyMaskFormatter()));
    }

    public void setActive(Stage stagePassed, ExpenseModel expenseObjectObjectPassed){
        stage = stagePassed;
        expenseObject = expenseObjectObjectPassed;
        stage.setTitle(expenseObject == null ? "Add Expense" : "Edit Expense");
        headerLabel.setText(expenseObject == null ? "Add Expense" : "Edit Expense");
        saveButton.setText(expenseObject == null ? "Add" : "Save");
        loadInformation();
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                (userComboBox.valueProperty().isNull())
                        .or(expenseDateDatePicker.valueProperty().isNull())
                        .or(expenseTypeComboBox.valueProperty().isNull())
                        .or(costTextField.textProperty().isEmpty())
        );
    }

    private void loadInformation(){
        loadUserComboBox();
        loadExpenseTypeComboBox();
        if (expenseObject != null){
            loadExpenseInformation();
        }
    }
    
    private void loadUserComboBox() {
        userComboBox.getItems().removeAll(userComboBox.getItems());
        for (UserModel item : SQLUser.getActiveUsers()){
            userComboBox.getItems().addAll(item);
        }
    }
    
    private void loadExpenseTypeComboBox() {
        expenseTypeComboBox.getItems().removeAll(expenseTypeComboBox.getItems());
        for (ExpenseTypeModel item : SQLExpenseType.getActiveExpenseType()){
            expenseTypeComboBox.getItems().addAll(item);
        }
    }
    
    private void loadExpenseInformation() {
        UserModel user = SQLUser.getUserByID(expenseObject.getUserID());
        ExpenseTypeModel expenseType = SQLExpenseType.getExpenseTypebyID(expenseObject.getExpenseType());
        
        expenseDateDatePicker.setValue(expenseObject.getDateOccurred().toLocalDate());
        userComboBox.setValue(user);
        expenseTypeComboBox.setValue(expenseType);
        costTextField.setText(NumberFormatService.formatMoney(expenseObject.getCost()));
        descriptionTextArea.setText(expenseObject.getDescription() == null ? "" : expenseObject.getDescription().trim());
        
        if (expenseObject.isInvoiced()){
            setPanelDisabled();
        }
        if (expenseObject.getFileName() != null){
            receiptButton.setText("Change Receipt");
        }
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        progressBar.setVisible(true);
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        int keyID = -1;
        
        if ("Save".equals(saveButton.getText().trim())){
            update();
            keyID = expenseObject.getId();
        } else if ("Add".equals(saveButton.getText().trim())) {
            keyID = insert();
        }
        
        if (imageSelection != null && keyID > 0){
            updateFile(keyID, imageSelection);
        }
        
        stage.close();
    }
    
    private int insert() {
        ExpenseModel item = new ExpenseModel();
        ExpenseTypeModel expenseType = (ExpenseTypeModel) expenseTypeComboBox.getValue();
        UserModel user = (UserModel) userComboBox.getValue();
        
        item.setActive(true);
        item.setUserID(user.getId());
        item.setExpenseType(expenseType.getId());
        item.setMatterID(Global.getCurrentMatter().getId());
        item.setDateOccurred(expenseDateDatePicker.getValue() == null ? null : java.sql.Date.valueOf(expenseDateDatePicker.getValue()));
        item.setDescription(descriptionTextArea.getText().trim().equals("") ? null : descriptionTextArea.getText().trim());        
        item.setCost(costTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(costTextField.getText().trim()));
        item.setInvoiced(false);
        
        return SQLExpense.insertExpense(item);
    }
    
    private void update() {
        ExpenseModel item = new ExpenseModel();
        ExpenseTypeModel expenseType = (ExpenseTypeModel) expenseTypeComboBox.getValue();
        UserModel user = (UserModel) userComboBox.getValue();
        
        item.setId(expenseObject.getId());
        item.setUserID(user.getId());
        item.setExpenseType(expenseType.getId());
        item.setDateOccurred(expenseDateDatePicker.getValue() == null ? null : java.sql.Date.valueOf(expenseDateDatePicker.getValue()));
        item.setDescription(descriptionTextArea.getText().trim().equals("") ? null : descriptionTextArea.getText().trim());        
        item.setCost(costTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(costTextField.getText().trim()));
        
        SQLExpense.updateExpenseByID(item);
    }
    
    @FXML private void handleFileButtonAction(){
        imageSelection = fileChooser();
        
        if (imageSelection != null){
            receiptButton.setText(imageSelection.getName());
        }
    }
    
    private File fileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
         return fileChooser.showOpenDialog(stage);
    }
    
    private void updateFile(int id, File image) {
        if (SQLExpense.insertExpenseFile(id, image)){
            // success
        } else {
            AlertDialog.StaticAlert(4, "Save Error",
                    "Unable To Insert File",
                    "The file was not able to be saved to the database.");
        }
    }
        
    private void setPanelDisabled() {
        expenseDateDatePicker.setEditable(false);
        expenseDateDatePicker.setOnMouseClicked(e -> {
                expenseDateDatePicker.hide();
        });
        
        
        expenseTypeComboBox.setEditable(false);
        expenseTypeComboBox.setOnMouseClicked(e -> {
                expenseTypeComboBox.hide();
        });
        
        userComboBox.setEditable(false);
        userComboBox.setOnMouseClicked(e -> {
                userComboBox.hide();
        });
        
        costTextField.setEditable(false);
        descriptionTextArea.setEditable(false);
        
        receiptButton.setDisable(true);
        saveButton.setVisible(false);
    }
}
