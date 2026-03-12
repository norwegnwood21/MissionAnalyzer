package org.example;

import java.io.File;
public class FileFormatDetector {
    public enum Format {
        JSON, XML, TEXT, UNKNOWN
    }


    public static Format detectFormat(File file) {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".json")) {
            return Format.JSON;
        } else if (fileName.endsWith(".xml")) {
            return Format.XML;
        } else if (fileName.endsWith(".txt")) {
            return Format.TEXT;
        }

       return Format.UNKNOWN;
    }


}