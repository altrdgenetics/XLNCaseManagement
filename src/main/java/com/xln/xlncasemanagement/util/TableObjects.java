/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author User
 */
public class TableObjects {
    
    public static Button viewButton() {
        Button viewButton = new Button();
        viewButton.setText("View");
        return viewButton;
    }

    public static HBox fileIcon() {
        HBox box = new HBox();

        ImageView imageview = new ImageView();
        imageview.setFitHeight(20);
        imageview.setFitWidth(20);
        imageview.setImage(new Image(TableObjects.class.getResourceAsStream("/fileIcon/doc.png")));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(imageview);
        return box;
    }
    
}
