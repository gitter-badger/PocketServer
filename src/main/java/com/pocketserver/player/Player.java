package com.pocketserver.player;

import java.net.InetAddress;

import com.pocketserver.entity.living.LivingEntity;

public class Player extends LivingEntity {
	private final InetAddress address;
    private GameMode gameMode = GameMode.SURVIVAL;

    public Player(int entityId, InetAddress address) {
    	super(entityId);
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