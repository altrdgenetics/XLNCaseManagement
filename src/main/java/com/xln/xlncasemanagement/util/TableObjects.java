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

    public static HBox fileIcon(String file) {
        HBox box = new HBox();

        ImageView imageview = new ImageView();
        imageview.setFitHeight(20);
        imageview.setFitWidth(20);
        imageview.setImage(new Image(TableObjects.class.getResourceAsStream("/fileIcon/" + fileIconType(file) + ".png")));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(imageview);
        return box;
    }
    
    private static String fileIconType(String file) {
        String icon = "unknown";
        
        if (file == null){
            icon = "none";
        } else if (file.toLowerCase().endsWith(".apk")) {
            icon = "apk";
        } else if (file.toLowerCase().endsWith(".mp3") || 
                file.toLowerCase().endsWith(".wav")){
            icon = "audio";
        } else if (file.toLowerCase().endsWith(".doc") || 
                file.toLowerCase().endsWith(".docx") || 
                file.toLowerCase().endsWith(".rtf") || 
                file.toLowerCase().endsWith(".txt")){
            icon = "doc";
        } else if (file.toLowerCase().endsWith(".ics")) {
            icon = "ics";
        } else if (file.toLowerCase().endsWith(".jpg") || 
                file.toLowerCase().endsWith(".jpeg") ||
                file.toLowerCase().endsWith(".png") ||
                file.toLowerCase().endsWith(".eps") ||
                file.toLowerCase().endsWith(".psd") ||
                file.toLowerCase().endsWith(".ai") ||
                file.toLowerCase().endsWith(".gif")){
            icon = "image";
        } else if (file.toLowerCase().endsWith(".lnk")){
            icon = "link";
        } else if (file.toLowerCase().endsWith(".pdf")){
            icon = "pdf";
        } else if (file.toLowerCase().endsWith(".ppt") ||
                file.toLowerCase().endsWith(".pptx")){
            icon = "ppt";
        } else if (file.toLowerCase().endsWith(".rar")){
            icon = "rar";
        } else if (file.toLowerCase().endsWith(".wmv") ||
                file.toLowerCase().endsWith(".mp4") ||
                file.toLowerCase().endsWith(".avi") ||
                file.toLowerCase().endsWith(".mov") ||
                file.toLowerCase().endsWith(".flv") ||
                file.toLowerCase().endsWith(".swf") ||
                file.toLowerCase().endsWith(".mpg") ||
                file.toLowerCase().endsWith(".mpeg") ||
                file.toLowerCase().endsWith(".m4v") ||
                file.toLowerCase().endsWith(".mkv")){
            icon = "video";
        } else if (file.toLowerCase().endsWith(".xls") ||
                file.toLowerCase().endsWith(".xlsx")){
            icon = "xls";
        } else if (file.toLowerCase().endsWith(".zip")){
            icon = "zip";
        }
        return icon;
    }
    
    public static HBox websiteIcon(String file) {
        HBox box = new HBox();

        ImageView imageview = new ImageView();
        imageview.setFitHeight(20);
        imageview.setFitWidth(20);
        imageview.setImage(new Image(TableObjects.class.getResourceAsStream("/fileIcon/" + websiteColumn(file) + ".png")));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(imageview);
        return box;
    }
    
    private static String websiteColumn(String file) {
        String icon = "none";
        
        if (file != null){
            icon = "link";
        }
        return icon;
    }
    
}
