package com.pocketserver.event.player;

import com.pocketserver.event.Cancellable;
import com.pocketserver.player.Player;
import com.pocketserver.world.Location;

public class PlayerMoveEvent extends PlayerEvent implements Cancellable {
    private final Location from;
    private boolean cancelled;
    private Location to;

    public PlayerMoveEvent(Player player, Location from, Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Location getFrom() {
        return from;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
