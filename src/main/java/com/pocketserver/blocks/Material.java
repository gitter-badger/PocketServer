package com.pocketserver.blocks;

public enum Material {
    AIR(0),GRASS(1);

    private final int id;

    Material(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}