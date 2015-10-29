package com.pocketserver.world;

import com.pocketserver.blocks.Block;

public class Chunk {
    private final int x;
    private final int z;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Chunk(Location location) {
        this(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Block getBlock(Location location) {
        return null;
    }

    public Block getBlock(int x, int y, int z) {
        return null;
    }
}