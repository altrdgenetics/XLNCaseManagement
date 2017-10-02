/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.sql;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class UserModel {
    
    private int id;
    private boolean active;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String username;
    private String password;
    private long passwordSalt;
    private boolean passwordReset;
    private Timestamp lastLoginDateTime;
    private String lastLoginPCName;
    private String lastLoginIP;
    private int lastMatterID;
    private boolean activeLogin;
    private boolean adminRights;
    private BigDecimal defaultRate;
    private boolean accountLocked;
    private String emailUsername;
    private String emailPassword;
    private long emailPasswordSalt;
    private String incomingURL;
    private String incomingProtocol;
    private String incomingPort;
    private String incomingFolder;
    private String outgoingURL;
    private String outgoingProtocol;
    private String outgoingPort;
    private String outgoingFolder;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(long passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public boolean isPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    public Timestamp getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(Timestamp lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public String getLastLoginPCName() {
        return lastLoginPCName;
    }

    public void setLastLoginPCName(String lastLoginPCName) {
        this.lastLoginPCName = lastLoginPCName;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public int getLastMatterID() {
        return lastMatterID;
    }

    public void setLastMatterID(int lastMatterID) {
        this.lastMatterID = lastMatterID;
    }

    public boolean isActiveLogin() {
        return activeLogin;
    }

    public void setActiveLogin(boolean activeLogin) {
        this.activeLogin = activeLogin;
    }

    public boolean isAdminRights() {
        return adminRights;
    }

    public void setAdminRights(boolean adminRights) {
        this.adminRights = adminRights;
    }

    public BigDecimal getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(BigDecimal defaultRate) {
        this.defaultRate = defaultRate;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public long getEmailPasswordSalt() {
        return emailPasswordSalt;
    }

    public void setEmailPasswordSalt(long emailPasswordSalt) {
        this.emailPasswordSalt = emailPasswordSalt;
    }

    

    public String getIncomingURL() {
        return incomingURL;
    }

    public void setIncomingURL(String incomingURL) {
        this.incomingURL = incomingURL;
    }

    public String getIncomingProtocol() {
        return incomingProtocol;
    }

    public void setIncomingProtocol(String incomingProtocol) {
        this.incomingProtocol = incomingProtocol;
    }

    public String getIncomingPort() {
        return incomingPort;
    }

    public void setIncomingPort(String incomingPort) {
        this.incomingPort = incomingPort;
    }

    public String getIncomingFolder() {
        return incomingFolder;
    }

    public void setIncomingFolder(String incomingFolder) {
        this.incomingFolder = incomingFolder;
    }

    public String getOutgoingURL() {
        return outgoingURL;
    }

    public void setOutgoingURL(String outgoingURL) {
        this.outgoingURL = outgoingURL;
    }

    public String getOutgoingProtocol() {
        return outgoingProtocol;
    }

    public void setOutgoingProtocol(String outgoingProtocol) {
        this.outgoingProtocol = outgoingProtocol;
    }

    public String getOutgoingPort() {
        return outgoingPort;
    }

    public void setOutgoingPort(String outgoingPort) {
        this.outgoingPort = outgoingPort;
    }

    public String getOutgoingFolder() {
        return outgoingFolder;
    }

    public void setOutgoingFolder(String outgoingFolder) {
        this.outgoingFolder = outgoingFolder;
    }
    
    
    
}
