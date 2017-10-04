/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;


import java.math.BigDecimal;
import junitparams.*;
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
     * @param expectedOutput
     */
    @Test
    @Parameters(method = "parametersForTestConvertStringToPhoneNumber")
    public void testConvertStringToPhoneNumber(String input, String expectedOutput) {
        System.out.println("convertStringToPhoneNumber");
        assertEquals(expectedOutput, 
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
     * Test of convertToBigDecimal method, of class NumberFormatService.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestStripMoney")
    public void testStripMoney(String input, BigDecimal expResult) {
        System.out.println("stripMoney");
        assertEquals(expResult, NumberFormatService.convertToBigDecimal(input));
    }

    private Object[] parametersForTestStripMoney() {
        return new Object[]{
            new Object[]{"$203,203.23", new BigDecimal("203203.23")},
            new Object[]{"$203.20", new BigDecimal("203.20")},
            new Object[]{"$203,203", new BigDecimal("203203")}
        };
    }
    
}
