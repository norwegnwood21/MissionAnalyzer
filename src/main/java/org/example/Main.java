package org.example;

import org.example.model.Mission;
import org.example.parser.JsonMissionParser;
import org.example.parser.TextMissionParser;
import org.example.parser.XmlMissionParser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите путь к файлу миссии:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();


        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Ошибка: путь к файлу не может быть пустым");
            return;
        }

        File file = new File(filePath);


        if (!file.exists()) {
            System.out.println("Ошибка: файл не существует по пути: " + filePath);
            return;
        }

        if (!file.isFile()) {
            System.out.println("Ошибка: указанный путь не является файлом");
            return;
        }

        if (!file.canRead()) {
            System.out.println("Ошибка: файл не доступен для чтения");
            return;
        }

        System.out.println("Файл успешно найден и доступен для чтения");

        FileFormatDetector.Format format = FileFormatDetector.detectFormat(file);
        System.out.println("Формат файла: " + format);

        if (format == FileFormatDetector.Format.JSON) {
            System.out.println("\nПарсинг JSON файла ");
            JsonMissionParser jsonParser = new JsonMissionParser();

            try {
                Mission mission = jsonParser.parseFile(file);
                System.out.println("JSON успешно распарсен");


                mission.printMissionInfo();

            } catch (IOException e) {
                System.out.println("Ошибка при парсинге JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (format == FileFormatDetector.Format.XML) {
            System.out.println("\nПарсинг XML файла");
            XmlMissionParser xmlParser = new XmlMissionParser();

            try {
                Mission mission = xmlParser.parseFile(file);
                System.out.println("XML успешно распарсен");
                mission.printMissionInfo();
            } catch (IOException e) {
                System.out.println("Ошибка при парсинге XML: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (format == FileFormatDetector.Format.TEXT) {
            System.out.println("\nПарсинг текстового файла ");
            TextMissionParser textParser = new TextMissionParser();

            try {
                Mission mission = textParser.parseFile(file);
                System.out.println("Текстовый файл успешно распарсен");
                mission.printMissionInfo();
            } catch (IOException e) {
                System.out.println("Ошибка при парсинге текстового файла: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Парсер для этого формата не реализован");
        }

        scanner.close();
    }
}