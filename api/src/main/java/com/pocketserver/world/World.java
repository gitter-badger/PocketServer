package com.pocketserver.world;

import com.pocketserver.blocks.Block;

public interface World {

    Chunk getChunk(int x, int z);
    
    Chunk getChunk(int cx, int cz, boolean load);

    Block getBlockAt(int x, int y, int z);

    Block getBlockAt(Location location);
}
