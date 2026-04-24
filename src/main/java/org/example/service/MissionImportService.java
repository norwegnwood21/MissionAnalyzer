package org.example.service;

import org.example.loader.DefaultMissionLoaderRegistryFactory;
import org.example.loader.MissionLoaderRegistry;
import org.example.loader.support.MissionFormatDetector;
import org.example.model.Mission;
import org.example.validator.MissionValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class MissionImportService {
    private final MissionLoaderRegistry loaderRegistry = DefaultMissionLoaderRegistryFactory.createDefault();
    private final MissionValidator validator = new MissionValidator();

    public ParsedMissionResult parseAndValidate(MultipartFile multipartFile) throws IOException {
        File temp = Files.createTempFile("mission-upload-", ".tmp").toFile();
        multipartFile.transferTo(temp);

        try {
            String format = MissionFormatDetector.detect(temp);

            if ("unknown".equals(format)) {
                throw new IllegalArgumentException("Формат файла не распознан");
            }

            Mission mission;
            try {
                mission = loaderRegistry.load(temp);
            } catch (IOException e) {
                throw new IllegalArgumentException("Не удалось прочитать или распарсить файл");
            }

            validator.validate(mission);

            return new ParsedMissionResult(mission, format);
        } finally {
            temp.delete();
        }
    }

    public record ParsedMissionResult(Mission mission, String format) {
    }
}