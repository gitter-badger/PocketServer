package com.pocketserver.entity.living;

import com.pocketserver.entity.Entity;
import com.pocketserver.world.Location;
import com.pocketserver.world.Vector;

public interface LivingEntity extends Entity {

    public int getHealth();

    public void setHealth(int health);

    public void kill();

    public Location getLocation();

    public void setLocation(Location location);
}
