/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.model.sql;

/**
 *
 * @author User
 */
public class FileModel {
    
    private int tableRelationID;
    private String fileName;
    private byte[] fileBlob;
    private String fileBlobHash;

    public int getTableRelationID() {
        return tableRelationID;
    }

    public void setTableRelationID(int tableRelationID) {
        this.tableRelationID = tableRelationID;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(byte[] fileBlob) {
        this.fileBlob = fileBlob;
    }

    public String getFileBlobHash() {
        return fileBlobHash;
    }

    public void setFileBlobHash(String fileBlobHash) {
        this.fileBlobHash = fileBlobHash;
    }
    
    
}
