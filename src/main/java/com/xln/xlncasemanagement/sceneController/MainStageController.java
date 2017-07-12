/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.sceneController;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.sql.SQLParty;
import com.xln.xlncasemanagement.util.DebugTools;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainStageController implements Initializable {

    private Stage stage;
    private Tab selectedTabTitle;
    
    //Menu Items----------------------------------------------------
    @FXML private Menu fileMenu;
    @FXML private MenuItem preferencesMenuItem;
    @FXML private MenuItem closeMenuItem;
    @FXML private Menu editMenu;
    @FXML private MenuItem partyRolodexMenuItem;
    @FXML private SeparatorMenuItem adminSeperatorMenuItem;
    @FXML private Menu adminMenu;
    @FXML private Menu reportMenu;
    @FXML private MenuItem runReportMenuItem;
    @FXML private MenuItem databaseMaintenanceMenuItem;
    @FXML private Menu helpMenu;
    @FXML private MenuItem aboutMenuItem;
    
    //Header Information--------------------------------------------
    @FXML private Label clientLabel;
    @FXML private ComboBox clientField;
    @FXML private Label headerLabel1;
    @FXML private ComboBox headerField1;
    @FXML private Label phoneLabel;
    @FXML private TextField phoneField;
    @FXML private Label emailLabel;
    @FXML private TextField emailField;
    @FXML private Label headerLabel2;
    @FXML private TextField headerField2;
    @FXML private Label headerLabel3;
    @FXML private TextField headerField3;
    @FXML private Label headerLabel4;
    @FXML private TextField headerField4;
    @FXML private Label headerLabel5;
    @FXML private TextField headerField5;
    @FXML private ImageView headerLogo;
    
    //Left Hand Side Buttons----------------------------------------
    @FXML private VBox buttonBar;
    @FXML private Button buttonOne;
    @FXML private Button buttonTwo;
    @FXML private Button buttonThree;
    @FXML private Button buttonFour;
    @FXML private Button buttonFive;
    @FXML private Button buttonSix;
    @FXML private Button buttonSeven;
    @FXML private Button buttonDelete;
    
    //Inject Sub-FXML------------------------------------------------
    @FXML private AnchorPane informationScene; // Inject tab content
    @FXML private InformationSceneController informationSceneController; // Inject controller
    @FXML private BorderPane casePartyScene; // Inject tab content
    @FXML private CasePartySceneController casePartySceneController; // Inject controller
    @FXML private BorderPane activityScene; // Inject tab content
    @FXML private ActivitySceneController activitySceneController; // Inject controller
    @FXML private BorderPane expensesScene; // Inject tab content
    @FXML private ExpensesSceneController expensesSceneController; // Inject controller
    @FXML private AnchorPane notesScene; // Inject tab content
    @FXML private NotesSceneController notesSceneController; // Inject controller
    
    //Tabs----------------------------------------------------------
    @FXML private TabPane mainTabPane;
    @FXML private Tab informationTab;
    @FXML private Tab partyTab;
    @FXML private Tab activityTab;
    @FXML private Tab expenseTab;
    @FXML private Tab noteTab;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
            onTabSelection();
        });
                
        //Setup Client ComboBox
        StringConverter<PartyModel> converter = new StringConverter<PartyModel>() {
            @Override
            public String toString(PartyModel object) {
                return StringUtilities.buildPartyName(object);
            }

            @Override
            public PartyModel fromString(String string) {
                return null;
            }
        };
        clientField.setConverter(converter);
        
        //Setup Client ComboBox
        StringConverter<MatterModel> converter2 = new StringConverter<MatterModel>() {
            @Override
            public String toString(MatterModel object) {
                return object.getMatterTypeName() + " - " + Global.getMmddyyyy().format(object.getOpenDate());
            }

            @Override
            public MatterModel fromString(String string) {
                return null;
            }
        };
        headerField1.setConverter(converter2);
    }    
        
    public void setActive(Stage stagePassed) {
        Global.setMainStage(stage);
        stage = stagePassed;
        setVersionInformation();
        stage.setTitle("Case Management");
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        
        if (!Global.getCurrentUser().isAdminRights()){
            editMenu.getItems().remove(adminSeperatorMenuItem);
            editMenu.getItems().remove(adminMenu);
        }
        
        headerLogo.setImage(Global.getApplicationLogo());
        loadClientComboBox();
    }

    private void setVersionInformation() {
        setHeaderLabels();
        hideHeaderControls();
        setButtonLabels();
        hideButtons();
    }
    
    @FXML private void handlePreferencesMenuItem() {
        //TODO
    }
    
    @FXML private void handleCloseMenuItem() { 
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
    }
    
    @FXML private void handlePartyRolodexMenuItem(){
        Global.getStageLauncher().partySearchScene(stage, true, false);
    }
    
    @FXML private void handleDatabaseMaintenanceMenuItem() {
        Global.getStageLauncher().MaintenanceScene(stage);
    }
    
    @FXML private void handleRunReportMenuItem() {
        //TODO
    }
        
    @FXML private void handleAboutMenuItem() {
        //TODO
    }
        
    @FXML private void handleClientSelection(){
        Global.setCurrentClient((PartyModel) clientField.getValue());
        
        disableTabsAndButtons(true);
        
        Global.setCurrentMatter(null);
        
        String phone = NumberFormatService.convertStringToPhoneNumber(Global.getCurrentClient().getPhoneOne());
        if (Global.getCurrentClient().getPhoneTwo() != null && !phone.trim().equals("")){
            phone += ", ";
        }
        phone += NumberFormatService.convertStringToPhoneNumber(Global.getCurrentClient().getPhoneTwo());
                
        phoneField.setText(phone);
        emailField.setText(Global.getCurrentClient().getEmail());
                
        loadMatterComboBox();
    }
    
    @FXML private void handleHeaderField1Selection(){
        Global.setCurrentMatter((MatterModel) headerField1.getValue());
        disableTabsAndButtons(false);
    }
    
    private void setHeaderLabels(){
        headerLabel1.setText(Global.getHeaderLabel1());
        headerLabel2.setText(Global.getHeaderLabel2());
        headerLabel3.setText(Global.getHeaderLabel3());
        headerLabel4.setText(Global.getHeaderLabel4());
        headerLabel5.setText(Global.getHeaderLabel5());
    }
    
    private void hideHeaderControls() {
        headerField1.setVisible(!Global.getHeaderLabel1().equals(""));
        headerField2.setVisible(!Global.getHeaderLabel2().equals(""));
        headerField3.setVisible(!Global.getHeaderLabel3().equals(""));
        headerField4.setVisible(!Global.getHeaderLabel4().equals(""));
        headerField5.setVisible(!Global.getHeaderLabel5().equals(""));
    }
    
    private void setButtonLabels() {
        buttonOne.setText(Global.getButtonLabel1());
        buttonTwo.setText(Global.getButtonLabel2());
        buttonThree.setText(Global.getButtonLabel3());
    }
    
    private void hideButtons(){
        if (Global.getButtonLabel2().equals("")){
            buttonBar.getChildren().remove(buttonTwo);
        }
        if (Global.getButtonLabel3().equals("")){
            buttonBar.getChildren().remove(buttonThree);
        }
        
        //currently not used
        buttonBar.getChildren().remove(buttonSix);
        buttonBar.getChildren().remove(buttonSeven);
    }
    
    @FXML private void buttonOneAction(){
        Global.getStageLauncher().partySearchScene(stage, false, true);
    }
    
    @FXML private void buttonTwoAction() {
        if (Global.getIncomingDocketingStage() == null) {
            Global.getStageLauncher().docketingIncomingScene(Global.getMainStage());
        } else {
            if (!Global.getIncomingDocketingStage().isShowing()) {
                Global.getIncomingDocketingStage().show();
            } else {
                Global.getIncomingDocketingStage().toFront();
            }
        }
    }

    @FXML private void buttonThreeAction(){
        if (Global.getOutgoingDocketingStage() == null) {
            Global.getStageLauncher().docketingOutgoingScene(Global.getMainStage());
        } else {
            if (!Global.getOutgoingDocketingStage().isShowing()) {
                Global.getOutgoingDocketingStage().show();
            } else {
                Global.getOutgoingDocketingStage().toFront();
            }
        }
    }
    
    @FXML private void buttonFourAction(){        
        if (selectedTabTitle.equals(informationTab)){
            informationSceneController.mainPanelButtonFourAction();
            disableTabsInUpdateMode(informationSceneController.isUpdateMode());
        } else if (selectedTabTitle.equals(partyTab)) {
            Global.getStageLauncher().partySearchScene(stage, false, false);
        } else if (selectedTabTitle.equals(activityTab)) {
            Global.getStageLauncher().detailedActivityAddEditScene(Global.getMainStage(), null);
        } else if (selectedTabTitle.equals(expenseTab)) {
            Global.getStageLauncher().detailedExpenseAddEditScene(Global.getMainStage(), null);
        } else if (selectedTabTitle.equals(noteTab)){
            notesSceneController.mainPanelButtonFourAction();
            disableTabsInUpdateMode(notesSceneController.isUpdateMode());
        }
    }
    
    @FXML private void buttonFiveAction(){
        //Letters Button Action
    }
    
    @FXML private void buttonSixAction(){
        //Currently Hidden
    }
    
    @FXML private void buttonSevenAction(){
        //Currently Hidden
    }
    
    @FXML private void buttonDeleteAction(){
        if (selectedTabTitle.equals(informationTab)){
            informationSceneController.mainPanelButtonDeleteAction();
            disableTabsInUpdateMode(informationSceneController.isUpdateMode());
        } else if (selectedTabTitle.equals(partyTab)) {
            //TODO
        } else if (selectedTabTitle.equals(activityTab)) {
            //TODO
        } else if (selectedTabTitle.equals(expenseTab)) {
            //TODO
        } else if (selectedTabTitle.equals(noteTab)){
            notesSceneController.mainPanelButtonDeleteAction();
            disableTabsInUpdateMode(notesSceneController.isUpdateMode());
        }
    }
    
    
    private void disableTabsInUpdateMode(boolean disable){   
        for(Tab tab : mainTabPane.getTabs()){
            if (!tab.equals(selectedTabTitle)){
                tab.setDisable(disable);
            }
        }
        clientField.setDisable(disable);
        headerField1.setDisable(disable);
        buttonOne.setDisable(disable);
        buttonTwo.setDisable(disable);
        buttonThree.setDisable(disable);
        buttonFive.setDisable(disable);
        buttonSix.setDisable(disable);
        buttonSeven.setDisable(disable);
        updateButtonFourLabel(disable);
    }
    
    private void onTabSelection() {
        selectedTabTitle = mainTabPane.getSelectionModel().getSelectedItem();

        if (selectedTabTitle.equals(informationTab)) {
            DebugTools.Printout("Selected Information Tab");
            informationSceneController.setActive();
            setInformationTabButtons();
        } else if (selectedTabTitle.equals(partyTab)) {
            DebugTools.Printout("Selected Party Tab");
            casePartySceneController.setActive();
            setPartyTabButtons();
        } else if (selectedTabTitle.equals(activityTab)) {
            DebugTools.Printout("Selected Activity Tab");
            activitySceneController.setActive();
            setActivityTabButtons();
        } else if (selectedTabTitle.equals(expenseTab)) {
            DebugTools.Printout("Selected Expense Tab");
            expensesSceneController.setActive();
            setExpenseTabButtons();
        } else if (selectedTabTitle.equals(noteTab)) {
            DebugTools.Printout("Selected Note Tab");
            notesSceneController.setActive();
            setNoteTabButtons();
        }
    }

    private void setInformationTabButtons(){
        buttonFour.setText("Update Info");
        buttonFive.setText("Letters");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Cancel");
        
        buttonDelete.setDisable(true);
        buttonDelete.getStyleClass().remove("danger");
        buttonDelete.getStyleClass().remove("warning");
        buttonDelete.getStyleClass().add("warning");
    }

    private void setPartyTabButtons(){
        buttonFour.setText("Add Party");
        buttonFive.setText("Letters");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
        
        buttonDelete.setDisable(true);
        buttonDelete.getStyleClass().remove("danger");
        buttonDelete.getStyleClass().remove("warning");
        buttonDelete.getStyleClass().add("danger");
    }

    private void setActivityTabButtons(){
        buttonFour.setText("Add Activty");
        buttonFive.setText("Letters");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
        
        buttonDelete.setDisable(true);
        buttonDelete.getStyleClass().remove("danger");
        buttonDelete.getStyleClass().remove("warning");
        buttonDelete.getStyleClass().add("danger");
    }

    private void setExpenseTabButtons(){
        buttonFour.setText("Add Expense");
        buttonFive.setText("Letters");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Delete");
        
        buttonDelete.setDisable(true);
        buttonDelete.getStyleClass().remove("danger");
        buttonDelete.getStyleClass().remove("warning");
        buttonDelete.getStyleClass().add("danger");
    }

    private void setNoteTabButtons(){
        buttonFour.setText("Update Note");
        buttonFive.setText("Letters");
        buttonSix.setText("");
        buttonSeven.setText("");
        buttonDelete.setText("Cancel");
        
        buttonDelete.setDisable(true);
        buttonDelete.getStyleClass().remove("danger");
        buttonDelete.getStyleClass().remove("warning");
        buttonDelete.getStyleClass().add("warning");
    }
    
    private void updateButtonFourLabel(boolean disable){
        String buttonFourLabel = "Update ";
        
        if (disable){
            buttonFourLabel = "Save ";
            buttonFour.getStyleClass().remove("info");
            buttonFour.getStyleClass().add("success");
            buttonDelete.setDisable(false);
        } else {
            buttonFour.getStyleClass().remove("success");
            buttonFour.getStyleClass().add("info");
            buttonDelete.setDisable(true);
        }
        
        if (selectedTabTitle.equals(informationTab)){
            buttonFourLabel += "Info";
        } else if (selectedTabTitle.equals(partyTab)) {
            buttonFourLabel += "Party";
        } else if (selectedTabTitle.equals(activityTab)) {
            buttonFourLabel += "Activity";
        } else if (selectedTabTitle.equals(expenseTab)) {
            buttonFourLabel += "Expense";
        } else if (selectedTabTitle.equals(noteTab)){
            buttonFourLabel += "Note";
        }
        buttonFour.setText(buttonFourLabel);
    }
    
    public void loadClientComboBox(){
        clientField.getItems().removeAll(clientField.getItems());
        for (PartyModel item : SQLParty.getActiveClients()){
            clientField.getItems().addAll(item);
        }
    }
    
    private void loadMatterComboBox(){
        headerField1.getItems().removeAll(headerField1.getItems());
        for (MatterModel item : SQLMatter.getActiveMattersByClient(Global.getCurrentClient().getId())){
            headerField1.getItems().addAll(item);
        }
    }
    
    private void disableTabsAndButtons(boolean disabled){
        //Buttons
        buttonFour.setDisable(disabled);
        buttonFive.setDisable(disabled);
        buttonSix.setDisable(disabled);
        buttonSeven.setDisable(disabled);
        
        //Tabs
        informationTab.setDisable(disabled);
        partyTab.setDisable(disabled);
        activityTab.setDisable(disabled);
        expenseTab.setDisable(disabled);
        noteTab.setDisable(disabled);
        
        if (disabled){
            mainTabPane.getSelectionModel().select(informationTab);
        }
    }
    
    // GETTERS AND SETTERS------------------------------------------------
    
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public AnchorPane getInformationScene() {
        return informationScene;
    }

    public void setInformationScene(AnchorPane informationScene) {
        this.informationScene = informationScene;
    }

    public InformationSceneController getInformationSceneController() {
        return informationSceneController;
    }

    public void setInformationSceneController(InformationSceneController informationSceneController) {
        this.informationSceneController = informationSceneController;
    }

    public BorderPane getCasePartyScene() {
        return casePartyScene;
    }

    public void setCasePartyScene(BorderPane casePartyScene) {
        this.casePartyScene = casePartyScene;
    }

    public CasePartySceneController getCasePartySceneController() {
        return casePartySceneController;
    }

    public void setCasePartySceneController(CasePartySceneController casePartySceneController) {
        this.casePartySceneController = casePartySceneController;
    }

    public BorderPane getActivityScene() {
        return activityScene;
    }

    public void setActivityScene(BorderPane activityScene) {
        this.activityScene = activityScene;
    }

    public ActivitySceneController getActivitySceneController() {
        return activitySceneController;
    }

    public void setActivitySceneController(ActivitySceneController activitySceneController) {
        this.activitySceneController = activitySceneController;
    }

    public BorderPane getExpensesScene() {
        return expensesScene;
    }

    public void setExpensesScene(BorderPane expensesScene) {
        this.expensesScene = expensesScene;
    }

    public ExpensesSceneController getExpensesSceneController() {
        return expensesSceneController;
    }

    public void setExpensesSceneController(ExpensesSceneController expensesSceneController) {
        this.expensesSceneController = expensesSceneController;
    }

    public AnchorPane getNotesScene() {
        return notesScene;
    }

    public void setNotesScene(AnchorPane notesScene) {
        this.notesScene = notesScene;
    }

    public NotesSceneController getNotesSceneController() {
        return notesSceneController;
    }

    public void setNotesSceneController(NotesSceneController notesSceneController) {
        this.notesSceneController = notesSceneController;
    }

    public TabPane getMainTabPane() {
        return mainTabPane;
    }

    public void setMainTabPane(TabPane mainTabPane) {
        this.mainTabPane = mainTabPane;
    }

    public Tab getInformationTab() {
        return informationTab;
    }

    public void setInformationTab(Tab informationTab) {
        this.informationTab = informationTab;
    }

    public Tab getPartyTab() {
        return partyTab;
    }

    public void setPartyTab(Tab partyTab) {
        this.partyTab = partyTab;
    }

    public Tab getActivityTab() {
        return activityTab;
    }

    public void setActivityTab(Tab activityTab) {
        this.activityTab = activityTab;
    }

    public Tab getExpenseTab() {
        return expenseTab;
    }

    public void setExpenseTab(Tab expenseTab) {
        this.expenseTab = expenseTab;
    }

    public Tab getNoteTab() {
        return noteTab;
    }

    public void setNoteTab(Tab noteTab) {
        this.noteTab = noteTab;
    }

    public Button getButtonOne() {
        return buttonOne;
    }

    public void setButtonOne(Button buttonOne) {
        this.buttonOne = buttonOne;
    }

    public Button getButtonTwo() {
        return buttonTwo;
    }

    public void setButtonTwo(Button buttonTwo) {
        this.buttonTwo = buttonTwo;
    }

    public Button getButtonThree() {
        return buttonThree;
    }

    public void setButtonThree(Button buttonThree) {
        this.buttonThree = buttonThree;
    }

    public Button getButtonFour() {
        return buttonFour;
    }

    public void setButtonFour(Button buttonFour) {
        this.buttonFour = buttonFour;
    }

    public Button getButtonFive() {
        return buttonFive;
    }

    public void setButtonFive(Button buttonFive) {
        this.buttonFive = buttonFive;
    }

    public Button getButtonSix() {
        return buttonSix;
    }

    public void setButtonSix(Button buttonSix) {
        this.buttonSix = buttonSix;
    }

    public Button getButtonSeven() {
        return buttonSeven;
    }

    public void setButtonSeven(Button buttonSeven) {
        this.buttonSeven = buttonSeven;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public ComboBox getClientField() {
        return clientField;
    }

    public void setClientField(ComboBox clientField) {
        this.clientField = clientField;
    }

    public ComboBox getHeaderField1() {
        return headerField1;
    }

    public void setHeaderField1(ComboBox headerField1) {
        this.headerField1 = headerField1;
    }

    
}
