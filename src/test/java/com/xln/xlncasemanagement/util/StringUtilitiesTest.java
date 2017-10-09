/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import com.xln.xlncasemanagement.model.sql.CompanyModel;
import com.xln.xlncasemanagement.model.sql.PartyModel;
import com.xln.xlncasemanagement.model.sql.UserModel;
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
public class StringUtilitiesTest {
    

    /**
     * Test of isHtml method, of class StringUtilities.
     * @param input
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestIsHtml")
    public void testIsHtml(String input, boolean expResult) {
        System.out.println("isHtml");
        assertEquals(expResult, StringUtilities.isHtml(input));
    }

    private Object[] parametersForTestIsHtml() {
        return new Object[]{
            new Object[]{"", false},
            new Object[]{"Some Basic Text.", false},
            new Object[]{"<html>\n<body>\n\n<p>This text is normal.</p>"
                + "\n\n<p><i>This text is italic.</i></p>"
                + "\n\n</body>\n</html>", true}
        };
    }
        
    /**
     * Test of properAttachmentName method, of class StringUtilities.
     * @param filename
     * @param emailID
     * @param attachmentNumber
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestProperAttachmentName")
    public void testProperAttachmentName(String filename, int emailID, int attachmentNumber, String expResult) {
        System.out.println("properAttachmentName");
        assertEquals(expResult, 
                StringUtilities.properAttachmentName(filename, emailID, attachmentNumber));
    }

    private Object[] parametersForTestProperAttachmentName() {
        return new Object[]{
            new Object[]{"Attachment.pdf", 13, 1, "13_01_Attachment.pdf"}
        };
    }
    
    /**
     * Test of buildPartyName method, of class StringUtilities.
     * @param inputPerson
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildPartyName")
    public void testBuildPartyName(PartyModel inputPerson, String expResult) {
        System.out.println("buildPartyName");
        assertEquals(expResult, StringUtilities.buildPartyName(inputPerson));
    }

    private Object[] parametersForTestBuildPartyName() {
        PartyModel test1 = new PartyModel();
        test1.setPrefix(null);
        test1.setFirstName("John");
        test1.setMiddleInitial(null);
        test1.setLastName("Doe");
        
        PartyModel test2 = new PartyModel();
        test2.setPrefix("");
        test2.setFirstName("John");
        test2.setMiddleInitial("");
        test2.setLastName("Doe");
        
        PartyModel test3 = new PartyModel();
        test3.setPrefix("Mr.");
        test3.setFirstName("John");
        test3.setMiddleInitial(null);
        test3.setLastName("Doe");
        
        PartyModel test4 = new PartyModel();
        test4.setPrefix(null);
        test4.setFirstName("John");
        test4.setMiddleInitial("M");
        test4.setLastName("Doe");
        
        return new Object[]{
            new Object[]{test1, "John Doe"},
            new Object[]{test2, "John Doe"},
            new Object[]{test3, "Mr. John Doe"},
            new Object[]{test4, "John M. Doe"}
        };
    }
        
    /**
     * Test of buildAddresseeName method, of class StringUtilities.
     * @param inputPerson
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildAddresseeNamee")
    public void testBuildAddresseeName(PartyModel inputPerson, String expResult) {
        System.out.println("buildAddresseeName");
        assertEquals(expResult, StringUtilities.buildAddresseeName(inputPerson));
    }

    private Object[] parametersForTestBuildAddresseeNamee() {
        PartyModel test1 = new PartyModel();
        test1.setPrefix(null);
        test1.setFirstName("John");
        test1.setMiddleInitial(null);
        test1.setLastName("Doe");
        
        PartyModel test2 = new PartyModel();
        test2.setPrefix("");
        test2.setFirstName("John");
        test2.setMiddleInitial("");
        test2.setLastName("Doe");
        
        PartyModel test3 = new PartyModel();
        test3.setPrefix("Mr.");
        test3.setFirstName("John");
        test3.setMiddleInitial(null);
        test3.setLastName("Doe");
        
        PartyModel test4 = new PartyModel();
        test4.setPrefix(null);
        test4.setFirstName("John");
        test4.setMiddleInitial("M");
        test4.setLastName("Doe");
        
        return new Object[]{
            new Object[]{test1, "Doe"},
            new Object[]{test2, "Doe"},
            new Object[]{test3, "Mr. Doe"},
            new Object[]{test4, "Doe"}
        };
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Test of buildUserNameWithUserName method, of class StringUtilities.
     */
    @Test
    public void testBuildUserNameWithUserName() {
        System.out.println("buildUserNameWithUserName");
        UserModel item = null;
        String expResult = "";
        String result = StringUtilities.buildUserNameWithUserName(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Test of buildUsersName method, of class StringUtilities.
     */
    @Test
    public void testBuildUsersName() {
        System.out.println("buildUsersName");
        UserModel item = null;
        String expResult = "";
        String result = StringUtilities.buildUsersName(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Test of buildName method, of class StringUtilities.
     */
    @Test
    public void testBuildName() {
        System.out.println("buildName");
        String first = "";
        String middle = "";
        String last = "";
        String expResult = "";
        String result = StringUtilities.buildName(first, middle, last);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Test of buildTableAddressBlock method, of class StringUtilities.
     */
    @Test
    public void testBuildTableAddressBlock() {
        System.out.println("buildTableAddressBlock");
        PartyModel item = null;
        String expResult = "";
        String result = StringUtilities.buildTableAddressBlock(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Test of buildAddressBlockWithLineBreaks method, of class StringUtilities.
     */
    @Test
    public void testBuildAddressBlockWithLineBreaks_CompanyModel() {
        System.out.println("buildAddressBlockWithLineBreaks");
        CompanyModel item = null;
        String expResult = "";
        String result = StringUtilities.buildAddressBlockWithLineBreaks(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Test of buildAddressBlockWithLineBreaks method, of class StringUtilities.
     */
    @Test
    public void testBuildAddressBlockWithLineBreaks_PartyModel() {
        System.out.println("buildAddressBlockWithLineBreaks");
        PartyModel item = null;
        String expResult = "";
        String result = StringUtilities.buildAddressBlockWithLineBreaks(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
