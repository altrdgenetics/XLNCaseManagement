/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.bookmarkProcessing;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.TemplateModel;
import com.xln.xlncasemanagement.util.FileUtilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

/**
 *
 * @author User
 */
public class GenerateDocument {
    
    public static String generateDocument(TemplateModel template, PartyModel client, MatterModel matter) {
        String saveDocName = null;
        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);
        if (eolWord != null) {
            //Setup Document
            String templateFile = FileUtilities.generateFilePathFromInputStream(
                    new ByteArrayInputStream(template.getFileBlob()), template.getFileName()
            );
                        
            saveDocName = String.valueOf(new Date().getTime()) + "_" + template.getFileName();
            saveDocName = saveDocName.replaceAll("[:\\\\/*?|<>]", "_");

            //Run Bookmarks
            Dispatch document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open", templateFile).toDispatch();
            ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();

            document = DocumentProcessing.processAWordLetter(document, client ,matter);

            //Save Document
            Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
            String newFilePath = Global.getTempDirectory() + File.separator + saveDocName;
            Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
            JacobCOMBridge.setWordActive(false, false, eolWord);
        }
        return saveDocName;
    }
    
}
