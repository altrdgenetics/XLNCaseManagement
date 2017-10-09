/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

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
public class TableObjectsTest extends TableObjects {
    
    /**
     * Test of fileIcon method, of class TableObjects.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestFileIconType")
    public void testFileIconType(String input, String expResult) {
        System.out.println("fileIconType");
        assertEquals(expResult, TableObjects.fileIconType(input));
    }
    
    private Object[] parametersForTestFileIconType() {
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
     * Test of websiteIcon method, of class TableObjects.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestWebsiteColumnIcon")
    public void testWebsiteColumnIcon(String input, String expResult) {
        System.out.println("websiteColumnIcon");
        assertEquals(expResult, TableObjects.websiteColumnIcon(input));
    }
    
    
    private Object[] parametersForTestWebsiteColumnIcon() {
        return new Object[]{
            new Object[]{null, "none"},
            new Object[]{"", "none"},
            new Object[]{"www.google.com", "link"}
        };
    }
}
