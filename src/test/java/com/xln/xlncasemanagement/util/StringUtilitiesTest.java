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
     * @param inputPerson
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildUserNameWithUserName")
    public void testBuildUserNameWithUserName(UserModel inputPerson, String expResult) {
        System.out.println("buildUserNameWithUserName");
        assertEquals(expResult, StringUtilities.buildUserNameWithUserName(inputPerson));
    }

    private Object[] parametersForTestBuildUserNameWithUserName() {
        UserModel user1 = new UserModel();
        user1.setFirstName("John");
        user1.setMiddleInitial(null);
        user1.setLastName("Doe");
        user1.setUsername(null);
        
        UserModel user2 = new UserModel();
        user2.setFirstName("John");
        user2.setMiddleInitial("");
        user2.setLastName("Doe");
        user2.setUsername("john.doe");
        
        UserModel user3 = new UserModel();
        user3.setFirstName(null);
        user3.setMiddleInitial(null);
        user3.setLastName("Doe");
        user3.setUsername("");
        
        UserModel user4 = new UserModel();
        user4.setFirstName("John");
        user4.setMiddleInitial("M");
        user4.setLastName("Doe");
        user4.setUsername("john.doe");
        
        UserModel user5 = new UserModel();
        user5.setFirstName("");
        user5.setMiddleInitial("");
        user5.setLastName("Doe");
        user5.setUsername("john.doe");
        
        return new Object[]{
            new Object[]{user1, "John Doe"},
            new Object[]{user2, "John Doe (john.doe)"},
            new Object[]{user3, "Doe"},
            new Object[]{user4, "John M. Doe (john.doe)"},
            new Object[]{user3, "Doe"}
        };
    }
    
    /**
     * Test of buildUsersName method, of class StringUtilities.
     * @param inputPerson
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildUsersName")
    public void testBuildUsersName(UserModel inputPerson, String expResult) {
        System.out.println("buildUsersName");
        assertEquals(expResult, StringUtilities.buildUsersName(inputPerson));
    }

    private Object[] parametersForTestBuildUsersName() {
        UserModel user1 = new UserModel();
        user1.setFirstName("John");
        user1.setMiddleInitial(null);
        user1.setLastName("Doe");
        user1.setUsername(null);
        
        UserModel user2 = new UserModel();
        user2.setFirstName("John");
        user2.setMiddleInitial("");
        user2.setLastName("Doe");
        user2.setUsername("john.doe");
        
        UserModel user3 = new UserModel();
        user3.setFirstName(null);
        user3.setMiddleInitial(null);
        user3.setLastName("Doe");
        user3.setUsername("");
        
        UserModel user4 = new UserModel();
        user4.setFirstName("John");
        user4.setMiddleInitial("M");
        user4.setLastName("Doe");
        user4.setUsername("john.doe");
        
        UserModel user5 = new UserModel();
        user5.setFirstName("");
        user5.setMiddleInitial("");
        user5.setLastName("Doe");
        user5.setUsername("john.doe");
        
        return new Object[]{
            new Object[]{user1, "John Doe"},
            new Object[]{user2, "John Doe"},
            new Object[]{user3, "Doe"},
            new Object[]{user4, "John M. Doe"},
            new Object[]{user3, "Doe"}
        };
    }
        
    /**
     * Test of buildName method, of class StringUtilities.
     * @param first
     * @param middle
     * @param last
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildName")
    public void testBuildName(String first, String middle, String last, String expResult) {
        System.out.println("buildName");
        assertEquals(expResult, StringUtilities.buildName(first, middle, last));
    }

    private Object[] parametersForTestBuildName() {
        return new Object[]{
            new Object[]{"John", null, "Doe", "John Doe"},
            new Object[]{"John", "", "Doe", "John Doe"},
            new Object[]{"John", " ", "Doe", "John Doe"},
            new Object[]{null, null, "Doe", "Doe"},
            new Object[]{"John", "M", "Doe", "John M. Doe"},
            new Object[]{"", "", "Doe", "Doe"}
        };
    }
    
    /**
     * Test of buildTableAddressBlock method, of class StringUtilities.
     * @param person
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestTableAddressBlock")
    public void testBuildTableAddressBlock(PartyModel person, String expResult) {
        System.out.println("buildTableAddressBlock");
        assertEquals(expResult, StringUtilities.buildTableAddressBlock(person));
    }

    private Object[] parametersForTestTableAddressBlock() {
        PartyModel test1 = new PartyModel();
        test1.setPrefix(null);
        test1.setFirstName("John");
        test1.setMiddleInitial(null);
        test1.setLastName("Doe");
        test1.setAddressOne(null);
        test1.setAddressTwo("123 High St");
        test1.setAddressThree("");
        test1.setCity("Columbus");
        test1.setState("OH");
        test1.setZip("43215");
        
        PartyModel test2 = new PartyModel();
        test2.setPrefix("");
        test2.setFirstName("John");
        test2.setMiddleInitial("");
        test2.setLastName("Doe");
        test2.setAddressOne("123 High St");
        test2.setAddressTwo(null);
        test2.setAddressThree("");
        test2.setCity("Columbus");
        test2.setState(null);
        test2.setZip("43215");
        
        PartyModel test3 = new PartyModel();
        test3.setPrefix("Mr.");
        test3.setFirstName("John");
        test3.setMiddleInitial(null);
        test3.setLastName("Doe");
        test3.setAddressOne("ATTN: Mrs. Doe");
        test3.setAddressTwo("123 High St");
        test3.setAddressThree("Apt 3");
        test3.setCity("Columbus");
        test3.setState("OH");
        test3.setZip("43215");
        
        PartyModel test4 = new PartyModel();
        test4.setPrefix(null);
        test4.setFirstName("John");
        test4.setMiddleInitial("M");
        test4.setLastName("Doe");
        test4.setAddressOne("");
        test4.setAddressTwo("");
        test4.setAddressThree("");
        test4.setCity("");
        test4.setState("OH");
        test4.setZip("");
        
        return new Object[]{
            new Object[]{test1, "123 High St, Columbus, OH 43215"},
            new Object[]{test2, "123 High St, Columbus 43215"},
            new Object[]{test3, "ATTN: Mrs. Doe, 123 High St, Apt 3, Columbus, OH 43215"},
            new Object[]{test4, "OH"}
        };
    }
    
    /**
     * Test of buildAddressBlockWithLineBreaks method, of class StringUtilities.
     * @param person
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildAddressBlockWithLineBreaks_CompanyModel")
    public void testBuildAddressBlockWithLineBreaks_CompanyModel(CompanyModel person, String expResult) {
        System.out.println("buildAddressBlockWithLineBreaks");
        assertEquals(expResult, StringUtilities.buildAddressBlockWithLineBreaks(person));
    }

    private Object[] parametersForTestBuildAddressBlockWithLineBreaks_CompanyModel() {
        CompanyModel test1 = new CompanyModel();
        test1.setName("XLN Systems");
        test1.setAddressOne(null);
        test1.setAddressTwo("123 High St");
        test1.setAddressThree("");
        test1.setCity("Columbus");
        test1.setState("OH");
        test1.setZip("43215");
        
        CompanyModel test2 = new CompanyModel();
        test2.setName(null);
        test2.setAddressOne("123 High St");
        test2.setAddressTwo(null);
        test2.setAddressThree("");
        test2.setCity("Columbus");
        test2.setState(null);
        test2.setZip("43215");
        
        CompanyModel test3 = new CompanyModel();
        test3.setName("XLN Systems");
        test3.setAddressOne("ATTN: Mrs. Doe");
        test3.setAddressTwo("123 High St");
        test3.setAddressThree("Apt 3");
        test3.setCity("Columbus");
        test3.setState("OH");
        test3.setZip("43215");
        
        CompanyModel test4 = new CompanyModel();
        test4.setName("");
        test4.setAddressOne("");
        test4.setAddressTwo("");
        test4.setAddressThree("");
        test4.setCity("");
        test4.setState("OH");
        test4.setZip("");
        
        return new Object[]{
            new Object[]{test1, "XLN Systems" + System.lineSeparator() + "123 High St" 
                + System.lineSeparator() + "Columbus, OH 43215"},
            new Object[]{test2, "123 High St" + System.lineSeparator() + "Columbus 43215"},
            new Object[]{test3, "XLN Systems" + System.lineSeparator() 
                + "ATTN: Mrs. Doe" + System.lineSeparator() 
                + "123 High St" + System.lineSeparator() + "Apt 3" 
                + System.lineSeparator() + "Columbus, OH 43215"},
            new Object[]{test4, "OH"}
        };
    }   
    
    /**
     * Test of buildAddressBlockWithLineBreaks method, of class StringUtilities.
     * @param person
     * @param expResult
     */
    @Test
    @Parameters(method = "parametersForTestBuildAddressBlockWithLineBreaks_PartyModel")
    public void testBuildAddressBlockWithLineBreaks_PartyModel(PartyModel person, String expResult) {
        System.out.println("buildAddressBlockWithLineBreaks");
        assertEquals(expResult, StringUtilities.buildAddressBlockWithLineBreaks(person));
    }
    
    private Object[] parametersForTestBuildAddressBlockWithLineBreaks_PartyModel() {
        PartyModel test1 = new PartyModel();
        test1.setPrefix(null);
        test1.setFirstName("John");
        test1.setMiddleInitial(null);
        test1.setLastName("Doe");
        test1.setAddressOne(null);
        test1.setAddressTwo("123 High St");
        test1.setAddressThree("");
        test1.setCity("Columbus");
        test1.setState("OH");
        test1.setZip("43215");
        
        PartyModel test2 = new PartyModel();
        test2.setPrefix("");
        test2.setFirstName("John");
        test2.setMiddleInitial("");
        test2.setLastName("Doe");
        test2.setAddressOne("123 High St");
        test2.setAddressTwo(null);
        test2.setAddressThree("");
        test2.setCity("Columbus");
        test2.setState(null);
        test2.setZip("43215");
        
        PartyModel test3 = new PartyModel();
        test3.setPrefix("Mr.");
        test3.setFirstName("John");
        test3.setMiddleInitial(null);
        test3.setLastName("Doe");
        test3.setAddressOne("ATTN: Mrs. Doe");
        test3.setAddressTwo("123 High St");
        test3.setAddressThree("Apt 3");
        test3.setCity("Columbus");
        test3.setState("OH");
        test3.setZip("43215");
        
        PartyModel test4 = new PartyModel();
        test4.setPrefix(null);
        test4.setFirstName("John");
        test4.setMiddleInitial("M");
        test4.setLastName("Doe");
        test4.setAddressOne("");
        test4.setAddressTwo("");
        test4.setAddressThree("");
        test4.setCity("");
        test4.setState("OH");
        test4.setZip("");
        
        return new Object[]{
            new Object[]{test1, "John Doe" + System.lineSeparator() + "123 High St" 
                + System.lineSeparator() + "Columbus, OH 43215"},
            new Object[]{test2, "John Doe" + System.lineSeparator() + "123 High St" 
                + System.lineSeparator() + "Columbus 43215"},
            new Object[]{test3, "Mr. John Doe" + System.lineSeparator() + "ATTN: Mrs. Doe" 
                + System.lineSeparator() + "123 High St" + System.lineSeparator() 
                + "Apt 3" + System.lineSeparator() + "Columbus, OH 43215"},
            new Object[]{test4, "John M. Doe" + System.lineSeparator() + "OH"}
        };
    }    
}
