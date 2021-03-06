package com.pocketserver.impl.entity;

import com.pocketserver.entity.Entity;
import com.pocketserver.world.Location;
import com.pocketserver.world.Vector;

public class PocketEntity implements Entity {

    private int entityId;
    private Location location;
    private Vector velocity = new Vector();

    public PocketEntity(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void teleport(Location location) {
        this.location = location;
        // TODO: Send teleport packets etc
    }

}
