/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import com.xln.xlncasemanagement.Global;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class FileUtilities {

    public static byte[] companyLogoFileToBytes(File imageFile) {
        try {
            Image image = new Image(new FileInputStream(imageFile), 200, 200, true, true);
            
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            try (ByteArrayOutputStream s = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", s);
                return s.toByteArray();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static byte[] fileToBytes(File file) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException ex) {
            DebugTools.Printout(ex.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    DebugTools.Printout(ex.getMessage());
                }
            }
        }
        return bytesArray;
    }

    public static String generateFileCheckSum(File inputFile) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            FileInputStream fis = new FileInputStream(inputFile);
            byte[] dataBytes = new byte[1024];
            
            int nread = 0;
            
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            
            byte[] mdbytes = md.digest();
            
            //convert the byte to hex format
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public static String generateFileCheckSum(InputStream fis) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] dataBytes = new byte[1024];
            
            int nread = 0;
            
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            
            byte[] mdbytes = md.digest();
            
            //convert the byte to hex format
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public static boolean compareCheckSum(byte[] dbfile, String dbChecksum) {
        String checkSumFromDBImage = FileUtilities.generateFileCheckSum(new ByteArrayInputStream(dbfile));

        if (dbChecksum.equals(checkSumFromDBImage)) {
            Image img = new Image(new ByteArrayInputStream(dbfile));

            Global.getCompanyInformation().setLogo(img);
            Global.getCompanyInformation().setLogoSH1(dbChecksum);
            return true;
        }
        return false;
    }
    
    public static File generateFileFromBlobData(InputStream is, String fileName, String checkSum) {
        File tempFile = null;
        String tempDirectory = System.getProperty("java.io.tmpdir");
        OutputStream outputStream = null;

        try {
            // write the inputStream to a FileOutputStream
            tempFile = new File(tempDirectory + fileName);
            outputStream = new FileOutputStream(tempFile);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
                        
        } catch (IOException ex) {
            DebugTools.Printout(ex.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    DebugTools.Printout(ex.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException ex) {
                    DebugTools.Printout(ex.getMessage());
                }
            }
        }
        return tempFile;
    }
    
}
