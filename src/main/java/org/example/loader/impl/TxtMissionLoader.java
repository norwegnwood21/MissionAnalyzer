package org.example.loader.impl;

import org.example.loader.parsers.TxtMissionParser;
import org.example.loader.spi.MissionLoader;
import org.example.loader.support.FileContentUtils;
import org.example.loader.support.MissionFormatDetector;
import org.example.model.Mission;

import java.io.File;
import java.io.IOException;

public class TxtMissionLoader implements MissionLoader {
    private final TxtMissionParser parser = new TxtMissionParser();
    public String getFormatName() { return "txt"; }
    public boolean supports(File file) {
        if (MissionFormatDetector.detect(file).equals("txt")) return true;
        return FileContentUtils.hasExtension(file, ".txt");
    }
    public Mission load(File file) throws IOException { return parser.parse(file); }
}
