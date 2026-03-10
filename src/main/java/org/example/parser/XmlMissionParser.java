package org.example.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.model.Mission;

import java.io.File;
import java.io.IOException;

public class XmlMissionParser {

    private final XmlMapper xmlMapper;

    public XmlMissionParser() {
        this.xmlMapper = new XmlMapper();
    }

    public Mission parseFile(File file) throws IOException {
        Mission mission = xmlMapper.readValue(file, Mission.class);
        return mission;
    }

    public boolean canParse(File file) {
        return file.getName().toLowerCase().endsWith(".xml");
    }
}