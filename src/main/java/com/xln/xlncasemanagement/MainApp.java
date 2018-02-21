package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.config.ConfigFile;
import com.xln.xlncasemanagement.sql.SQLCompany;
import com.xln.xlncasemanagement.util.FileUtilities;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        setApplicationDefaults();
        
        Global.setStageLauncher(new StageLauncher());
        Global.setLoginStage(stage);
        Global.getStageLauncher().loginScene();
    }

    private void setApplicationDefaults(){
        //Set boolean for Apple Computers
        Global.setIsAppleComputer(System.getProperty("os.name").equalsIgnoreCase("Mac OS X"));
        
        //Set Temp Folder
        FileUtilities.generateTempLocation();
        
        //Load DB Connection
        ConfigFile.readConfigFile();
                
        //Set company Information
        Global.setCompanyInformation(SQLCompany.getCompanyInformation());
        
        if (Global.getCompanyInformation().getLogo() != null){
            Global.setApplicationLogo(Global.getCompanyInformation().getLogo());
        }
    }    

}
