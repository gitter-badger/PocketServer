package com.pocketserver.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    private final Map<String, Command> commandMap = new ConcurrentHashMap<>();

    public void exec(CommandExecutor executor, String command) {

    }

}
