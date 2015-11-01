package com.pocketserver.blocks;

import com.google.common.base.Preconditions;
import com.pocketserver.world.Location;

public class Block {
    private final Location location;
    private Material material;
    private byte data;

    public Block(Material material, Location location) {
        this.material = material;
        this.location = location;
    }

    public Block(Material material, Location location,byte data) {
        this(material,location);
        this.data = data;
    }


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        Preconditions.checkNotNull(material);
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }
}