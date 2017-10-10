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
            new Object[]{"movie.avi", "video"},
            new Object[]{"test.apk", "apk"},
            new Object[]{"nothing.piz", "unknown"},
            new Object[]{null, "none"},
            new Object[]{"", "none"}
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
