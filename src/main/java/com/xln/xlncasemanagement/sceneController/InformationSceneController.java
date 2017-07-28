package com.xln.xlncasemanagement.sceneController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MakeModel;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.ModelModel;
import com.xln.xlncasemanagement.sql.SQLActivity;
import com.xln.xlncasemanagement.sql.SQLExpense;
import com.xln.xlncasemanagement.sql.SQLMake;
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.sql.SQLModel;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class InformationSceneController implements Initializable {

    boolean updateMode = false;
    
    //LEFT SIDE OF PANEL
    @FXML DatePicker OpenDateDatePicker;
    @FXML DatePicker ClosedDateDatePicker;
    @FXML Label Label1;
    @FXML DatePicker WarrantyDateDatePicker;
    @FXML Label Label2;
    @FXML TextField Label2TextField;
    @FXML Label Label3;
    @FXML ComboBox<MakeModel> Label3ComboBox;
    @FXML Label Label4;
    @FXML ComboBox<ModelModel> Label4ComboBox;
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
        setComboBoxModel(); 
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
        Label3ComboBox.setOnMouseClicked(e -> {
            if (!Label3ComboBox.isEditable() && Label3ComboBox.isShowing()) {
                Label3ComboBox.hide();
            }
        });
        Label4ComboBox.setOnMouseClicked(e -> {
            if (!Label4ComboBox.isEditable() && Label4ComboBox.isShowing()) {
                Label4ComboBox.hide();
            }
        });
    }
    
    private void setComboBoxModel(){
        //Setup Make ComboBox
        StringConverter<MakeModel> con1 = new StringConverter<MakeModel>() {
            @Override
            public String toString(MakeModel object) {
                    return object.getName();
                
            }

            @Override
            public MakeModel fromString(String string) {
                return null;
            }
        };
        Label3ComboBox.setConverter(con1);
        
        //Setup Model ComboBox
        StringConverter<ModelModel> con2 = new StringConverter<ModelModel>() {
            @Override
            public String toString(ModelModel object) {
                    return object.getName();
                
            }

            @Override
            public ModelModel fromString(String string) {
                return null;
            }
        };
        Label4ComboBox.setConverter(con2);
    }
    
    public void setActive() {
        setVersionInformation();
        setComboBoxModel();
        loadComboBoxes();
        loadInformation();
    }
        
    private void setVersionInformation(){
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
            DebugTools.Printout("Saved Information");
        }
    }
        
    public void mainPanelButtonDeleteAction() {
        updateMode = !updateMode;
        DebugTools.Printout("Reverted Information (Cancel Button Action)");
        setEditableStatus(false);
    }
    
    private void setEditableStatus(boolean editable){
        OpenDateDatePicker.setEditable(editable);
        ClosedDateDatePicker.setEditable(editable);
        WarrantyDateDatePicker.setEditable(editable);
        Label2TextField.setEditable(editable);
//        Label3ComboBox.setEditable(editable);
//        Label4ComboBox.setEditable(editable);
        Label5TextField.setEditable(editable);
    }

    public boolean isUpdateMode() {
        return updateMode;
    }

    private void loadComboBoxes(){
        loadMakeComboBox();
        loadModelComboBox();
    }
    
    private void loadMakeComboBox(){
        Label3ComboBox.getItems().removeAll(Label3ComboBox.getItems());
        Label3ComboBox.getItems().addAll(new MakeModel());
        SQLMake.getActiveMake().forEach(item -> Label3ComboBox.getItems().addAll(item));
    }
    
    private void loadModelComboBox(){
        Label4ComboBox.getItems().removeAll(Label4ComboBox.getItems());
        Label4ComboBox.getItems().addAll(new ModelModel());        
        SQLModel.getActiveModel().forEach(item -> Label4ComboBox.getItems().addAll(item));
    }
    
    private void loadInformation(){
        if (Global.getCurrentMatter() != null){
            MatterModel model = SQLMatter.getMatterByID(Global.getCurrentMatter().getId());
            Global.setCurrentMatter(model);
            setInformation();
        } else {
            clearWindow();
        }
    }

    private void setInformation() {
        //HEADER PANEL
        Global.getMainStageController().getHeaderField2().setText(Global.getCurrentMatter().getMakeName() == null
                ? "" : Global.getCurrentMatter().getMakeName());
        Global.getMainStageController().getHeaderField3().setText(Global.getCurrentMatter().getModelName() == null
                ? "" : Global.getCurrentMatter().getModelName());
        Global.getMainStageController().getHeaderField4().setText(Global.getCurrentMatter().getSerial() == null
                ? "" : Global.getCurrentMatter().getSerial());
        Global.getMainStageController().getHeaderField5().setText("");
        
        //LEFT SIDE OF PANEL
        OpenDateDatePicker.setValue(Global.getCurrentMatter().getOpenDate() == null
                ? null : Global.getCurrentMatter().getOpenDate().toLocalDate());
        ClosedDateDatePicker.setValue(Global.getCurrentMatter().getCloseDate() == null
                ? null : Global.getCurrentMatter().getCloseDate().toLocalDate());
        WarrantyDateDatePicker.setValue(Global.getCurrentMatter().getWarranty() == null
                ? null : Global.getCurrentMatter().getWarranty().toLocalDate());
        Label2TextField.setText(Global.getCurrentMatter().getBudget() == null 
                ? "" : NumberFormatService.formatMoney(Global.getCurrentMatter().getBudget()));
        Label3ComboBox.setValue(SQLMake.getMakeByID(Global.getCurrentMatter().getMake()));
        Label4ComboBox.setValue(SQLModel.geModelByID(Global.getCurrentMatter().getModel()));
        Label5TextField.setText(Global.getCurrentMatter().getSerial() == null
                ? "" : Global.getCurrentMatter().getSerial());

        //RIGHT SIDE OF PANEL
        HashMap billables = SQLMatter.getSummaryByMatterID(Global.getCurrentMatter().getId());
        BigDecimal budget = Global.getCurrentMatter().getBudget() == null 
                        ? BigDecimal.ZERO : Global.getCurrentMatter().getBudget();
        BigDecimal total = NumberFormatService.convertToBigDecimal(billables.get("totalBilledAmount").toString());
        
        TotalHoursTextField.setText(billables.get("totalActivityHour").toString());
        BilledHoursTextField.setText(billables.get("billedActivityHour").toString());
        UnBilledHoursTextField.setText(billables.get("unBilledActivityHour").toString());
        TotalExpensesTextField.setText(billables.get("totalExpenseAmount").toString());
        BilledExpensesTextField.setText(billables.get("billedExpenseAmount").toString());
        UnBilledExpensesTextField.setText(billables.get("unBilledExpenseAmount").toString());
        TotalCostTextField.setText(billables.get("totalBilledAmount").toString());
        BalanceTextField.setText(NumberFormatService.formatMoney(budget.subtract(total)));
    }

    public void clearWindow(){
        OpenDateDatePicker.setValue(null);
        ClosedDateDatePicker.setValue(null);
        WarrantyDateDatePicker.setValue(null);
        Label2TextField.setText("");
        Label3ComboBox.setValue(new MakeModel());
        Label4ComboBox.setValue(new ModelModel());
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
        MakeModel make = (MakeModel) Label3ComboBox.getValue();
        ModelModel model = (ModelModel) Label4ComboBox.getValue();
                
        //Update Matter
        Global.getCurrentMatter().setOpenDate(OpenDateDatePicker.getValue() == null 
                ? null : java.sql.Date.valueOf(OpenDateDatePicker.getValue()));
        Global.getCurrentMatter().setCloseDate(ClosedDateDatePicker.getValue() == null 
                ? null : java.sql.Date.valueOf(ClosedDateDatePicker.getValue()));
        
        
        Global.getCurrentMatter().setWarranty(WarrantyDateDatePicker.getValue() == null 
                ? null : java.sql.Date.valueOf(WarrantyDateDatePicker.getValue()));
        Global.getCurrentMatter().setBudget(Label2TextField.getText().trim().equals("")
                ? BigDecimal.ZERO : NumberFormatService.convertToBigDecimal(Label2TextField.getText().trim()));
        Global.getCurrentMatter().setMake(make.getId());
        Global.getCurrentMatter().setModel(model.getId());
        Global.getCurrentMatter().setSerial(Label5TextField.getText().trim().equals("") 
                ? null : Label5TextField.getText().trim());
                
        SQLMatter.updateMatterInformationByID(Global.getCurrentMatter());  
        
        loadInformation();
    }
    
}
