package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sql.SQLCompany;
import com.xln.xlncasemanagement.sql.SQLUser;
import com.xln.xlncasemanagement.util.LabelHashTables;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        setApplicationDefaults();
        
        Global.setStageLauncher(new StageLauncher());
        Global.getStageLauncher().loginScene(stage);
    }

    private void setApplicationDefaults(){
        //Remove Later
        setSpoofData();
        
        //Set company Information
        Global.setCompanyInformation(SQLCompany.getCompanyInformation());
        
        if (Global.getCompanyInformation().getLogo() != null){
            Global.setApplicationLogo(Global.getCompanyInformation().getLogo());
        }
    }
        
    /**
     * Set pre-configured options for testing
     */
    private void setSpoofData() {
        String version = "1";
        
        LabelHashTables.setGlobalLabels(version);
        Global.setVersion(version);
        
        UserModel user = SQLUser.getUserByUserName("anthony.perk");
        Global.setCurrentUser(user);
    }
}
