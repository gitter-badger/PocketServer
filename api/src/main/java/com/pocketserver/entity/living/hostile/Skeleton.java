package com.pocketserver.entity.living.hostile;

import com.pocketserver.entity.living.LivingEntity;

public class Skeleton extends LivingEntity implements Hostile {

    public Skeleton(int entityId) {
        super(entityId);
    }
    
    @Override
    public void attack(LivingEntity entity) {

    }
    
}
