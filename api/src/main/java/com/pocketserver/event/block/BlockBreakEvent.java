package com.pocketserver.event.block;

import com.pocketserver.block.Block;
import com.pocketserver.event.Cancellable;
import com.pocketserver.event.player.PlayerEvent;
import com.pocketserver.player.Player;
import com.pocketserver.world.Location;

public class BlockBreakEvent extends PlayerEvent implements Cancellable {
    private final Block block;
    private boolean cancelled;

    public BlockBreakEvent(Player player, Block block) {
        super(player);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public Location getLocation() {
        return block.getLocation();
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
