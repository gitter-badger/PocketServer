package com.pocketserver.event.player;

import com.pocketserver.event.entity.EntityEvent;
import com.pocketserver.player.Player;

public class PlayerEvent extends EntityEvent {

    public PlayerEvent(Player player) {
        super(player);
    }

    public Player getPlayer() {
        return (Player) getEntity();
    }
}
