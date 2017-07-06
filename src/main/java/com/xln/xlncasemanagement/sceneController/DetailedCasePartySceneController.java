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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Andrew
 */
public class DetailedCasePartySceneController implements Initializable {

    
    @FXML private GridPane gridPane;
    @FXML private Label HeaderLabel;
    @FXML private Label CaseRelationLabel;
    @FXML private ComboBox CaseRelationCombobox;
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
    
    public void setActive(PartyModel itemPassed) {
        caseParty = itemPassed;
        loadCaseRelationComboBox();
        loadPrefixComboBox();
        loadStateComboBox();
        if (caseParty == null){
            HeaderLabel.setText("Add Case Party Information");
            gridPane.getChildren().remove(CaseRelationLabel);
            gridPane.getChildren().remove(CaseRelationCombobox);
            gridPane.getRowConstraints().get(1).setMaxHeight(0);
        } else {
            HeaderLabel.setText("Edit Case Party Information");
            loadInformation();
        }
        
        
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
    
    @FXML
    private void saveInformation() {
        
    }
    
}
