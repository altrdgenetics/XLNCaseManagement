/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ActivityModel;
import com.xln.xlncasemanagement.model.sql.ActivityTypeModel;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.sql.SQLActivityFile;
import com.xln.xlncasemanagement.sql.SQLActivityType;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLUser;
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
import javafx.scene.control.CheckBox;
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
public class DetailedActivitySceneController implements Initializable {

    Stage stage;
    ActivityModel activityObject;
    File imageSelection;
    
    @FXML private Label headerLabel;
    @FXML private ComboBox activityTypeComboBox;
    @FXML private ComboBox userComboBox;
    @FXML private TextField rateTextField;
    @FXML private DatePicker occurredDatePicker;
    @FXML private TextField durationTextField;
    @FXML private CheckBox billableCheckBox;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button fileButton;
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
        StringConverter<ActivityTypeModel> converter2 = new StringConverter<ActivityTypeModel>() {
            @Override
            public String toString(ActivityTypeModel object) {
                return object.getActivityType();
            }

            @Override
            public ActivityTypeModel fromString(String string) {
                return null;
            }
        };
        activityTypeComboBox.setConverter(converter2);
    }
    
    private void setTextformatter() {
        rateTextField.setTextFormatter(new TextFormatter<>(NumberFormatService.moneyMaskFormatter()));
        durationTextField.setTextFormatter(new TextFormatter<>(NumberFormatService.durationMaskFormatter()));
    }
    
    public void setActive(Stage stagePassed, ActivityModel activityObjectPassed){
        stage = stagePassed;
        activityObject = activityObjectPassed;
        loadInformation();
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                (activityTypeComboBox.valueProperty().isNull())
                        .or(userComboBox.valueProperty().isNull())
                        .or(rateTextField.textProperty().isEmpty())
                        .or(occurredDatePicker.valueProperty().isNull())
                        .or(durationTextField.textProperty().isEmpty())
                        .or(userComboBox.disabledProperty())
        );
    }

    private void loadInformation(){
        stage.setTitle(activityObject == null ? "Add Activity" : "Edit Activity");
        headerLabel.setText(activityObject == null ? "Add Activity" : "Edit Activity");
        saveButton.setText(activityObject == null ? "Add" : "Save");
        loadUserComboBox();
        loadActivityTypeComboBox();
        if (activityObject != null){
            loadActivityInformation();
        } else {
            occurredDatePicker.setValue(LocalDate.now());
        }
    }
    
    private void loadUserComboBox() {
        userComboBox.getItems().removeAll(userComboBox.getItems());
        SQLUser.getActiveUsers().forEach(item -> userComboBox.getItems().addAll(item));
    }
    
    private void loadActivityTypeComboBox() {
        activityTypeComboBox.getItems().removeAll(activityTypeComboBox.getItems());
        SQLActivityType.getActiveActivityType().forEach(item -> activityTypeComboBox.getItems().addAll(item));
    }
    
    private void loadActivityInformation() {
        UserModel user = SQLUser.getUserByID(activityObject.getUserID());
        
        
        if (activityObject.getActivityTypeID() == 0){
            ActivityTypeModel sys = new ActivityTypeModel();
            sys.setId(0);
            sys.setActivityType("SYSTEM");
            
            activityTypeComboBox.getItems().addAll(sys);
            activityTypeComboBox.setValue(sys);
        } else {
            ActivityTypeModel activityType = SQLActivityType.geActivityTypeByID(activityObject.getActivityTypeID());
            activityTypeComboBox.setValue(activityType);
        }
        
        userComboBox.setValue(user);
        rateTextField.setText(NumberFormatService.formatMoney(activityObject.getRate()));
        occurredDatePicker.setValue(activityObject.getDateOccurred().toLocalDate());
        durationTextField.setText(String.valueOf(activityObject.getDuration()));
        billableCheckBox.setSelected(activityObject.isBillable());
        descriptionTextArea.setText(activityObject.getDescription() == null ? "" : activityObject.getDescription().trim());
        
        if (activityObject.isInvoiced()){
            setPanelInvoiced();
        }
        if (activityObject.getFileName() != null){
            fileButton.setText("Change File");
        } else {
            fileButton.setText("Add File");
        }
    }
    
    @FXML
    private void handleClose() {
        stage.close();
    }
        
    @FXML private void handleFileButtonAction(){
        imageSelection = fileChooser();
        
        if (imageSelection != null){
            fileButton.setText(imageSelection.getName());
        }
    }
    
    @FXML private void handleSelectedUserDefaultRate(){
        if (userComboBox.getValue() != null && rateTextField.getText().equals("")){
            UserModel user = (UserModel) userComboBox.getValue();
            BigDecimal defaultRate = user.getDefaultRate();
            BigDecimal previousMatterRate = SQLActivity.getLastRate(user.getId(), Global.getCurrentMatter().getId());
            
            if (previousMatterRate != BigDecimal.ZERO){
                rateTextField.setText(NumberFormatService.formatMoney(previousMatterRate));
            } else {
                rateTextField.setText(NumberFormatService.formatMoney(defaultRate));
            }
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
    
    @FXML
    private void saveButtonAction() {
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
                    update();
                    keyID = activityObject.getId();
                } else if ("Add".equals(saveButton.getText().trim())) {
                    keyID = insert();
                }

                SQLAudit.insertAudit(
                        ("Add".equals(saveButton.getText().trim()) ? "Added" : "Saved") 
                                + "Activity ID: " + keyID);
                
                if (imageSelection != null && keyID > 0) {
                    long lStartTime = System.currentTimeMillis();  
                    success = SQLActivityFile.insertActivityFile(keyID, imageSelection);
                    long lEndTime = System.currentTimeMillis();
                    DebugTools.HandleInfoPrintout("Saved File In: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }

                if (success) {
                    Platform.runLater(() -> {
                        stage.close();
                    });
                } else {
                    activityObject = SQLActivity.getActivityByID(keyID);
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
    
    private int insert() {
        ActivityModel item = new ActivityModel();
        ActivityTypeModel activityType = (ActivityTypeModel) activityTypeComboBox.getValue();
        UserModel user = (UserModel) userComboBox.getValue();
        
        item.setActive(true);
        item.setUserID(user.getId());
        item.setActivityTypeID(activityType.getId());
        item.setMatterID(Global.getCurrentMatter().getId());
        item.setDateOccurred(occurredDatePicker.getValue() == null ? null : java.sql.Date.valueOf(occurredDatePicker.getValue()));
        item.setDuration(durationTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(durationTextField.getText().trim()));
        item.setRate(rateTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(rateTextField.getText().trim()));
        item.setTotal(item.getDuration().multiply(item.getRate()));
        item.setDescription(descriptionTextArea.getText().trim().equals("") ? null : descriptionTextArea.getText().trim());        
        item.setBillable(billableCheckBox.isSelected());
        item.setInvoiced(false);
        
        return SQLActivity.insertActivity(item);
    }
    
    private void update() {
        ActivityModel item = new ActivityModel();
        ActivityTypeModel activityType = (ActivityTypeModel) activityTypeComboBox.getValue();
        UserModel user = (UserModel) userComboBox.getValue();
        
        item.setUserID(user.getId());
        item.setActivityTypeID(activityType.getId());
        item.setDateOccurred(occurredDatePicker.getValue() == null ? null : java.sql.Date.valueOf(occurredDatePicker.getValue()));
        item.setDuration(durationTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(durationTextField.getText().trim()));
        item.setRate(rateTextField.getText().trim().equals("") ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(rateTextField.getText().trim()));
        item.setTotal(item.getDuration().multiply(item.getRate()));
        item.setDescription(descriptionTextArea.getText().trim().equals("") ? null : descriptionTextArea.getText().trim());        
        item.setBillable(billableCheckBox.isSelected());
        item.setId(activityObject.getId());
        
        SQLActivity.updateActivityByID(item);
    }
    
    private void setPanelInvoiced() {
        occurredDatePicker.setEditable(false);
        occurredDatePicker.setOnMouseClicked(e -> {
                occurredDatePicker.hide();
        });
        
        
        activityTypeComboBox.setEditable(false);
        activityTypeComboBox.setOnMouseClicked(e -> {
                activityTypeComboBox.hide();
        });
        
        userComboBox.setEditable(false);
        userComboBox.setOnMouseClicked(e -> {
                userComboBox.hide();
        });
        
        rateTextField.setEditable(false);
        durationTextField.setEditable(false);
        billableCheckBox.setDisable(true);
        descriptionTextArea.setEditable(false);
        
        fileButton.setDisable(true);
        saveButton.setVisible(false);
    }
    
    private void setPanelDisabled(boolean disabled) {
        activityTypeComboBox.setDisable(disabled);
        userComboBox.setDisable(disabled);
        rateTextField.setDisable(disabled);
        occurredDatePicker.setDisable(disabled);
        durationTextField.setDisable(disabled);
        billableCheckBox.setDisable(disabled);
        descriptionTextArea.setDisable(disabled);
        fileButton.setDisable(disabled);
        closeButton.setDisable(disabled);
    }
    
}
