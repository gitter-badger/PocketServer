package com.pocketserver.world;

import com.pocketserver.block.Block;

public interface Chunk {

    World getWorld();

    int getX();

    int getZ();

    Block getBlock(Location loc);

    Block getBlock(int dx, int dy, int dz);

}