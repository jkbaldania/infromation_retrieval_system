package com.v1.irs.irhandler;

import java.io.File;
import java.io.FileFilter;

public class HelperFileFilter implements FileFilter {

    @Override
    public boolean accept(File filePath) {
        if (filePath.getName().toLowerCase().endsWith(".txt") || filePath.getName().toLowerCase().endsWith(".html")
                || filePath.getName().toLowerCase().endsWith(".htm") || filePath.getName().toLowerCase().endsWith(".xhtml")) {
            return true;
        }
        return false;
    }
}
