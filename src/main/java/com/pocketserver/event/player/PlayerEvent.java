package com.pocketserver.event.player;

import com.pocketserver.event.Event;
import com.pocketserver.player.Player;

public class PlayerEvent extends Event {
    private final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
