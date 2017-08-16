/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.report;

import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.util.StringUtilities;
import java.sql.Date;
import java.util.HashMap;

/**
 *
 * @author User
 */
public class ReportHashMap {
    
    public static HashMap generateDefaultInformation(HashMap hash) {
        hash.put("current user", StringUtilities.buildUsersName(Global.getCurrentUser()));
        hash.put("mattertype", Global.getNewCaseType());
        hash.put("leadwording", Global.getLeadWording());
        return hash;
    }
    
    public static HashMap matterID(HashMap hash, int matterID) {
        hash.put("matterid", matterID);
        return hash;
    }
    
    public static HashMap clientID(HashMap hash, int clientID) {
        hash.put("clientid", clientID);
        return hash;
    }
    
    public static HashMap startDateEndDate(HashMap hash, Date startDate, Date endDate) {
        hash.put("startDate", startDate);
        hash.put("endDate", endDate);
        return hash;
    }
    
}
