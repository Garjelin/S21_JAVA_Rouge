package org.example;

import jcurses.system.InputChar;
import jcurses.system.Toolkit;
import jcurses.widgets.Window;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Проверяем, запущена ли программа уже в терминале
        if (System.getenv("IN_TERMINAL") == null) {
            String terminalCommand = detectTerminal();
            if (terminalCommand == null) {
                System.err.println("Подходящий терминал не найден!");
                return;
            }
            System.out.println("Найден терминал: " + terminalCommand);
            // Запускаем программу в новом терминале
            // --hold - оставить терминал открытым
            String projectPath = "/home/ator/work/S21/JAVA_BOOTCEMP/THIRD/Rogue_2";
            String jcursesJarPath = projectPath + "/libs/jcurses.jar";
            String mainPath = projectPath + "/build/classes/java/main";
            String command = terminalCommand + " -e env IN_TERMINAL=true java -cp " + jcursesJarPath + ":" + mainPath + " org.example.Main";
            System.out.println("Открытие нового терминала для запуска программы...");

            // Запускаем процесс и ожидаем завершения
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                processBuilder.inheritIO(); // Перенаправляет вывод процесса в текущий терминал
                Process process = processBuilder.start();
                process.waitFor(); // Ждём завершения терминала
            } catch (IOException | InterruptedException e) {
                System.err.println("Ошибка запуска терминала: " + e.getMessage());
            }
            System.out.println("Программа завершена");
            return; // Завершаем текущий процесс
        }

        // Основная логика программы
        runJCursesApplication();
    }

    private static void runJCursesApplication() {
        System.out.println("Игра запущена с использованием библиотеки JCurses!");

        Window window = new Window(40, 10, false, "Простое окно");
        window.show();

        System.out.println("Нажмите любую клавишу для выхода...");
        waitForKeyPress();

        window.hide();
        System.out.println("Игра завершена");
    }

    private static void waitForKeyPress() {
        while (true) {
            InputChar input = Toolkit.readCharacter();
            if (input != null) {
                break;
            }
        }
    }

    private static String detectTerminal() {
        String[] terminals = {"konsole", "gnome-terminal", "xterm", "alacritty"};
        for (String terminal : terminals) {
            if (isCommandAvailable(terminal)) {
                return terminal;
            }
        }
        return null;
    }

    private static boolean isCommandAvailable(String command) {
        try {
            Process process = new ProcessBuilder("which", command).start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
