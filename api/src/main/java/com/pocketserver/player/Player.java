package com.pocketserver.player;

import java.net.InetSocketAddress;

import com.pocketserver.entity.living.LivingEntity;

public interface Player extends LivingEntity {

    void sendMessage(String message);

    void chat(String message);

    GameMode getGameMode();

    void setGameMode(GameMode mode);

    InetSocketAddress getAddress();

    String getName();
    
}
