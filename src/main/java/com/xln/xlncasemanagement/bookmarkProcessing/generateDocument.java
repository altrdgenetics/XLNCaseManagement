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
import java.io.File;
import java.util.Date;

/**
 *
 * @author User
 */
public class generateDocument {
    
    public static String generateDocument(MatterModel matter) {
        File docPath = null;
        String saveDocName = null;
        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);
        if (eolWord != null) {
            //Setup Document
            docPath = new File(Global.getTempDirectory());
            docPath.mkdirs();

            saveDocName = String.valueOf(new Date().getTime()) + ".docx";

            saveDocName = saveDocName.replaceAll("[:\\\\/*?|<>]", "_");

            Dispatch document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open",
                    Global.getTempDirectory() + "SERBAnnualReport.docx").toDispatch();
            ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();

            document = documentProcessing.processAWordLetter(document, matter);

            Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
            String newFilePath = docPath + File.separator + saveDocName;
            Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
            JacobCOMBridge.setWordActive(false, false, eolWord);
        }

        return saveDocName;
    }
    
}
