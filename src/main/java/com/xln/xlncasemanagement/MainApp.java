package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.util.LabelHashTables;
import javafx.application.Application;
import javafx.scene.image.Image;
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
        LabelHashTables.setGlobalLabels("2");
        
        Global.setApplicationLogo(new Image(getClass().getResource("/image/xlnlogo.png").toString()));
    }
    
}
