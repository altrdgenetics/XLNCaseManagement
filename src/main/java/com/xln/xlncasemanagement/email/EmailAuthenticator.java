/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.email;

import com.xln.xlncasemanagement.model.sql.UserModel;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Andrew
 */
public class EmailAuthenticator {

    /**
     * This method returns the password authentication and uses the username and
     * password from the SystemEmailModel
     *
     * @param account SystemEmailModel (requires username & password)
     * @return the javax.mail.Authenticator
     */
    public static javax.mail.Authenticator setEmailAuthenticator(UserModel account) {
        javax.mail.Authenticator auth = new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getEmailUsername(), account.getEmailPassword());
            }
        };
        return auth;
    }

}
