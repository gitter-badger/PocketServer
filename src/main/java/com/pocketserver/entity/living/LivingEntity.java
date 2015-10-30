package com.pocketserver.entity.living;

import com.pocketserver.entity.Entity;
import com.pocketserver.world.Location;
import com.pocketserver.world.Vector;

public class LivingEntity extends Entity {
	
    private int health;
    private Location location;
    private Vector velocity;

    public LivingEntity(int entityId) {
        super(entityId);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void kill() {
        setHealth(0);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
