package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MakeModel;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.MatterTypeModel;
import com.xln.xlncasemanagement.model.sql.ModelModel;
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class InformationSceneController implements Initializable {

    boolean updateMode = false;
    int makeID;
    int modelID;
    
    //LEFT SIDE OF PANEL
    @FXML Label matterTypeLabel;
    @FXML Button matterTypeButton;
    @FXML DatePicker OpenDateDatePicker;
    @FXML DatePicker ClosedDateDatePicker;
    @FXML Label Label1;
    @FXML DatePicker WarrantyDateDatePicker;
    @FXML Label Label2;
    @FXML TextField Label2TextField;
    @FXML Label Label3;
    @FXML Button Label3Button;
    @FXML Label Label4;
    @FXML Button Label4Button;
    @FXML Label Label5;
    @FXML TextField Label5TextField;
        
    //RIGHT SIDE OF PANEL
    @FXML TextField TotalHoursTextField;
    @FXML TextField BilledHoursTextField;
    @FXML TextField UnBilledHoursTextField;
    @FXML TextField TotalExpensesTextField;
    @FXML TextField BilledExpensesTextField;
    @FXML TextField UnBilledExpensesTextField;
    @FXML TextField TotalCostTextField;
    @FXML TextField BalanceTextField;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setVersionInformation();
        setSelectorsHiddenOnNonEdit();   
    }

    private void setSelectorsHiddenOnNonEdit(){
        OpenDateDatePicker.setOnMouseClicked(e -> {
            if (!OpenDateDatePicker.isEditable() && OpenDateDatePicker.isShowing()) {
                OpenDateDatePicker.hide();
            }
        });
        ClosedDateDatePicker.setOnMouseClicked(e -> {
            if (!ClosedDateDatePicker.isEditable() && ClosedDateDatePicker.isShowing()) {
                ClosedDateDatePicker.hide();
            }
        });
        WarrantyDateDatePicker.setOnMouseClicked(e -> {
            if (!WarrantyDateDatePicker.isEditable() && WarrantyDateDatePicker.isShowing()) {
                WarrantyDateDatePicker.hide();
            }
        });
        
        Label4Button.disableProperty().bind(
                Label3Button.textProperty().isEqualTo("No " + Global.getHeaderLabel2().replace(":", ""))
                .or(Label3Button.disabledProperty())
        );
    }
       
    @FXML
    private void onMatterTypeButtonAction() {
        if (updateMode) {
            DebugTools.Printout("Searching Matter Type");
            MatterTypeModel matterType = Global.getStageLauncher().MaintenanceMatterTypeScene(Global.getMainStage(), false);

            if (matterType != null) {
                SQLMatter.updateMatterTypeByID(matterType.getId(), Global.getCurrentMatter().getId());
                matterTypeButton.setText(matterType.getMatterType());
                
                
                DebugTools.Printout("Need To Update Matter Type In Header");
            }
        } else {
            DebugTools.Printout("Searching Disabled");
        }
    }
    
    @FXML
    private void onLabel3ButtonAction() {
        if (updateMode) {
            DebugTools.Printout("Searching Make");
            MakeModel make = Global.getStageLauncher().MaintenanceMakeScene(Global.getMainStage(), false);

            if (make != null) {
                if (make.getId() == 0 || make.getId() != makeID){
                    modelID = 0;
                    Global.getCurrentMatter().setModel(modelID);
                    Label4Button.setText("No " + Global.getHeaderLabel3().replace(":", ""));
                }
                
                makeID = make.getId();
                Global.getCurrentMatter().setMake(makeID);
                Label3Button.setText(make.getId() == 0 ? "No " + Global.getHeaderLabel2().replace(":", "") : make.getName());
                
                if (make.getId() == 0){
                    modelID = 0;
                    Global.getCurrentMatter().setModel(modelID);
                    Label4Button.setText("No " + Global.getHeaderLabel3().replace(":", ""));
                }
            }
            
        } else {
            DebugTools.Printout("Searching Disabled");
        }
    }
    
    @FXML private void onLabel4ButtonAction() {
        if (updateMode){
            DebugTools.Printout("Searching Model");
            ModelModel model = Global.getStageLauncher().MaintenanceModelScene(Global.getMainStage(), false, makeID);

            if (model != null) {
                modelID = model.getId();
                Global.getCurrentMatter().setModel(modelID);
                Label4Button.setText(model.getId() == 0 ? "No " + Global.getHeaderLabel3().replace(":", "") : model.getName());
            }
            
        } else {
            DebugTools.Printout("Searching Disabled");
        }
    }
    
    public void setActive() {
        loadInformation();
    }
        
    private void setVersionInformation(){
        matterTypeLabel.setText(Global.getNewCaseType() + " Type:");
        Label1.setText(Global.getInformationLabel1());
        Label2.setText(Global.getInformationLabel2());
        Label3.setText(Global.getInformationLabel3());
        Label4.setText(Global.getInformationLabel4());
        Label5.setText(Global.getInformationLabel5());
    }
    
    public void mainPanelButtonFourAction() {
        updateMode = !updateMode;
        setEditableStatus(updateMode);
        
        if (updateMode) {
            loadInformation();
            OpenDateDatePicker.requestFocus();
        } else {
            saveInformation();
            
            MatterModel matterID = SQLMatter.getMatterByID(Global.getCurrentMatter().getId());
            Global.getMainStageController().loadMatterComboBox();
            
            Global.getMainStageController().getHeaderField1().getSelectionModel().select(matterID);
            
            loadInformation(); //Reload After Save
            DebugTools.Printout("Saved Information");
        }
    }
        
    public void mainPanelButtonDeleteAction() {
        updateMode = !updateMode;
        DebugTools.Printout("Reverted Information (Cancel Button Action)");
        setEditableStatus(false);
    }
    
    private void setEditableStatus(boolean editable){
        matterTypeButton.setDisable(!editable);
        OpenDateDatePicker.setEditable(editable);
        ClosedDateDatePicker.setEditable(editable);
        WarrantyDateDatePicker.setEditable(editable);
        Label2TextField.setEditable(editable);
        Label3Button.setDisable(!editable);
        //Label4Button.setDisable(!editable); //bound already
        Label5TextField.setEditable(editable);
    }

    public boolean isUpdateMode() {
        return updateMode;
    }
    
    private void loadInformation(){
        if (Global.getCurrentMatter() != null){
            MatterModel model = SQLMatter.getMatterByID(Global.getCurrentMatter().getId());
            Global.setCurrentMatter(model);
            setInformation();
            Global.getMainStageController().loadHeader();
        } else {
            clearWindow();
        }
    }

    private void setInformation() {         
        //Master IDs
        makeID = Global.getCurrentMatter().getMake();
        modelID = Global.getCurrentMatter().getModel();
        
        //LEFT SIDE OF PANEL
        matterTypeButton.setText(Global.getCurrentMatter().getMatterTypeName()== null 
                ? "No " + Global.getNewCaseType().replace(":", "") + " Type" : Global.getCurrentMatter().getMatterTypeName());
        OpenDateDatePicker.setValue(Global.getCurrentMatter().getOpenDate() == null
                ? null : Global.getCurrentMatter().getOpenDate().toLocalDate());
        ClosedDateDatePicker.setValue(Global.getCurrentMatter().getCloseDate() == null
                ? null : Global.getCurrentMatter().getCloseDate().toLocalDate());
        WarrantyDateDatePicker.setValue(Global.getCurrentMatter().getWarranty() == null
                ? null : Global.getCurrentMatter().getWarranty().toLocalDate());
        Label2TextField.setText(Global.getCurrentMatter().getBudget() == null 
                ? "" : NumberFormatService.formatMoney(Global.getCurrentMatter().getBudget()));
        Label3Button.setText(Global.getCurrentMatter().getMakeName() == null 
                ? "No " + Global.getHeaderLabel2().replace(":", "") : Global.getCurrentMatter().getMakeName());
        Label4Button.setText(Global.getCurrentMatter().getModelName() == null
        ? "No " + Global.getHeaderLabel3().replace(":", "") : Global.getCurrentMatter().getModelName());
        Label5TextField.setText(Global.getCurrentMatter().getSerial() == null
                ? "" : Global.getCurrentMatter().getSerial());

        //RIGHT SIDE OF PANEL
        HashMap billables = SQLMatter.getSummaryByMatterID(Global.getCurrentMatter().getId());
        BigDecimal budget = Global.getCurrentMatter().getBudget() == null 
                        ? BigDecimal.ZERO : Global.getCurrentMatter().getBudget();
        BigDecimal total = NumberFormatService.convertToBigDecimal(billables.get("totalTotalAmount").toString());
        BigDecimal balance = budget.subtract(total);

        Platform.runLater(() -> {
            if (balance.signum() == -1) {
                BalanceTextField.setStyle("-fx-text-fill: #d9534f; -fx-background-color: #f2dede;"); //Red
            } else if (balance.compareTo(BigDecimal.ZERO) == 0) {
                BalanceTextField.setStyle("-fx-text-fill: #333333; -fx-background-color: white;"); //Black
            } else {
                BalanceTextField.setStyle("-fx-text-fill: #3c763d; -fx-background-color: #dff0d8;"); //Green
            }
        });
 
        TotalHoursTextField.setText(billables.get("totalActivityHour").toString() + "   (" + billables.get("totalActivityAmount").toString() + ")");
        BilledHoursTextField.setText(billables.get("billedActivityHour").toString() + "   (" + billables.get("billedActivityAmount").toString() + ")");
        UnBilledHoursTextField.setText(billables.get("unBilledActivityHour").toString() + "   (" + billables.get("unbilledActivityAmount").toString() + ")");
        TotalExpensesTextField.setText(billables.get("totalExpenseAmount").toString());
        BilledExpensesTextField.setText(billables.get("billedExpenseAmount").toString());
        UnBilledExpensesTextField.setText(billables.get("unBilledExpenseAmount").toString());
        TotalCostTextField.setText(billables.get("totalTotalAmount").toString());
        BalanceTextField.setText(NumberFormatService.formatMoney(balance));
    }

    public void clearWindow(){
        //LEFT SIDE OF PANEL
        matterTypeButton.setText("No " + Global.getNewCaseType().replace(":", "") + " Type");
        OpenDateDatePicker.setValue(null);
        ClosedDateDatePicker.setValue(null);
        WarrantyDateDatePicker.setValue(null);
        Label2TextField.setText("");
        Label3Button.setText("No " + Global.getHeaderLabel2().replace(":", ""));
        Label4Button.setText("No " + Global.getHeaderLabel3().replace(":", ""));
        Label5TextField.setText("");

        //RIGHT SIDE OF PANEL
        TotalHoursTextField.setText("");
        BilledHoursTextField.setText("");
        UnBilledHoursTextField.setText("");
        TotalExpensesTextField.setText("");
        BilledExpensesTextField.setText("");
        UnBilledExpensesTextField.setText("");
        TotalCostTextField.setText("");
        BalanceTextField.setText("");
    }
    
    private void saveInformation(){                
        //Update Matter
        Global.getCurrentMatter().setOpenDate(OpenDateDatePicker.getValue() == null 
                ? null : java.sql.Date.valueOf(OpenDateDatePicker.getValue()));
        Global.getCurrentMatter().setCloseDate(ClosedDateDatePicker.getValue() == null 
                ? null : java.sql.Date.valueOf(ClosedDateDatePicker.getValue()));
        Global.getCurrentMatter().setWarranty(WarrantyDateDatePicker.getValue() == null 
                ? null : java.sql.Date.valueOf(WarrantyDateDatePicker.getValue()));
        Global.getCurrentMatter().setBudget(Label2TextField.getText().trim().equals("")
                ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(Label2TextField.getText().trim()));
        Global.getCurrentMatter().setMake(makeID);
        Global.getCurrentMatter().setModel(modelID);
        Global.getCurrentMatter().setSerial(Label5TextField.getText().trim().equals("") 
                ? null : Label5TextField.getText().trim());
                
        SQLMatter.updateMatterInformationByID(Global.getCurrentMatter());  
    }
    
}
