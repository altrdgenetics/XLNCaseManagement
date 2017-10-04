/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import com.xln.xlncasemanagement.Global;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author User
 */
public class NumberFormatService {
    
    public static String convertStringToPhoneNumber(String number) {
        String formattedNumber = "";

        if (number != null) {
            number = number.replaceAll("[^0-9]", "");
            
            if (number.length() >= 10) {
                formattedNumber += "(" + number.substring(0, 3) + ") ";
                formattedNumber += number.substring(3, 6) + "-";
                formattedNumber += number.substring(6, 10);

                if (number.length() > 10) {
                    formattedNumber += " x" + number.substring(10);
                }
            } else {
                formattedNumber = number;
            }
        }
        return formattedNumber.trim();
    }

    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }
        
    public static String formatMoney(BigDecimal amount){
        NumberFormat currencyFormatter = NumberFormat
                .getCurrencyInstance(Locale.getDefault().equals(Locale.US) 
                        ? Locale.CANADA : Locale.getDefault());
        return currencyFormatter.format(amount);
    }
    
    public static BigDecimal convertToBigDecimal(String amount){  
        String seperatorsRemoved = amount.replaceAll(String.valueOf(DecimalFormatSymbols.getInstance().getGroupingSeparator()), "");
        String currencyRemoved = seperatorsRemoved.replaceAll("\\p{Sc}", "");
        
        return new BigDecimal(currencyRemoved);
    }
    
    public static UnaryOperator<TextFormatter.Change> moneyMaskFormatter() {
        Pattern moneyFormatPattern = Pattern.compile(Global.getMoneyRegex());
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (moneyFormatPattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        return filter;
    }
    
    public static UnaryOperator<TextFormatter.Change> durationMaskFormatter() {
        Pattern durationPattern = Pattern.compile(Global.getDurationRegex());
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (durationPattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };
        return filter;
    }
    
    public static String convertLongToTime(long millis) {
        String duration = String.format("%02dhr %02dmin %02dsec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        if (TimeUnit.MILLISECONDS.toHours(millis) == 0) {
            String[] split = duration.split("hr");
            duration = split[1].trim();
        }
        return duration.trim();
    }
    
}
