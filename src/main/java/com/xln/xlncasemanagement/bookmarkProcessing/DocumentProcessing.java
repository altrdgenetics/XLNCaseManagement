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
import com.xln.xlncasemanagement.sql.SQLMatter;
import com.xln.xlncasemanagement.util.NumberFormatService;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author User
 */
public class DocumentProcessing {
 
    public static Dispatch processAWordLetter(Dispatch Document, PartyModel client, MatterModel matter) {
        //Build Out User Name
        String LoggedInUserName = StringUtilities.buildUsersName(Global.getCurrentUser());
        String UserDefaultRate = Global.getCurrentUser().getDefaultRate() == null ? "" :
                NumberFormatService.formatMoney(Global.getCurrentUser().getDefaultRate());
        
        
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
        HashMap billables = SQLMatter.getSummaryByMatterID(Global.getCurrentMatter().getId());
        BigDecimal budget = Global.getCurrentMatter().getBudget() == null 
                        ? BigDecimal.ZERO : Global.getCurrentMatter().getBudget();
        BigDecimal total = NumberFormatService.convertToBigDecimal(billables.get("totalTotalAmount").toString());
        BigDecimal balance = budget.subtract(total);

                
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
            ProcessBookmark.process("MATTERTYPE" + (i == 0 ? "" : i), matter.getMatterTypeName() == null ? "" : matter.getMatterTypeName(), Document);
            ProcessBookmark.process("OPENDATESHORT" + (i == 0 ? "" : i), Global.getMmddyyyy().format(matter.getOpenDate()), Document);
            ProcessBookmark.process("OPENDATELONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getOpenDate()), Document);
            ProcessBookmark.process("CLOSEDDATESHORT" + (i == 0 ? "" : i), Global.getMmddyyyy().format(matter.getCloseDate()), Document);
            ProcessBookmark.process("CLOSEDDATELONG" + (i == 0 ? "" : i), Global.getMMMMMdyyyy().format(matter.getCloseDate()), Document);
            ProcessBookmark.process("TOTALHOURS" + (i == 0 ? "" : i), billables.get("totalActivityHour").toString(), Document);
            ProcessBookmark.process("BILLEDHOURS" + (i == 0 ? "" : i), billables.get("billedActivityHour").toString(), Document);
            ProcessBookmark.process("UNBILLEDHOURS" + (i == 0 ? "" : i), billables.get("unBilledActivityHour").toString(), Document);
            ProcessBookmark.process("TOTALACTIVITYCOST" + (i == 0 ? "" : i), billables.get("totalActivityAmount").toString(), Document);
            ProcessBookmark.process("BILLEDACTIVITYCOST" + (i == 0 ? "" : i), billables.get("billedActivityAmount").toString(), Document);
            ProcessBookmark.process("UNBILLEDACTIVITYCOST" + (i == 0 ? "" : i), billables.get("unbilledActivityAmount").toString(), Document);
            ProcessBookmark.process("TOTALEXPENSES" + (i == 0 ? "" : i), billables.get("totalExpenseAmount").toString(), Document);
            ProcessBookmark.process("BILLEDEXPENSES" + (i == 0 ? "" : i), billables.get("billedExpenseAmount").toString(), Document);
            ProcessBookmark.process("UNBILLEDEXPENSES" + (i == 0 ? "" : i), billables.get("unBilledExpenseAmount").toString(), Document);
            ProcessBookmark.process("TOTALCOST" + (i == 0 ? "" : i), billables.get("totalTotalAmount").toString(), Document);
            ProcessBookmark.process("BALANCE" + (i == 0 ? "" : i), NumberFormatService.formatMoney(balance), Document);
            ProcessBookmark.process("NOTE", matter.getNote() == null ? "" : matter.getNote(), Document);


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
