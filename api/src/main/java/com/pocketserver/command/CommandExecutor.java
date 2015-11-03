package com.pocketserver.command;

public interface CommandExecutor {

    String getName();

    void sendMessage(String message);

}
