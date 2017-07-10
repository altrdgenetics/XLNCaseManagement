/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.util;

import com.xln.xlncasemanagement.Global;

/**
 *
 * @author User
 */
public class DebugTools {
    
    public static void Printout(String text){
        if (Global.isDebug()){
            System.out.println(text);
        }
    }   
    
}
