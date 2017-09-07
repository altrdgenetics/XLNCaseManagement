/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.model.sql.CompanyModel;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sceneController.MainStageController;
import java.io.File;
import java.text.SimpleDateFormat;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Global {
    
    private static final boolean debug = true;
    
    
    private static Parent root;    
    private static Stage loginStage;
    private static Stage mainStage;
    private static MainStageController mainStageController;
    private static Stage incomingDocketingStage;
    private static Stage outgoingDocketingStage;
    private static StageLauncher stageLauncher;
    private static CompanyModel companyInformation;
    private static Image applicationLogo;
    private static UserModel currentUser;
    private static PartyModel currentClient;
    private static MatterModel currentMatter; 
    private static final int GLOBAL_BOOKMARKLIMIT = 10;
    private static final int MAX_ALLOWED_LOGIN_ATTEMPTS = 6;
    private static String JACOBDLL_PATH = "";
    private static final String tempDirectory = 
            System.getProperty("java.io.tmpdir") + "XLNCase" + File.separator;
        
    //Config File
    private static String version = "";
    private static String companyID = "";
            
    //list of all states
    private static final String STATES[] = { "AL", "AK", "AS", "AZ", "AR", "CA", "CO",
        "CT", "DE", "DC", "FL", "FM", "GA", "GU", "HI", "ID", "IL", "IN", "IA",
        "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
        "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR",
        "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VT", "VA",
        "VI", "WA", "WV", "WI", "WY"};
    
    //Date Formatters
    private static final SimpleDateFormat mmddyyyyhhmmssa = new SimpleDateFormat("EEE, MM/dd/yyyy hh:mm:ss a");
    private static final SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
    private static final SimpleDateFormat MMMMMdyyyy = new SimpleDateFormat("MMMMM d, yyyy");
    private static final SimpleDateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat hhmmssa = new SimpleDateFormat("hh:mm:ss a");
    
    //String Formatter
    private static final String moneyRegex = "-?\\p{Sc}?(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))*(\\.\\d{0,2})?";
    private static final String durationRegex = "(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))*(\\.\\d{0,2})?";
    
    //List of Label Changes
    private static String newCaseType = "";
    private static String leadWording = "";
    
    private static String buttonLabel1 = "";
    private static String buttonLabel2 = "";
    private static String buttonLabel3 = "";
    
    private static String headerLabel1 = "";
    private static String headerLabel2 = "";
    private static String headerLabel3 = "";
    private static String headerLabel4 = "";
    private static String headerLabel5 = "";
    
    private static String informationLabel1 = "";
    private static String informationLabel2 = "";
    private static String informationLabel3 = "";
    private static String informationLabel4 = "";
    private static String informationLabel5 = "";
    private static String informationLabel6 = "";

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        Global.version = version;
    }

    
    
    public static boolean isDebug() {
        return debug;
    }
    
    public static Parent getRoot() {
        return root;
    }

    public static void setRoot(Parent root) {
        Global.root = root;
    }

    public static Stage getLoginStage() {
        return loginStage;
    }

    public static void setLoginStage(Stage loginStage) {
        Global.loginStage = loginStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        Global.mainStage = mainStage;
    }

    public static MainStageController getMainStageController() {
        return mainStageController;
    }

    public static void setMainStageController(MainStageController mainStageController) {
        Global.mainStageController = mainStageController;
    }

    public static StageLauncher getStageLauncher() {
        return stageLauncher;
    }

    public static void setStageLauncher(StageLauncher stageLauncher) {
        Global.stageLauncher = stageLauncher;
    }

    public static Stage getIncomingDocketingStage() {
        return incomingDocketingStage;
    }

    public static void setIncomingDocketingStage(Stage incomingDocketingStage) {
        Global.incomingDocketingStage = incomingDocketingStage;
    }

    public static Stage getOutgoingDocketingStage() {
        return outgoingDocketingStage;
    }

    public static void setOutgoingDocketingStage(Stage outgoingDocketingStage) {
        Global.outgoingDocketingStage = outgoingDocketingStage;
    }

    public static CompanyModel getCompanyInformation() {
        return companyInformation;
    }

    public static void setCompanyInformation(CompanyModel companyInformation) {
        Global.companyInformation = companyInformation;
    }
    
    public static Image getApplicationLogo() {
        return applicationLogo;
    }

    public static void setApplicationLogo(Image applicationLogo) {
        Global.applicationLogo = applicationLogo;
    }

    public static UserModel getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserModel currentUser) {
        Global.currentUser = currentUser;
    }

    public static PartyModel getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(PartyModel currentClient) {
        Global.currentClient = currentClient;
    }

    public static MatterModel getCurrentMatter() {
        return currentMatter;
    }

    public static void setCurrentMatter(MatterModel currentMatter) {
        Global.currentMatter = currentMatter;
    }

    public static int getGLOBAL_BOOKMARKLIMIT() {
        return GLOBAL_BOOKMARKLIMIT;
    }

    public static String getTempDirectory() {
        return tempDirectory;
    }

    public static int getMAX_ALLOWED_LOGIN_ATTEMPTS() {
        return MAX_ALLOWED_LOGIN_ATTEMPTS;
    }

    public static String getJACOBDLL_PATH() {
        return JACOBDLL_PATH;
    }

    public static void setJACOBDLL_PATH(String JACOBDLL_PATH) {
        Global.JACOBDLL_PATH = JACOBDLL_PATH;
    }
    
    public static String[] getSTATES() {
        return STATES;
    }

    public static String getNewCaseType() {
        return newCaseType;
    }

    public static void setNewCaseType(String newCaseType) {
        Global.newCaseType = newCaseType;
    }

    public static String getLeadWording() {
        return leadWording;
    }

    public static void setLeadWording(String leadWording) {
        Global.leadWording = leadWording;
    }

    public static String getButtonLabel1() {
        return buttonLabel1;
    }

    public static void setButtonLabel1(String buttonLabel1) {
        Global.buttonLabel1 = buttonLabel1;
    }

    public static String getButtonLabel2() {
        return buttonLabel2;
    }

    public static void setButtonLabel2(String buttonLabel2) {
        Global.buttonLabel2 = buttonLabel2;
    }

    public static String getButtonLabel3() {
        return buttonLabel3;
    }

    public static void setButtonLabel3(String buttonLabel3) {
        Global.buttonLabel3 = buttonLabel3;
    }
    
    public static String getHeaderLabel1() {
        return headerLabel1;
    }

    public static void setHeaderLabel1(String headerLabel1) {
        Global.headerLabel1 = headerLabel1;
    }

    public static String getHeaderLabel2() {
        return headerLabel2;
    }

    public static void setHeaderLabel2(String headerLabel2) {
        Global.headerLabel2 = headerLabel2;
    }

    public static String getHeaderLabel3() {
        return headerLabel3;
    }

    public static void setHeaderLabel3(String headerLabel3) {
        Global.headerLabel3 = headerLabel3;
    }

    public static String getHeaderLabel4() {
        return headerLabel4;
    }

    public static void setHeaderLabel4(String headerLabel4) {
        Global.headerLabel4 = headerLabel4;
    }

    public static String getHeaderLabel5() {
        return headerLabel5;
    }

    public static void setHeaderLabel5(String headerLabel5) {
        Global.headerLabel5 = headerLabel5;
    }

    public static String getInformationLabel1() {
        return informationLabel1;
    }

    public static void setInformationLabel1(String informationLabel1) {
        Global.informationLabel1 = informationLabel1;
    }

    public static String getInformationLabel2() {
        return informationLabel2;
    }

    public static void setInformationLabel2(String informationLabel2) {
        Global.informationLabel2 = informationLabel2;
    }

    public static String getInformationLabel3() {
        return informationLabel3;
    }

    public static void setInformationLabel3(String informationLabel3) {
        Global.informationLabel3 = informationLabel3;
    }

    public static String getInformationLabel4() {
        return informationLabel4;
    }

    public static void setInformationLabel4(String informationLabel4) {
        Global.informationLabel4 = informationLabel4;
    }

    public static String getInformationLabel5() {
        return informationLabel5;
    }

    public static void setInformationLabel5(String informationLabel5) {
        Global.informationLabel5 = informationLabel5;
    }

    public static String getInformationLabel6() {
        return informationLabel6;
    }

    public static void setInformationLabel6(String informationLabel6) {
        Global.informationLabel6 = informationLabel6;
    }

    public static SimpleDateFormat getMmddyyyyhhmmssa() {
        return mmddyyyyhhmmssa;
    }

    public static SimpleDateFormat getiCalendarDateFormat() {
        return iCalendarDateFormat;
    }

    public static SimpleDateFormat getMMMMMdyyyy() {
        return MMMMMdyyyy;
    }
    
    public static SimpleDateFormat getMmddyyyy() {
        return mmddyyyy;
    }

    public static SimpleDateFormat getHhmmssa() {
        return hhmmssa;
    }

    public static String getMoneyRegex() {
        return moneyRegex;
    }

    public static String getDurationRegex() {
        return durationRegex;
    }

    public static String getCompanyID() {
        return companyID;
    }

    public static void setCompanyID(String companyID) {
        Global.companyID = companyID;
    }
    
}
