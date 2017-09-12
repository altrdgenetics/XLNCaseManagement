/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 *
 * @author Andrew
 */
public class AlertDialog {

    public static boolean StaticAlert(int type, String title, String header, String body) {
        Alert alert = null;
        Optional<ButtonType> result = null;
                
        switch (type) {
            case 1:
                alert = new Alert(AlertType.CONFIRMATION);
                break;
            case 2:
                alert = new Alert(AlertType.INFORMATION);
                break;
            case 3:
                alert = new Alert(AlertType.WARNING);
                break;
            case 4:
                alert = new Alert(AlertType.ERROR);
                break;
            default:
                alert = new Alert(AlertType.NONE);
                break;
        }

        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);
        result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    
    
}
