package com.pocketserver.player;

public class Player extends OfflinePlayer {
	
    private GameMode gameMode = GameMode.SURVIVAL;

    public void sendMessage() {

    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
}