/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class NumberFormatServiceTest {

    /**
     * Test of convertToBigDecimal method, of class NumberFormatService.
     */
    @Test
    public void testStripMoney() {
        System.out.println("stripMoney");
        String amount = "$203,203.23";
        BigDecimal expResult = new BigDecimal("203203.23");
        BigDecimal result = NumberFormatService.convertToBigDecimal(amount);
        assertEquals(expResult, result);
    }
    
}
