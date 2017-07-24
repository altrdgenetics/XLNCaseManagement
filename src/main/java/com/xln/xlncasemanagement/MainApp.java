package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.model.sql.UserModel;
import com.xln.xlncasemanagement.sql.SQLCompany;
import com.xln.xlncasemanagement.util.LabelHashTables;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;
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

        //Get Locale Information
        Locale locale = Locale.getDefault();
        
        DecimalFormatSymbols decimal = new DecimalFormatSymbols(locale);
        Currency currency = Currency.getInstance(locale);
        Global.setMoneySymbol(currency.getSymbol(locale));
        Global.setDecimalSep(String.valueOf(decimal.getDecimalSeparator()));
        
        
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
        LabelHashTables.setGlobalLabels("1");
        
        UserModel user = new UserModel();
        user.setAdminRights(true);
        Global.setCurrentUser(user);
                
    }
}
