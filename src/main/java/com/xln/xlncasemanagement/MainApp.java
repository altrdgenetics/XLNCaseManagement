package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.util.LabelHashTables;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Remove Later
        LabelHashTables.setGlobalLabels("1");
        
        Global.setStageLauncher(new StageLauncher());
        Global.getStageLauncher().mainStage(stage);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
