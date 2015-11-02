package com.pocketserver.event.player;

import com.pocketserver.command.Command;
import com.pocketserver.event.Cancellable;
import com.pocketserver.player.Player;

public class PlayerPostCommandEvent extends PlayerEvent implements Cancellable {
    private final Command command;
    private boolean cancelled;

    public PlayerPostCommandEvent(Player player,Command command) {
        super(player);
        this.command = command;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Command getCommand() {
        return command;
    }
}
