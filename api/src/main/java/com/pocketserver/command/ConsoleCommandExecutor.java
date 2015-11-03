package com.pocketserver.command;

public class ConsoleCommandExecutor implements CommandExecutor {

    @Override
    public String getName() {
        return "**CONSOLE**";
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

}
