package com.pocketserver.entity;


public abstract class Entity {
    private final int entityId;

    protected Entity(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }
}