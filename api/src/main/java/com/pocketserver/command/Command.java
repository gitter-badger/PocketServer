package com.pocketserver.command;

public interface Command {

    String getName();

    String[] getAliases();

    String getHelpText(CommandExecutor executor);

    void exec(CommandExecutor executor, String used, String[] args);

}
