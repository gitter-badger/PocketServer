package com.pocketserver.command;

import java.util.Arrays;

public abstract class Command {

    private final String[] aliases;

    public Command(String name, String... aliases) {
        this.aliases = new String[aliases.length + 1];
        this.aliases[0] = name;
        for (int i = 0; i < aliases.length; i++)
            this.aliases[i + 1] = aliases[i];
    }

    public final String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }

    public abstract void exec(CommandExecutor executor, String used, String[] args);

}
