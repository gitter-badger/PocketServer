package com.pocketserver.impl.block;

import com.pocketserver.block.Block;
import com.pocketserver.world.Location;

public class PocketBlock implements Block {

    private Location location;

    @Override
    public Location getLocation() {
        return location;
    }
    // TODO: Implement a block.
}
