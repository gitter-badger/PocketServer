package com.pocketserver.player;

import java.net.InetAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerRegistery {
    private static final PlayerRegistery INSTANCE = new PlayerRegistery();
    private final Map<InetAddress,Player> playerMap = new ConcurrentHashMap<>();

    public static PlayerRegistery get() {
        return INSTANCE;
    }

    public void registerPlayer(Player player) {
        this.playerMap.put(player.getAddress(),player);
    }

    public void unregisterPlayer(Player player) {
        this.playerMap.remove(player.getAddress());
    }

    public Optional<Player> getPlayer(InetAddress address) {
        return Optional.ofNullable(playerMap.get(address));
    }

    public Optional<Player> getPlayer(String name) {
        return playerMap.values().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findAny();
    }
}
