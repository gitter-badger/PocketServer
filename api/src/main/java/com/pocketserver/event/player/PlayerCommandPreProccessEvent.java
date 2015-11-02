package com.pocketserver.event.player;

import com.pocketserver.event.Cancellable;
import com.pocketserver.player.Player;

public class PlayerCommandPreProccessEvent extends PlayerEvent implements Cancellable {
    private final Player player;
    private final String command;
    private boolean cancelled;

    public PlayerCommandPreProccessEvent(Player player,String command) {
        super(player);
        this.player = player;
        this.command = command;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
