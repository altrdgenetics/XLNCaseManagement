/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.sql.SQLActivityFile;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PaymentSceneController implements Initializable {

    Stage stage;
    File imageSelection;
    
    @FXML private Label headerLabel;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private DatePicker expenseDateDatePicker;
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
        setListeners();
        setTextformatter();
    }    
    
    private void setTextformatter() {
        costTextField.setTextFormatter(new TextFormatter<>(NumberFormatService.moneyMaskFormatter()));
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                (expenseDateDatePicker.valueProperty().isNull())
                        .or(costTextField.textProperty().isEmpty())
        );
    }

    public void setActive(Stage stagePassed){
        stage = stagePassed;
        expenseDateDatePicker.setValue(LocalDate.now());
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML
    private void saveButtonAction() {
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    setPanelDisabled(true);
                    progressBar.setVisible(true);
                });

                int activityKeyID = -1;
                boolean success = true;
                
                insertPayment();
                
                activityKeyID = insertActivity();

                SQLAudit.insertAudit("Added Activity ID: " + activityKeyID);
                
                if (imageSelection != null && activityKeyID > 0) {
                    long lStartTime = System.currentTimeMillis();  
                    success = SQLActivityFile.insertActivityFile(activityKeyID, imageSelection);
                    long lEndTime = System.currentTimeMillis();
                    DebugTools.HandleInfoPrintout("Saved File In: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }

                if (success) {
                    Platform.runLater(() -> {
                        stage.close();
                    });
                } else {
                    Platform.runLater(() -> {
                        AlertDialog.StaticAlert(4, "Save Error",
                                "Unable To Insert File",
                                "The file was not able to be saved to the database. "
                                        + "The rest of the information was saved properly.");
                        stage.close();
                    });
                }
            }
        }.start();
    }
    
    private void insertPayment(){
        BigDecimal newTotal = 
                NumberFormatService.convertToBigDecimal(costTextField.getText()).add(
                        (Global.getCurrentMatter().getBudget() == null ? BigDecimal.ZERO : Global.getCurrentMatter().getBudget())
        );
        
        Global.getCurrentMatter().setBudget(newTotal);
        SQLMatter.updateMatterPaymentByID(Global.getCurrentMatter());
    }
    
    private int insertActivity() {
        ActivityModel item = new ActivityModel();
        
        String description = "Received Payment of " 
                + NumberFormatService.formatMoney(NumberFormatService.convertToBigDecimal(costTextField.getText()))
                + (descriptionTextArea.getText().trim().equals("") ? "" : " - " + descriptionTextArea.getText().trim());
        
        item.setActive(true);
        item.setUserID(Global.getCurrentUser().getId());
        item.setActivityTypeID(0);
        item.setMatterID(Global.getCurrentMatter().getId());
        item.setDateOccurred(expenseDateDatePicker.getValue() == null ? null : java.sql.Date.valueOf(expenseDateDatePicker.getValue()));
        item.setDuration(BigDecimal.ZERO);
        item.setRate(BigDecimal.ZERO);
        item.setTotal(item.getDuration().multiply(item.getRate()));
        item.setDescription(description);        
        item.setBillable(false);
        item.setInvoiced(true);
        
        return SQLActivity.insertActivity(item);
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
    
    private void setPanelDisabled(boolean disabled) {
        closeButton.setDisable(disabled);
        expenseDateDatePicker.setDisable(disabled);
        costTextField.setDisable(disabled);
        descriptionTextArea.setDisable(disabled);
        receiptButton.setDisable(disabled);
    }
    
}
