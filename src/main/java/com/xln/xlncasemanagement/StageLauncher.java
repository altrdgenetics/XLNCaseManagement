/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement;

import com.xln.xlncasemanagement.model.sql.*;
import com.xln.xlncasemanagement.sceneController.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author User
 */
public class StageLauncher {

    public void loginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginStage.fxml"));
            Global.setRoot((Parent) loader.load());

            Global.getLoginStage().initStyle(StageStyle.TRANSPARENT);
            Global.getLoginStage().getIcons().add(Global.getApplicationLogo());
            Scene scene = new Scene(Global.getRoot());
            Global.getLoginStage().setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            Global.getRoot().setStyle(
                      "-fx-border-insets: 10;"
                    + "-fx-background-insets: 10;"
                    + "-fx-border-color: gray;"
                    + "-fx-border-style: solid;"
                    + "-fx-border-width: 0.25;"
                    + "-fx-effect: dropshadow(gaussian, rgba(100, 100, 100, 0.5), 20, 0.25, 0, 0);"
            );

            LoginStageController controller = loader.getController();
            controller.setActive(Global.getLoginStage());
            Global.getLoginStage().show();
            
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mainStage() {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainStage.fxml"));
            Global.setRoot((Parent) loader.load());
            
            stage.initStyle(StageStyle.DECORATED);
            stage.getIcons().add(Global.getApplicationLogo());
            Scene scene = new Scene(Global.getRoot());
            stage.setScene(scene);
                     
            MainStageController controller = loader.getController();
            Global.setMainStageController(controller);
            controller.setActive(stage);
            
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void docketingIncomingScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DocketingIncomingScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DocketingIncomingSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void docketingOutgoingScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DocketingOutgoingScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DocketingOutgoingSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detailedPartyAddEditScene(Stage stagePassed, boolean maintenanceMode, PartyModel itemPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedPartyScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedPartySceneController controller = loader.getController();
            controller.setActive(stage, maintenanceMode, itemPassed);

            stage.setResizable(false);
            stage.sizeToScene();
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detailedExpenseAddEditScene(Stage stagePassed, ExpenseModel itemPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedExpenseScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedExpenseSceneController controller = loader.getController();
            controller.setActive(stage, itemPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void detailedActivityAddEditScene(Stage stagePassed, ActivityModel itemPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DetailedActivityScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            DetailedActivitySceneController controller = loader.getController();
            controller.setActive(stage, itemPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void partySearchScene(Stage stagePassed, boolean maintenanceMode, boolean newMatter) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PartySearchScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            PartySearchSceneController controller = loader.getController();
            controller.setActive(stage, maintenanceMode, newMatter);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceCompanyScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceCompanyScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceCompanySceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceActivityTypeScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceActivityTypeScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceActivityTypeSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceActivityTypeAddEditScene(Stage stagePassed, ActivityTypeModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceActivityTypeAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceActivityTypeAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceExpenseTypeScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceExpenseTypeScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceExpenseTypeSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceExpenseTypeAddEditScene(Stage stagePassed, ExpenseTypeModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceExpenseTypeAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceExpenseTypeAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public MatterTypeModel MaintenanceMatterTypeScene(Stage stagePassed, boolean maintenanceModePassed) {
        Stage stage = new Stage();
        MatterTypeModel matterType = null;  
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceMatterTypeScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceMatterTypeSceneController controller = loader.getController();
            controller.setActive(stage, maintenanceModePassed);

            stage.showAndWait();
            matterType = controller.getMatterType();
            
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matterType; 
    }
    
    public void MaintenanceMatterTypeAddEditScene(Stage stagePassed, MatterTypeModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceMatterTypeAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceMatterTypeAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenancePartyNamePrefixScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenancePartyNamePrefixScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenancePartyNamePrefixSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenancePartyNamePrefixAddEditScene(Stage stagePassed, PartyNamePrefixModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenancePartyNamePrefixAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenancePartyNamePrefixAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenancePartyRelationTypeScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenancePartyRelationTypeScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenancePartyRelationTypeSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenancePartyRelationTypeAddEditScene(Stage stagePassed, PartyRelationTypeModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenancePartyRelationTypeAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenancePartyRelationTypeAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void NewMatterCaseTypeSelectionScene(Stage stagePassed, PartyModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/NewMatterCaseTypeSelectionScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            NewMatterCaseTypeSelectionSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CaseRelationSelectionScene(Stage stagePassed, PartyModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CaseRelationSelectionScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            CaseRelationSelectionSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceUserScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceUserScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceUserSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceUserAddEditScene(Stage stagePassed, UserModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceUserAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceUserAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ReportScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ReportSelectionScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            ReportSelectionSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void retrieveFileLoadingScene(Stage stagePassed, String type, int id) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LoadingFileScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            
            Bounds boundsInScreen = null;
            if (type.equals("Expense")) {
                boundsInScreen = Global
                        .getMainStageController()
                        .getExpensesScene()
                        .localToScreen(Global
                                .getMainStageController()
                                .getExpensesScene()
                                .getBoundsInLocal()
                        );
            } else if (type.equals("Activity")) {
                boundsInScreen = Global
                        .getMainStageController()
                        .getActivityScene()
                        .localToScreen(Global
                                .getMainStageController()
                                .getActivityScene()
                                .getBoundsInLocal()
                        );
            }

            if (boundsInScreen != null) {
                double height = boundsInScreen.getHeight();
                double width = boundsInScreen.getWidth();
                double centerXPosition = boundsInScreen.getMaxX() - width;
                double centerYPosition = boundsInScreen.getMaxY() - height;
                stage.setHeight(height);
                stage.setWidth(width);
                stage.setX(centerXPosition);
                stage.setY(centerYPosition);
            }

            stage.setScene(scene);
            Global.getMainStageController().disableEverythingForLoading(true);
            stage.show();

            LoadingFileSceneController controller = loader.getController();
            controller.getFile(stage, type, id);
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public MakeModel MaintenanceMakeScene(Stage stagePassed, boolean maintenaceModePassed) {
        MakeModel make = null;        
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceMakeScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceMakeSceneController controller = loader.getController();
            controller.setActive(stage, maintenaceModePassed);

            stage.showAndWait();
            make = controller.getSelectedMake();
            
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return make;        
    }
    
    public void MaintenanceMakeAddEditScene(Stage stagePassed, MakeModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceMakeAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceMakeAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ModelModel MaintenanceModelScene(Stage stagePassed, boolean maintenaceModePassed, int makePassed) {
        ModelModel model = null;
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceModelScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceModelSceneController controller = loader.getController();
            controller.setActive(stage, maintenaceModePassed, makePassed);

            stage.showAndWait();
            model = controller.getSelectedModel();
            
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
    
    public void MaintenanceModelAddEditScene(Stage stagePassed, ModelModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceModelAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceModelAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void LetterSelectionScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LetterSelectionScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            LetterSelectionSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceTemplateScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceTemplateScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceTemplateSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceTemplateAddEditScene(Stage stagePassed, TemplateModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceTemplateAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceTemplateAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceReportScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceReportScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceReportSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MaintenanceReportAddEditScene(Stage stagePassed, ReportModel objectPassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceReportAddEditScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceReportAddEditSceneController controller = loader.getController();
            controller.setActive(stage, objectPassed);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ReportParameterScene(Stage stagePassed, int reportID) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MaintenanceReportParametersScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            MaintenanceReportParametersSceneController controller = loader.getController();
            controller.setActive(stage, reportID);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void BillingScene(Stage stagePassed, boolean billing) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/BillingScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            BillingSceneController controller = loader.getController();
            controller.setActive(stage, billing);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HashMap ReportParamTwoDatesScene(Stage stagePassed, HashMap hashMap) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ReportParamTwoDatesScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            ReportParamTwoDatesSceneController controller = loader.getController();
            controller.setActive(stage, hashMap);

            stage.showAndWait();
            hashMap = controller.getHash();
            
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashMap;
    }
    
    public void PasswordResetScene(Stage stagePassed) {
        Stage stage = new Stage();
        try { 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PasswordResetScene.fxml"));
            Scene scene = new Scene(loader.load());
            stage.getIcons().add(Global.getApplicationLogo());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(stagePassed);
            stage.setScene(scene);

            PasswordResetSceneController controller = loader.getController();
            controller.setActive(stage);

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StageLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
