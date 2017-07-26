/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.UserModel;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Andrew
 */
public class StringUtilities {

    // adapted from post by Phil Haack and modified to match better
    private final static String tagStart
            = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
    private final static String tagEnd
            = "\\</\\w+\\>";
    private final static String tagSelfClosing
            = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
    private final static String htmlEntity
            = "&[a-zA-Z][a-zA-Z0-9]+;";
    private final static Pattern htmlPattern = Pattern.compile(
            "(" + tagStart + ".*" + tagEnd + ")|(" + tagSelfClosing + ")|(" + htmlEntity + ")",
            Pattern.DOTALL);

    /**
     * Will return true if s contains HTML markup tags or entities.
     *
     * @param s String to test
     * @return true if string contains HTML
     */
    public static boolean isHtml(String s) {
        boolean ret=false;
        if (s != null) {
            ret=htmlPattern.matcher(s).find();
        }
        return ret;
    }

    /**
     * Builds out the filename for the attachment. order is...
     * [emailID_number_base.extension]
     *
     * @param filename String
     * @param emailID Integer
     * @param attachmentNumber Integer
     * @return
     */
    public static String properAttachmentName(String filename, int emailID, int attachmentNumber) {
        String base = FilenameUtils.removeExtension(filename).replace("[^A-Za-z0-9]", "_");
        String extension = FilenameUtils.getExtension(filename);
        String number = String.valueOf(attachmentNumber);
        if (attachmentNumber < 10){
            number = "0" + number;
        }
        return emailID + "_" + number + "_" + base + "." + extension;
    }

    /**
     * Gets the current time in "MM/dd/yyyy HH:mm:ss a" Format
     *
     * @return String
     */
    public static String currentTime(){
        return Global.getMmddyyyyhhmmssa().format(new Date());
    }
    
    public static String buildPartyName(PartyModel item) {
        String fullName = "";
        if (item.getPrefix() != null) {
            fullName = fullName.trim() + (item.getPrefix().equals("") ? "" : item.getPrefix().trim());
        }        
        if (item.getFirstName() != null) {
            fullName = fullName.trim() + (item.getFirstName().equals("") ? "" : " " + item.getFirstName().trim());
        }
        if (item.getMiddleInitial() != null) {
            fullName = fullName.trim() + (item.getMiddleInitial().equals("") ? "" : " " + (item.getMiddleInitial().trim().length() == 1 ? item.getMiddleInitial().trim() + "." : item.getMiddleInitial().trim()));
        }
        if (item.getLastName() != null) {
            fullName = fullName.trim() + (item.getLastName().equals("") ? "" : " " + item.getLastName().trim());
        }
        return fullName.trim();
    }
    
    public static String buildUserName(UserModel item) {
        String fullName = "";

        if (item.getFirstName() != null) {
            fullName = fullName.trim() + (item.getFirstName().equals("") ? "" : " " + item.getFirstName().trim());
        }
        if (item.getMiddleInitial() != null) {
            fullName = fullName.trim() + (item.getMiddleInitial().equals("") ? "" : " " + (item.getMiddleInitial().trim().length() == 1 ? item.getMiddleInitial().trim() + "." : item.getMiddleInitial().trim()));
        }
        if (item.getLastName() != null) {
            fullName = fullName.trim() + (item.getLastName().equals("") ? "" : " " + item.getLastName().trim());
        }
        if (item.getUsername() != null) {
            fullName = fullName.trim() + (item.getUsername().equals("") ? "" : " (" +item.getUsername().trim() + ")");
        }

        return fullName.trim();
    }

    public static String buildName(String first, String middle, String last) {
        String fullName = "";

        if (first != null) {
            fullName = fullName.trim() + (first.equals("") ? "" : " " + first.trim());
        }
        if (middle != null) {
            fullName = fullName.trim() + (middle.equals("") ? "" : " " + (middle.trim().length() == 1 ? middle.trim() + "." : middle.trim()));
        }
        if (last != null) {
            fullName = fullName.trim() + (last.equals("") ? "" : " " + last.trim());
        }

        return fullName.trim();
    }
    
    public static String buildTableAddressBlock(PartyModel item) {
        String addressLine = "";

        if (item.getAddressOne() != null) {
            addressLine += item.getAddressOne().equals("") ? "" : (item.getAddressOne());
        }
        if (item.getAddressTwo() != null) {
            addressLine += item.getAddressTwo().equals("") ? "" : (", " + item.getAddressTwo());
        }
        if (item.getAddressThree() != null) {
            addressLine += item.getAddressThree().equals("") ? "" : (", " + item.getAddressThree());
        }
        if (item.getCity() != null) {
            addressLine += item.getCity().equals("") ? "" : (", " + item.getCity());
        }
        if (item.getState() != null) {
            addressLine += item.getState().equals("") ? "" : (", " + item.getState());
        }
        if (item.getZip() != null) {
            addressLine += item.getZip().equals("") ? "" : (" " + item.getZip());
        }

        return addressLine.trim();
    }
}
