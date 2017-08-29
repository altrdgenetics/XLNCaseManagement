/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.config;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.model.sql.ConfigModel;
import com.xln.xlncasemanagement.sql.DBCInfo;
import com.xln.xlncasemanagement.sql.SQLConfig;
import com.xln.xlncasemanagement.util.DebugTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient; 
import org.apache.commons.net.ntp.TimeInfo;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author User
 */
public class ConfigFile {
    
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static void readConfigFile() {
        //TODO get Config File
        File configFile = new File("ini");

        if (configFile.exists()) {

            //Read File
            String encryptedKey = readConfigFile(configFile);
            if (!encryptedKey.equals("")) {
                //Decrypt File
                String decryptedKey = decrypt(encryptedKey, ALGORITHM);

                //Get Connection Information
                ConfigModel item = SQLConfig.getConfigByCompanyID(decryptedKey);

                if (item.getContractenddate().compareTo(internetTime()) < 0) {

                    //Company Info
                    Global.setVersion("");
                    Global.setCompanyID("");

                    //DB Connection
                    DBCInfo.setDBdriver(item.getDbtype());
                    DBCInfo.setDBurl(item.getDburl());
                    DBCInfo.setDBname(item.getDbname());
                    DBCInfo.setDBusername(item.getDbusername());
                    DBCInfo.setDBpassword(item.getDbpassword());
                } else {
                    //EXPIRED LICENSE
                    missingConfigDialog();
                }
            } else {
                //Empty File
                missingConfigDialog();
            }
        } else {
            //If no config file show dialog to copy it to location or exit application
            missingConfigDialog();
        }
    }

    private static void missingConfigDialog() {
        //Show Popup with option to select file Location and attempt to read again

    }

    public static String readConfigFile(File configFile) {
        BufferedReader br = null;
        FileReader fr = null;
        String companyKey = "";

        try {
            fr = new FileReader(configFile);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                companyKey += sCurrentLine;
            }

        } catch (IOException e) {
            DebugTools.Printout(e.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                DebugTools.Printout(ex.toString());
            }
        }
        return companyKey;
    }

    public static String decrypt(String encodedInitialData, String key) {
        String plainText = "";
        try {
            byte[] encryptedData = DatatypeConverter.parseBase64Binary(encodedInitialData);
            byte[] raw = DatatypeConverter.parseHexBinary(key);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] iv = Arrays.copyOfRange(encryptedData, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(encryptedData, 16, encryptedData.length);
            IvParameterSpec iv_specs = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv_specs);
            byte[] plainTextBytes = cipher.doFinal(cipherText);
            plainText = new String(plainTextBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
            DebugTools.Printout(ex.toString());
        }
        return plainText;
    }
    
    public static Date internetTime() {
        Date date = new Date(System.currentTimeMillis());
        
        try {
            String TIME_SERVER = "time-a.nist.gov";
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            date = new Date(returnTime);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConfigFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
    
}
