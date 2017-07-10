/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.CompanyModel;
import com.xln.xlncasemanagement.sql.SQLCompany;
import com.xln.xlncasemanagement.util.AlertDialog;
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
        loadStateComboBox();
        loadCompanyInformation();
    }
    
    @FXML private void closeButtonAction() {
        stage.close();
    }
    
    @FXML private void saveButtonAction() {
        
    }
    
    @FXML private void updateLogoButtonAction() {
        File image = logoFileChooser();
                
        if (image != null){
            updateLogo(image);
        }
    }
    
    private void loadStateComboBox() {
        for (String item : Global.getSTATES()) {
            StateComboBox.getItems().addAll(item);
        }
    }
    
    private void loadCompanyInformation() {
        CompanyModel item = SQLCompany.getCompanyInformation();
        
        CompanyNameTextField.setText(item.getName());
        AddressOneTextField.setText(item.getAddressOne());
        AddressTwoTextField.setText(item.getAddressTwo());
        AddressThreeTextField.setText(item.getAddressThree());
        CityTextField.setText(item.getCity());
        StateComboBox.setValue(item.getState());
        ZipCodeTextField.setText(item.getZip());
        PhoneNumberTextField.setText(item.getPhone());
        FaxNumberTextField.setText(item.getFax());
        EmailAddressTextField.setText(item.getEmail());
        WebAddressTextField.setText(item.getWebsite());
        
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
