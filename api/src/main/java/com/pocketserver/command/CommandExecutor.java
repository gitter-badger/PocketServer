package com.pocketserver.command;

import com.pocketserver.Permissible;

public interface CommandExecutor extends Permissible {

    String getName();

    void sendMessage(String message);

}
