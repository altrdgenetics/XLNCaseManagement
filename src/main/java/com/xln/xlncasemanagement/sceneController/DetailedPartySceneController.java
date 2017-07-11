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
public class DetailedPartySceneController implements Initializable {

    
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
    
    boolean maintenanceMode;
    PartyModel partyItem;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setActive(boolean maintenanceModePassed, PartyModel itemPassed) {
        maintenanceMode = maintenanceModePassed;
        partyItem = itemPassed;
        String partyLabel = "";
        loadCaseRelationComboBox();
        loadPrefixComboBox();
        loadStateComboBox();
        if (!maintenanceMode){
            partyLabel = "Case ";
        }
        
        if (partyItem == null){
            HeaderLabel.setText("Add " + partyLabel + "Party Information");
            gridPane.getChildren().remove(CaseRelationLabel);
            gridPane.getChildren().remove(CaseRelationCombobox);
            gridPane.getRowConstraints().get(1).setMaxHeight(0);
        } else {
            HeaderLabel.setText("Edit " + partyLabel + "Party Information");
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
        CaseRelationCombobox.setValue(partyItem.getRelationName());
        PrefixCombobox.setValue(partyItem.getPrefix());
        FirstNameTextField.setText(partyItem.getFirstName());
        MiddleInitialTextField.setText(partyItem.getMiddleInitial());
        lastNameTextField.setText(partyItem.getLastName());
        AddressOneTextField.setText(partyItem.getAddressOne());
        AddressTwoTextField.setText(partyItem.getAddressTwo());
        AddressThreeTextField.setText(partyItem.getAddressThree());
        CityTextField.setText(partyItem.getCity());
        StateComboBox.setValue(partyItem.getState());
        ZipCodeTextField.setText(partyItem.getZip());
        PhoneNumberOneTextField.setText(partyItem.getPhoneOne());
        PhoneNumberTwoTextField.setText(partyItem.getPhoneTwo());
        EmailAddressTextField.setText(partyItem.getEmail());
    }
    
    @FXML
    private void saveInformation() {
        
    }
    
}
