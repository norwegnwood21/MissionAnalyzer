package org.example;
import org.example.model.Mission;
import org.example.parser.JsonMissionParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
public class Main {
    public static void main(String[] args) {
        System.out.println("Введите путь к файлу миссии:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        //проверка на пустой файл
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Ошибка: путь к файлу не может быть пустым");
            return;
        }
        File file = new File(filePath);
        // Проверяем, существует ли файл
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
        System.out.println("Файл успешно найден и доступен для чтения!");
        System.out.println("Имя файла: " + file.getName());
        System.out.println("Размер: " + file.length() + " байт");

        // Пробуем прочитать первые несколько строк
        Path path = Paths.get(filePath);
        System.out.println("\nПервые 5 строк файла:");

        try (Stream<String> lines = Files.lines(path)) {
            lines.limit(5).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        System.out.println("\n Анализ формата файла ");
        FileFormatDetector.Format format = FileFormatDetector.detectFormat(file);
        System.out.println("Формат: " + format);

        if (format == FileFormatDetector.Format.JSON) {
            System.out.println("\nПарсинг JSON файла ");
            JsonMissionParser jsonParser = new JsonMissionParser();

            try {
                Mission mission = jsonParser.parseFile(file);
                System.out.println("JSON успешно распарсен");

                // Выводим информацию о миссии
                mission.printMissionInfo();

            } catch (IOException e) {
                System.out.println("Ошибка при парсинге JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Парсер для этого формата не реализован");
        }

        scanner.close();
    }
}