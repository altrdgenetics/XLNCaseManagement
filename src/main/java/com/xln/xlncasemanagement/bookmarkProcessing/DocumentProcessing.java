/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.bookmarkProcessing;

import com.jacob.com.Dispatch;
import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.MatterModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;

/**
 *
 * @author User
 */
public class DocumentProcessing {
 
    public static Dispatch processAWordLetter(Dispatch Document, PartyModel client, MatterModel matter) {
        
        //Build Out User Name
        String LoggedInUserName = StringUtilities.buildUserName(Global.getCurrentUser());
        String UserDefaultRate = NumberFormatService.formatMoney(Global.getCurrentUser().getDefaultRate());
        
        //Build Out Company Information
        String CompanyPhoneNumber = NumberFormatService.convertStringToPhoneNumber(Global.getCompanyInformation().getPhone());
        String CompanyFaxNumber = NumberFormatService.convertStringToPhoneNumber(Global.getCompanyInformation().getFax());
        String CompanyAddressBlock = StringUtilities.buildAddressBlockWithLineBreaks(Global.getCompanyInformation());
        
        
        //Build Out Client Information
        String ClientName = StringUtilities.buildPartyName(client);
        String ClientAddresseeName = StringUtilities.buildAddresseeName(client);
        String ClientAddressBlock = StringUtilities.buildAddressBlockWithLineBreaks(client);
        String ClientPhoneNumbers = NumberFormatService.convertStringToPhoneNumber(client.getPhoneOne());
        if (client.getPhoneTwo() != null && !ClientPhoneNumbers.trim().equals("")){
            ClientPhoneNumbers += ", ";
        }
        ClientPhoneNumbers += NumberFormatService.convertStringToPhoneNumber(client.getPhoneTwo());
                
        //Build Out Matter Information
        //
        // none yet
        
        
        //ProcessBookmarks
        for (int i = 0; i < Global.getGLOBAL_BOOKMARKLIMIT(); i++) {
            //COMPANY INFORMATION
            ProcessBookmark.process("COMPANYNAME" + (i == 0 ? "" : i), Global.getCompanyInformation().getName(), Document);
            ProcessBookmark.process("COMPANYADDRESSBLOCK" + (i == 0 ? "" : i), CompanyAddressBlock, Document);
            ProcessBookmark.process("COMPANYPHONENUMBER" + (i == 0 ? "" : i), CompanyPhoneNumber, Document);
            ProcessBookmark.process("COMPANYFAXNUMBER" + (i == 0 ? "" : i), CompanyFaxNumber, Document);
            ProcessBookmark.process("COMPANYEMAIL", Global.getCompanyInformation().getEmail(), Document);
            ProcessBookmark.process("COMPANYWEBSITE", Global.getCompanyInformation().getWebsite(), Document);


            //USER INFORMATION
            ProcessBookmark.process("USERNAME" + (i == 0 ? "" : i), LoggedInUserName, Document);
            ProcessBookmark.process("USERDEFAULTRATE" + (i == 0 ? "" : i), UserDefaultRate, Document);


            //CLIENT INFORMATION
            ProcessBookmark.process("CLIENTNAME" + (i == 0 ? "" : i), ClientName, Document);
            ProcessBookmark.process("CLIENTADDRESSEENAME" + (i == 0 ? "" : i), ClientAddresseeName, Document);
            ProcessBookmark.process("CLIENTADDRESSBLOCK" + (i == 0 ? "" : i), ClientAddressBlock, Document);
            ProcessBookmark.process("CLIENTPHONENUMBER", ClientPhoneNumbers, Document);
            ProcessBookmark.process("CLIENTEMAIL", Global.getCurrentClient().getEmail(), Document);


            //MATTER INFORMATION
            ProcessBookmark.process("OPENDATESHORT" + (i == 0 ? "" : i), Global.getMmddyyyy().format(matter.getOpenDate()), Document);
            ProcessBookmark.process("OPENDATELONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getOpenDate()), Document);
            ProcessBookmark.process("CLOSEDDATESHORT" + (i == 0 ? "" : i), Global.getMmddyyyy().format(matter.getCloseDate()), Document);
            ProcessBookmark.process("CLOSEDDATELONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getCloseDate()), Document);


            //VERSION DIFFERENCES
            switch (Global.getVersion()) {
                case "1": //Legal
                    ProcessBookmark.process("STATUTEOFLIMITATIONSHORT" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getWarranty()), Document);
                    ProcessBookmark.process("STATUTEOFLIMITATIONLONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getWarranty()), Document);
                    ProcessBookmark.process("JURISDICTION" + (i == 0 ? "" : i), matter.getMakeName(), Document);
                    ProcessBookmark.process("JUDGE" + (i == 0 ? "" : i), matter.getModelName(), Document);
                    ProcessBookmark.process("CASENUMBER" + (i == 0 ? "" : i), matter.getSerial(), Document);
                    break;
                case "2": //Computer Repair
                    ProcessBookmark.process("WARRANTYSHORT" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getWarranty()), Document);
                    ProcessBookmark.process("WARRANTYLONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getWarranty()), Document);
                    ProcessBookmark.process("MAKE" + (i == 0 ? "" : i), matter.getMakeName(), Document);
                    ProcessBookmark.process("MODEL" + (i == 0 ? "" : i), matter.getModelName(), Document);
                    ProcessBookmark.process("SERIALNUMBER" + (i == 0 ? "" : i), matter.getSerial(), Document);
                    break;
                default:
                    break;
            }
        }

        return Document;
    }

}
