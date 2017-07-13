/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.PartyNamePrefixModel;
import com.xln.xlncasemanagement.model.sql.PartyRelationTypeModel;
import com.xln.xlncasemanagement.sql.SQLCaseParty;
import com.xln.xlncasemanagement.sql.SQLParty;
import com.xln.xlncasemanagement.sql.SQLPartyNamePrefix;
import com.xln.xlncasemanagement.sql.SQLPartyRelationType;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Andrew
 */
public class DetailedPartySceneController implements Initializable {

    Stage stage;
    
    @FXML private GridPane gridPane;
    @FXML private Label HeaderLabel;
    @FXML private Label CaseRelationLabel;
    @FXML private ComboBox<PartyRelationTypeModel> CaseRelationCombobox;
    @FXML private ComboBox PrefixCombobox;
    @FXML private TextField FirstNameTextField;
    @FXML private TextField MiddleInitialTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField AddressOneTextField;
    @FXML private TextField AddressTwoTextField;
    @FXML private TextField AddressThreeTextField;
    @FXML private TextField CityTextField;
    @FXML private ComboBox StateComboBox;
    @FXML private TextField ZipCodeTextField;
    @FXML private TextField PhoneNumberOneTextField;
    @FXML private TextField PhoneNumberTwoTextField;
    @FXML private TextField EmailAddressTextField;
    @FXML private Button CloseButton;
    @FXML private Button SaveButton;    
    
    boolean maintenanceMode;
    PartyModel partyItem;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup ComboBox
        StringConverter<PartyRelationTypeModel> converter = new StringConverter<PartyRelationTypeModel>() {
            @Override
            public String toString(PartyRelationTypeModel object) {
                return object.getPartyRelationType();
            }

            @Override
            public PartyRelationTypeModel fromString(String string) {
                return null;
            }
        };
        CaseRelationCombobox.setConverter(converter);
        
        SaveButton.disableProperty().bind(lastNameTextField.textProperty().isEmpty());
    }    
    
    public void setActive(Stage stagePassed, boolean maintenanceModePassed, PartyModel itemPassed) {
        stage = stagePassed;
        maintenanceMode = maintenanceModePassed;
        partyItem = itemPassed;
        String partyLabel = "";
        loadCaseRelationComboBox();
        loadPrefixComboBox();
        loadStateComboBox();
        if (maintenanceMode){
            gridPane.getChildren().remove(CaseRelationLabel);
            gridPane.getChildren().remove(CaseRelationCombobox);
            gridPane.getRowConstraints().get(1).setMaxHeight(0);
        } else {
            partyLabel = "Case ";
        }
        
        if (partyItem == null){
            HeaderLabel.setText("Add " + partyLabel + "Party Information");
        } else {
            HeaderLabel.setText("Edit " + partyLabel + "Party Information");
            loadInformation();
        }
    }
    
    @FXML private void closeButtonAction(){
        stage.close();
    }
    
    private void loadCaseRelationComboBox() {
        for (PartyRelationTypeModel item : SQLPartyRelationType.getActivePartyRelationType()){
            CaseRelationCombobox.getItems().addAll(item);
        }
    }
    
    private void loadPrefixComboBox() {
        for (PartyNamePrefixModel item : SQLPartyNamePrefix.getActivePartyNamePrefix()){
            PrefixCombobox.getItems().addAll(item.getPrefix());
        }
    }
    
    private void loadStateComboBox() {
        for (String item : Global.getSTATES()) {
            StateComboBox.getItems().addAll(item);
        }
    }

    private void loadInformation() {
        if (!maintenanceMode){
            PartyRelationTypeModel relation = new PartyRelationTypeModel();
            relation.setId(partyItem.getRelationID());
            relation.setPartyRelationType(partyItem.getRelationName());
            
            CaseRelationCombobox.setValue(relation);
        }
                
        PrefixCombobox.setValue(partyItem.getPrefix() == null ? "" : partyItem.getPrefix());
        FirstNameTextField.setText(partyItem.getFirstName() == null ? "" : partyItem.getFirstName());
        MiddleInitialTextField.setText(partyItem.getMiddleInitial() == null ? "" : partyItem.getMiddleInitial());
        lastNameTextField.setText(partyItem.getLastName() == null ? "" : partyItem.getLastName());
        AddressOneTextField.setText(partyItem.getAddressOne() == null ? "" : partyItem.getAddressOne());
        AddressTwoTextField.setText(partyItem.getAddressTwo() == null ? "" : partyItem.getAddressTwo());
        AddressThreeTextField.setText(partyItem.getAddressThree() == null ? "" : partyItem.getAddressThree());
        CityTextField.setText(partyItem.getCity() == null ? "" : partyItem.getCity());
        StateComboBox.setValue(partyItem.getState() == null ? "" : partyItem.getState());
        ZipCodeTextField.setText(partyItem.getZip() == null ? "" : partyItem.getZip());
        PhoneNumberOneTextField.setText(NumberFormatService.convertStringToPhoneNumber(partyItem.getPhoneOne()));
        PhoneNumberTwoTextField.setText(NumberFormatService.convertStringToPhoneNumber(partyItem.getPhoneTwo()));
        EmailAddressTextField.setText(partyItem.getEmail() == null ? "" : partyItem.getEmail());
    }

    @FXML
    private void saveInformation() {
        PartyModel item = new PartyModel();
        
        item.setPrefix(PrefixCombobox.getValue() == null ?  null : 
                (PrefixCombobox.getValue().toString().trim().equals("") ? null : PrefixCombobox.getValue().toString().trim()));
        item.setFirstName(FirstNameTextField.getText().trim().equals("") ? null : FirstNameTextField.getText().trim());
        item.setMiddleInitial(MiddleInitialTextField.getText().trim().equals("") ? null : MiddleInitialTextField.getText().trim());
        item.setLastName(lastNameTextField.getText().trim().equals("") ? null : lastNameTextField.getText().trim());
        item.setAddressOne(AddressOneTextField.getText().trim().equals("") ? null : AddressOneTextField.getText().trim());
        item.setAddressTwo(AddressTwoTextField.getText().trim().equals("") ? null : AddressTwoTextField.getText().trim());
        item.setAddressThree(AddressThreeTextField.getText().trim().equals("") ? null : AddressThreeTextField.getText().trim());
        item.setCity(CityTextField.getText().trim().equals("") ? null : CityTextField.getText().trim());
        item.setState(StateComboBox.getValue() == null ?  null : 
                (StateComboBox.getValue().toString().trim().equals("") ? null : StateComboBox.getValue().toString().trim()));
        item.setZip(ZipCodeTextField.getText().trim().equals("") ? null : ZipCodeTextField.getText().trim());
        item.setPhoneOne(PhoneNumberOneTextField.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(PhoneNumberOneTextField.getText().trim()));
        item.setPhoneTwo(PhoneNumberTwoTextField.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(PhoneNumberTwoTextField.getText().trim()));
        item.setEmail(EmailAddressTextField.getText().trim().equals("") ? null : EmailAddressTextField.getText().trim());

        //For If In Update Mode - Existing Party
        if (partyItem != null) {
            item.setId(partyItem.getId());
            item.setActive(partyItem.isActive());
        } else {
            item.setActive(true);
        }
        
        //Case Party OR Party Table logic.
        if (maintenanceMode) {
            //For non Case Party People
            if (partyItem != null) {
                SQLParty.updatePartyByID(item);
            } else {
                SQLParty.insertParty(item);
            }
        } else {
            //FOR CaseParty People
            PartyRelationTypeModel relation = (PartyRelationTypeModel) CaseRelationCombobox.getValue();  
                        
            item.setRelationID(relation.getId());
            item.setPartyID(partyItem.getPartyID());
            item.setMatterID(partyItem.getMatterID());
            //update party
            SQLCaseParty.updateCasePartyByID(item);
        }
        stage.close();
    }

}
