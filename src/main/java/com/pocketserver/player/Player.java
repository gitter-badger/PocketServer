package com.pocketserver.player;

import com.pocketserver.entity.living.LivingEntity;

import java.net.InetAddress;

public class Player extends LivingEntity {
	private final InetAddress address;
    private GameMode gameMode = GameMode.SURVIVAL;

    public Player(InetAddress address) {
        this.address = address;
    }

    public void sendMessage() {

    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
}