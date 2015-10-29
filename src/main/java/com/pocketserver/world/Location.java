package com.pocketserver.world;

import com.pocketserver.blocks.Block;

import java.io.Serializable;

public class Location implements Cloneable,Serializable{
    private final World world;
    private final double x;
    private final double y;
    private final double z;

    public Location(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(Location location) {
        this(location.getWorld(),location.getX(),location.getY(),location.getZ());
    }

    public Block getBlock() {
        return world.getBlockAt(this);
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getBlockX() {
        return (int) x;
    }

    public int getBlockY() {
        return (int) y;
    }

    public int getBlockZ() {
        return (int) z;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
