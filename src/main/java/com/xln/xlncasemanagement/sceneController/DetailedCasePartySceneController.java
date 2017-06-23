/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Andrew
 */
public class DetailedCasePartySceneController implements Initializable {

    @FXML ComboBox CaseRelationCombobox;
    @FXML ComboBox PrefixCombobox;
    @FXML TextField FirstNameTextField;
    @FXML TextField MiddleInitialTextField;
    @FXML TextField lastNameTextField;
    @FXML TextField AddressOneTextField;
    @FXML TextField AddressTwoTextField;
    @FXML TextField AddressThreeTextField;
    @FXML TextField CityTextField;
    @FXML ComboBox StateComboBox;
    @FXML TextField ZipCodeTextField;
    @FXML TextField PhoneNumberOneTextField;
    @FXML TextField PhoneNumberTwoTextField;
    @FXML TextField EmailAddressTextField;
    @FXML Button CloseButton;
    @FXML Button SaveButton;    
    
    PartyModel caseParty;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setActive(PartyModel CasePartyPassed) {
        caseParty = CasePartyPassed;
        loadCaseRelationComboBox();
        loadPrefixComboBox();
        loadStateComboBox();
        loadInformation();
    }
    
    private void loadCaseRelationComboBox() {
        
    }
    
    private void loadPrefixComboBox() {
        
    }
    
    private void loadStateComboBox() {
        for (String item : Global.getSTATES()) {
            StateComboBox.getItems().addAll(item);
        }
    }

    private void loadInformation() {
        CaseRelationCombobox.setValue(caseParty.getRelation());
        PrefixCombobox.setValue(caseParty.getPrefix());
        FirstNameTextField.setText(caseParty.getFirstName());
        MiddleInitialTextField.setText(caseParty.getMiddleInitial());
        lastNameTextField.setText(caseParty.getLastName());
        AddressOneTextField.setText(caseParty.getAddressOne());
        AddressTwoTextField.setText(caseParty.getAddressTwo());
        AddressThreeTextField.setText(caseParty.getAddressThree());
        CityTextField.setText(caseParty.getCity());
        StateComboBox.setValue(caseParty.getState());
        ZipCodeTextField.setText(caseParty.getZip());
        PhoneNumberOneTextField.setText(caseParty.getPhoneOne());
        PhoneNumberTwoTextField.setText(caseParty.getPhoneTwo());
        EmailAddressTextField.setText(caseParty.getEmail());
    }
}
