/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;


import java.math.BigDecimal;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;


/**
 *
 * @author User
 */
@RunWith(JUnitParamsRunner.class)
public class NumberFormatServiceTest {
    
    /**
     * Test of convertToBigDecimal method, of class NumberFormatService.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestConvertStringToPhoneNumber")
    public void testConvertStringToPhoneNumber(String input, String expResult) {
        System.out.println("convertStringToPhoneNumber");
        assertEquals(expResult, 
                NumberFormatService.convertStringToPhoneNumber(input)
        );
    }
    
    private Object[] parametersForTestConvertStringToPhoneNumber() {
        return new Object[]{
            new Object[]{"3256987412", "(325) 698-7412"},
            new Object[]{"(325) 698-7412", "(325) 698-7412"},
            new Object[]{"325698741", "325698741"},
            new Object[]{null, ""},
            new Object[]{"", ""},
            new Object[]{"32569874134", "(325) 698-7413 x4"}
        };
    }

    /**
     * Test of convertPhoneNumberToString method, of class NumberFormatService.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestConvertPhoneNumberToString")
    public void testConvertPhoneNumberToString(String input, String expResult) {
        System.out.println("convertPhoneNumberToString");
        assertEquals(expResult, 
                NumberFormatService.convertPhoneNumberToString(input)
        );
    }
    
    private Object[] parametersForTestConvertPhoneNumberToString() {
        return new Object[]{
            new Object[]{"(325) 698-7412", "3256987412"},
            new Object[]{"325698741", "325698741"},
            new Object[]{"", ""},
            new Object[]{"(325) 698-7413 x4", "32569874134"}
        };
    }
    
    /**
     * Test of formatMoney method, of class NumberFormatService.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestFormatMoney")
    public void testFormatMoney(BigDecimal input, String expResult) {
        System.out.println("formatMoney");
        assertEquals(expResult, NumberFormatService.formatMoney(input));
    }

    private Object[] parametersForTestFormatMoney() {
        return new Object[]{
            new Object[]{new BigDecimal("203203.23") ,"$203,203.23"},
            new Object[]{new BigDecimal("203.2") ,"$203.20"},
            new Object[]{new BigDecimal("203203") ,"$203,203.00"}
        };
    }
    
    /**
     * Test of convertToBigDecimal method, of class NumberFormatService.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestConvertToBigDecimal")
    public void testConvertToBigDecimal(String input, BigDecimal expResult) {
        System.out.println("stripMoney");
        assertEquals(expResult, NumberFormatService.convertToBigDecimal(input));
    }

    private Object[] parametersForTestConvertToBigDecimal() {
        return new Object[]{
            new Object[]{"$203,203.23", new BigDecimal("203203.23")},
            new Object[]{"$203.20", new BigDecimal("203.20")},
            new Object[]{"$203,203", new BigDecimal("203203")}
        };
    }


    /**
     * Test of convertLongToTime method, of class NumberFormatService.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestConvertLongToTime")
    public void testConvertLongToTime(long input, String expResult) {
        System.out.println("convertLongToTime");
        assertEquals(expResult, NumberFormatService.convertLongToTime(input));
    }
    
    private Object[] parametersForTestConvertLongToTime() {
        return new Object[]{
            new Object[]{5000L, "05sec"},
            new Object[]{90000L, "01min 30sec"},
            new Object[]{180000L, "03min 00sec"},
            new Object[]{18000000L, "05hr 00min 00sec"}
        };
    }
}
