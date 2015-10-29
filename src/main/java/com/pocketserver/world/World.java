package com.pocketserver.world;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.pocketserver.blocks.Block;

public class World {
    private final Table<Integer,Integer,Chunk> chunkLocations = HashBasedTable.create();

    public Block getBlockAt(int x,int y, int z) {
        x = x >> 4;
        z = z >> 4;
        if (!chunkLocations.contains(x, z)) {
            return null;
        }
        Chunk chunk = chunkLocations.get(x, z);
        return chunk.getBlock(x,y,z);
    }

    public Block getBlockAt(Location location) {
        return getBlockAt(location.getBlockX(),location.getBlockY(),location.getBlockZ());
    }
}
