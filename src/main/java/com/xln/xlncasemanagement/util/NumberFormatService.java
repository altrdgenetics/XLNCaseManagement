/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

/**
 *
 * @author User
 */
public class NumberFormatService {
    
    public static String convertStringToPhoneNumber(String number) {
        String formattedNumber = "";

        if (number == null) {
            formattedNumber = "";
        } else if (number.length() >= 10) {
            formattedNumber += "(" + number.substring(0, 3) + ") ";
            formattedNumber += number.substring(3, 6) + "-";
            formattedNumber += number.substring(6, 10);

            if (number.length() > 10) {
                formattedNumber += " x" + number.substring(10);
            }
        } else {
            formattedNumber = number;
        }
        return formattedNumber.trim();
    }

    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }
    
}
