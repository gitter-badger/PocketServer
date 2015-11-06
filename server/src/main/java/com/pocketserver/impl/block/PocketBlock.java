package com.pocketserver.impl.block;

import com.pocketserver.block.Block;
import com.pocketserver.block.Material;
import com.pocketserver.world.Chunk;
import com.pocketserver.world.Location;

public class PocketBlock implements Block {

    private final Location location;
    private Material material;
    private byte data;

    public PocketBlock(Material material, byte data, Location location) {
        this.material = material;
        this.data = data;
        this.location = location;
    }

    public PocketBlock(Material material, Location location) {
        this(material, (byte) 0, location);
    }

    @Override
    public Material getType() {
        return material;
    }

    @Override
    public byte getData() {
        return data;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    // TODO: Implement a block.

    @Override
    public Chunk getChunk() {
        return getLocation().getChunk();
    }

}
