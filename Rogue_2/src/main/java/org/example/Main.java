package org.example;

import jcurses.system.InputChar;
import jcurses.system.Toolkit;
import jcurses.widgets.Window;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Проверяем, запущена ли программа уже в терминале
        if (System.getenv("IN_TERMINAL") == null) {
            // Запускаем программу в новом терминале
            // --hold - оставить терминал открытым
            String command = "konsole -e env IN_TERMINAL=true java -cp /home/ator/work/S21/JAVA_BOOTCEMP/THIRD/Rogue_2/libs/jcurses.jar:/home/ator/work/S21/JAVA_BOOTCEMP/THIRD/Rogue_2/build/classes/java/main org.example.Main";
            System.out.println("Открываю новый терминал для запуска программы...");

            // Запускаем процесс и ожидаем завершения
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.inheritIO(); // Перенаправляет вывод процесса в текущий терминал
            Process process = processBuilder.start();
            process.waitFor(); // Ждём завершения терминала
            return; // Завершаем текущий процесс
        }

        // Основная логика программы
        runJCursesApplication();
    }

    private static void runJCursesApplication() {
        System.out.println("Программа запущена в терминале с JCurses!");
        System.out.println("Проверка JCurses...");

        // Создаём простое окно
        Window window = new Window(40, 10, false, "Тест JCurses");
        window.show();

        // Ждём нажатия клавиши, чтобы завершить
        System.out.println("Нажмите любую клавишу для выхода...");
        waitForKeyPress();

        window.hide();
        System.out.println("Программа завершена.");
    }

    private static void waitForKeyPress() {
        while (true) {
            InputChar input = Toolkit.readCharacter();
            if (input != null) {
                break;
            }
        }
    }
}
