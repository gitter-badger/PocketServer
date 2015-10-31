package com.pocketserver.player;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerRegistery {
	
    private static final PlayerRegistery INSTANCE = new PlayerRegistery();
    private final Map<InetSocketAddress, Player> playerMap = new ConcurrentHashMap<>();

    public static PlayerRegistery get() {
        return INSTANCE;
    }

    public void registerPlayer(Player player) {
        this.playerMap.put(player.getAddress(),player);
    }

    public void unregisterPlayer(Player player) {
        this.playerMap.remove(player.getAddress());
    }

    public Player getPlayer(InetSocketAddress address) {
        return playerMap.get(address);
    }

    public Player getPlayer(String name) {
    	for (Player p : playerMap.values())
    		if (p.getName().equalsIgnoreCase(name))
    			return p;
        return null;
    }
}
