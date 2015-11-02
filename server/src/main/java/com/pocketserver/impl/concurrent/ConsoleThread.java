package com.pocketserver.impl.concurrent;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleThread extends Thread {
    private boolean running;

    public ConsoleThread(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("[\\r\\n]"));
        while (running) {
            String line = scanner.nextLine().trim().replaceAll("\\s+", " ");
            executeCommand(line);
        }
        scanner.close();
    }

    private void executeCommand(String line) {

    }
}
