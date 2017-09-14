/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.CompanyModel;
import com.xln.xlncasemanagement.sql.SQLAudit;
import com.xln.xlncasemanagement.sql.SQLCompany;
import com.xln.xlncasemanagement.util.AlertDialog;
import com.xln.xlncasemanagement.util.NumberFormatService;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MaintenanceCompanySceneController implements Initializable {

    Stage stage;
    
    @FXML private TextField CompanyNameTextField;
    @FXML private TextField AddressOneTextField;
    @FXML private TextField AddressTwoTextField;
    @FXML private TextField AddressThreeTextField;
    @FXML private TextField CityTextField;
    @FXML private ComboBox StateComboBox;
    @FXML private TextField ZipCodeTextField;
    @FXML private TextField PhoneNumberTextField;
    @FXML private TextField FaxNumberTextField;
    @FXML private TextField EmailAddressTextField;
    @FXML private TextField WebAddressTextField;
    @FXML private ImageView logoImage;
    @FXML private Button UpdateLogoButton;
    @FXML private Button CloseButton;
    @FXML private Button SaveButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setActive(Stage stagePassed) {
        stage = stagePassed;
        addListeners();
        loadStateComboBox();
        loadCompanyInformation();
    }
    
    @FXML private void closeButtonAction() {
        stage.close();
    }
    
    @FXML private void saveButtonAction() {
        CompanyModel item = new CompanyModel();
        item.setName(CompanyNameTextField.getText().trim().isEmpty() ? null : CompanyNameTextField.getText().trim());
        item.setAddressOne(AddressOneTextField.getText().trim().isEmpty() ? null : AddressOneTextField.getText().trim());
        item.setAddressTwo(AddressTwoTextField.getText().trim().isEmpty() ? null : AddressTwoTextField.getText().trim());
        item.setAddressThree(AddressThreeTextField.getText().trim().isEmpty() ? null : AddressThreeTextField.getText().trim());
        item.setCity(CityTextField.getText().trim().isEmpty() ? null : CityTextField.getText().trim());
        item.setState(StateComboBox.getValue().toString().trim().isEmpty() ? null : StateComboBox.getValue().toString().trim());
        item.setZip(ZipCodeTextField.getText().trim().isEmpty() ? null : ZipCodeTextField.getText().trim());
        item.setPhone(PhoneNumberTextField.getText().trim().isEmpty() ? null : NumberFormatService.convertPhoneNumberToString(PhoneNumberTextField.getText().trim()));
        item.setFax(FaxNumberTextField.getText().trim().isEmpty() ? null : NumberFormatService.convertPhoneNumberToString(FaxNumberTextField.getText().trim()));
        item.setEmail(EmailAddressTextField.getText().trim().isEmpty() ? null : EmailAddressTextField.getText().trim());
        item.setWebsite(WebAddressTextField.getText().trim().isEmpty() ? null : WebAddressTextField.getText().trim());
        item.setId(Global.getCompanyInformation().getId());
        
        //Insert Data
        if (SQLCompany.updateCompanyInformation(item)){
            //Update Global Data if Successfully inserted.
            Global.setCompanyInformation(SQLCompany.getCompanyInformation());
            SQLAudit.insertAudit("Updated Company Information");
            stage.close();
        } else {
            //Show Dialog if Save to Database Fails
            AlertDialog.StaticAlert(4, "Database Save Error", "Unable To Save Information", "Unable to save company information to the database, please try again.");
        }
    }
    
    @FXML private void updateLogoButtonAction() {
        File image = logoFileChooser();
                
        if (image != null){
            updateLogo(image);
        }
    }
    
    private void addListeners(){
        SaveButton.disableProperty().bind(
                CompanyNameTextField.textProperty().isEmpty()
        );
    }
    
    private void loadStateComboBox() {
        for (String item : Global.getSTATES()) {
            StateComboBox.getItems().addAll(item);
        }
    }
    
    private void loadCompanyInformation() {
        CompanyModel item = SQLCompany.getCompanyInformation();
        
        CompanyNameTextField.setText(item.getName() == null ? "" : item.getName().trim());
        AddressOneTextField.setText(item.getAddressOne() == null ? "" : item.getAddressOne().trim());
        AddressTwoTextField.setText(item.getAddressTwo() == null ? "" : item.getAddressTwo().trim());
        AddressThreeTextField.setText(item.getAddressThree() == null ? "" : item.getAddressThree().trim());
        CityTextField.setText(item.getCity() == null ? "" : item.getCity().trim());
        StateComboBox.setValue(item.getState() == null ? "" : item.getState().trim());
        ZipCodeTextField.setText(item.getZip() == null ? "" : item.getZip().trim());
        PhoneNumberTextField.setText(item.getPhone() == null ? "" : NumberFormatService.convertStringToPhoneNumber(item.getPhone().trim()));
        FaxNumberTextField.setText(item.getFax() == null ? "" : NumberFormatService.convertStringToPhoneNumber(item.getFax().trim()));
        EmailAddressTextField.setText(item.getEmail() == null ? "" : item.getEmail().trim());
        WebAddressTextField.setText(item.getWebsite() == null ? "" : item.getWebsite().trim());
        
        if (item.getLogo() != null){
            logoImage.setImage(item.getLogo());
        }
    }
        
    private File logoFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
            );
         return fileChooser.showOpenDialog(stage);
    }
    
    private void updateLogo(File image) {
        if (SQLCompany.updateCompanyLogo(image)){
            logoImage.setImage(Global.getCompanyInformation().getLogo());
        } else {
            AlertDialog.StaticAlert(4, "Save Error",
                    "Unable To Update Company Logo",
                    "The image was not able to be saved to the database.");
        }
    }
}
