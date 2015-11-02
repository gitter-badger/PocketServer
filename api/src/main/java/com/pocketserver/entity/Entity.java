package com.pocketserver.entity;

import com.pocketserver.world.Location;
import com.pocketserver.world.Vector;

public interface Entity {

    int getEntityId();

    Vector getVelocity();

    void setVelocity(Vector velocity);

    Location getLocation();

    void teleport(Location location);

}