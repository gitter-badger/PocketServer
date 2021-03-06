package com.pocketserver.player;

import java.net.InetSocketAddress;

import com.pocketserver.command.CommandExecutor;
import com.pocketserver.entity.living.LivingEntity;

public interface Player extends LivingEntity, CommandExecutor {

    void sendMessage(String message);

    void chat(String message);

    GameMode getGameMode();

    void setGameMode(GameMode mode);

    InetSocketAddress getAddress();

}
