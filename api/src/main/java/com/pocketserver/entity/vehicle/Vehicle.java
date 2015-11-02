package com.pocketserver.entity.vehicle;

import com.pocketserver.entity.living.LivingEntity;

public class Vehicle implements Rideable {
    private LivingEntity passenger;

    @Override
    public void setPassenger(LivingEntity entity) {
        this.passenger = entity;
    }

    @Override
    public LivingEntity getPassenger() {
        return passenger;
    }
}
