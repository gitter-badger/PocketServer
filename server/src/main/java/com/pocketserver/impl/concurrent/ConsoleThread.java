package com.pocketserver.impl.concurrent;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.pocketserver.Server;

public class ConsoleThread extends Thread {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("[\\r\\n]"));
        while (Server.getServer().isRunning()) {
            String line = scanner.nextLine().trim().replaceAll("\\s+", " ");
            executeCommand(line);
        }
        scanner.close();
    }

    private void executeCommand(String line) {
        System.out.println("Received command: " + line);
    }
}
