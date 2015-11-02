package com.pocketserver.entity.vehicle;

import com.pocketserver.entity.living.LivingEntity;

public interface Rideable {
    void setPassenger(LivingEntity entity);

    LivingEntity getPassenger();
}
