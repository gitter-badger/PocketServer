package com.pocketserver.impl.world;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class PocketWorld {

    private final Table<Integer, Integer, PocketChunk> chunks = HashBasedTable.create();

    /*
    public Chunk getChunk(int x, int z) {
        return getChunk(x, z, false);
    }

    public Chunk getChunk(int cx, int cz, boolean load) {
        if (!load || chunks.contains(cx, cz))
            return chunks.get(cx, cz);
        Chunk c = new Chunk(this, cx, cz);
        chunks.put(cx, cz, c);
        return c;
    }

    public Block getBlockAt(int x,int y, int z) {
        int cx = x >> 4, cz = z >> 4;
        Chunk c = getChunk(cx, cz);
        return c != null ? c.getBlock(x % 16, y, z % 16) : null;
    }

    public Block getBlockAt(Location location) {
        return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
    */
}
