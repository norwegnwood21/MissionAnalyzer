package org.example.loader.impl;

import org.example.loader.parsers.TypeMissionParser;
import org.example.loader.spi.MissionLoader;
import org.example.loader.support.FileContentUtils;
import org.example.loader.support.MissionFormatDetector;
import org.example.model.Mission;

import java.io.File;
import java.io.IOException;

public class TypeMissionLoader implements MissionLoader {
    private final TypeMissionParser parser = new TypeMissionParser();
    public String getFormatName() { return "type"; }
    public boolean supports(File file) {
        if (MissionFormatDetector.detect(file).equals("type")) return true;
        return FileContentUtils.hasExtension(file, ".type");
    }
    public Mission load(File file) throws IOException { return parser.parse(file); }
}
