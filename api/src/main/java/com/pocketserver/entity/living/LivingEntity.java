package com.pocketserver.entity.living;

import com.pocketserver.entity.Entity;

public interface LivingEntity extends Entity {

    public void kill();

    public double getHealth();

    public void setHealth(double health);
}
