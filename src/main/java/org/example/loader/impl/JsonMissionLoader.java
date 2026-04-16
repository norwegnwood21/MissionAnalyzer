package org.example.loader.impl;

import org.example.loader.parsers.JsonMissionParser;
import org.example.loader.spi.MissionLoader;
import org.example.loader.support.FileContentUtils;
import org.example.loader.support.MissionFormatDetector;
import org.example.model.Mission;

import java.io.File;
import java.io.IOException;

public class JsonMissionLoader implements MissionLoader {
    private final JsonMissionParser parser = new JsonMissionParser();
    public String getFormatName() { return "json"; }
    public boolean supports(File file) {
        if (MissionFormatDetector.detect(file).equals("json")) return true;
        return FileContentUtils.hasExtension(file, ".json");
    }
    public Mission load(File file) throws IOException { return parser.parse(file); }
}
