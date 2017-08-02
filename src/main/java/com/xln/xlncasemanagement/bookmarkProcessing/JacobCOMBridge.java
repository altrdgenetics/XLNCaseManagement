/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xln.xlncasemanagement.bookmarkProcessing;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;
import com.xln.xlncasemanagement.Global;
import com.xln.xlncasemanagement.util.FileUtilities;
import java.io.File;

/**
 *
 * @author Andrew
 */
public class JacobCOMBridge {

    public static ActiveXComponent setWordActive(boolean active, boolean visible, ActiveXComponent eolWord) {
        final String libFile = "amd64".equals(System.getProperty("os.arch")) ? "jacob-1.18-x64.dll" : "jacob-1.18-x86.dll";
        String dll = "";
        if (Global.getJACOBDLL_PATH().equals("")) {
            dll = FileUtilities
                    .generateFilePathFromInputStream(
                            JacobCOMBridge.class.getResourceAsStream("/jacob/" + libFile),
                            libFile
                    );
            Global.setJACOBDLL_PATH(dll);
        } else {
            File jacobFile = new File(Global.getJACOBDLL_PATH());

            if (jacobFile.exists()) {
                dll = Global.getJACOBDLL_PATH();
            } else {
                dll = FileUtilities
                        .generateFilePathFromInputStream(
                                JacobCOMBridge.class.getResourceAsStream("/jacob/" + libFile),
                                libFile
                        );
                Global.setJACOBDLL_PATH(dll);
            }
        }

        if (loadLibrary(dll)) {
            if (active) {
                if (eolWord == null) {
                    eolWord = new ActiveXComponent("Word.Application");
                }
                eolWord.setProperty("Visible", visible);
            } else {
                if (eolWord != null) {
                    eolWord.invoke("Quit", new Variant[0]);
                    eolWord.safeRelease();
                }
                eolWord = null;
            }
        }
        return eolWord;
    }

    private static boolean loadLibrary(final String dllPath) {
        try {
            System.setProperty(LibraryLoader.JACOB_DLL_PATH, dllPath);
            LibraryLoader.loadJacobLibrary();
            return true;
        } catch (Exception ex) {
            System.err.println("Missing DLL: " + dllPath);
            return false;
        }
    }

}
