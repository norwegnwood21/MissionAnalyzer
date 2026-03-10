package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileFormatDetector {
    public enum Format {
        JSON, XML, TEXT, UNKNOWN
    }


    public static Format detectFormat(File file) {
        String fileName = file.getName().toLowerCase();

        // пробуем определить по расширению
        if (fileName.endsWith(".json")) {
            return Format.JSON;
        } else if (fileName.endsWith(".xml")) {
            return Format.XML;
        } else if (fileName.endsWith(".txt")) {
            return Format.TEXT;
        }

        // если расширение не помогло, смотрим на содержимое
        return detectFormatByContent(file);
    }

    //определение формата по первым символам
    private static Format detectFormatByContent(File file) {
        Path path = file.toPath();

        try (Stream<String> lines = Files.lines(path)) {
            String firstLine = lines.findFirst().orElse("").trim();

            if (firstLine.startsWith("{")) {
                return Format.JSON;
            } else if (firstLine.startsWith("<")) {
                return Format.XML;
            } else if (firstLine.contains(":")) {
                return Format.TEXT;
            }
        } catch (IOException e) {
            System.out.println("Не удалось определить формат: " + e.getMessage());
        }

        return Format.UNKNOWN;
    }

}