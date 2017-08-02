/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.bookmarkProcessing;

import com.jacob.com.Dispatch;
import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;

/**
 *
 * @author User
 */
public class DocumentProcessing {
    
    public static Dispatch processAWordLetter(Dispatch Document, MatterModel matter) {
        
        //ProcessBookmarks
        for (int i = 0; i < Global.getGLOBAL_BOOKMARKLIMIT(); i++) {
            ProcessBookmark.process("OPENDATESHORT" + (i == 0 ? "" : i), Global.getMmddyyyy().format(matter.getOpenDate()), Document);
            ProcessBookmark.process("OPENDATELONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getOpenDate()), Document);
            ProcessBookmark.process("CLOSEDDATESHORT" + (i == 0 ? "" : i), Global.getMmddyyyy().format(matter.getCloseDate()), Document);
            ProcessBookmark.process("CLOSEDDATELONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getCloseDate()), Document);
        }

        return Document;
    }
    
}
