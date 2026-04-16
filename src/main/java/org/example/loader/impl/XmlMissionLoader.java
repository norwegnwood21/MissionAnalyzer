package org.example.loader.impl;

import org.example.loader.parsers.XmlMissionParser;
import org.example.loader.spi.MissionLoader;
import org.example.loader.support.FileContentUtils;
import org.example.loader.support.MissionFormatDetector;
import org.example.model.Mission;

import java.io.File;
import java.io.IOException;

public class XmlMissionLoader implements MissionLoader {
    private final XmlMissionParser parser = new XmlMissionParser();
    public String getFormatName() { return "xml"; }
    public boolean supports(File file) {
        if (MissionFormatDetector.detect(file).equals("xml")) return true;
        return FileContentUtils.hasExtension(file, ".xml");
    }
    public Mission load(File file) throws IOException { return parser.parse(file); }
}
