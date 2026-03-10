package org.example.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.*;

import java.io.File;
import java.io.IOException;

public class JsonMissionParser {

    private final ObjectMapper objectMapper;

    public JsonMissionParser() {
        this.objectMapper = new ObjectMapper();
    }


    public Mission parseFile(File file) throws IOException {

        Mission mission = objectMapper.readValue(file, Mission.class);
        return mission;
    }


    public boolean canParse(File file) {
        return file.getName().toLowerCase().endsWith(".json");
    }
}
