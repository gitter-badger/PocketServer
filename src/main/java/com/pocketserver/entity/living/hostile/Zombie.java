package com.pocketserver.entity.living.hostile;

import com.pocketserver.entity.living.LivingEntity;

public class Zombie extends LivingEntity implements Hostile {

    public Zombie(int entityId) {
        super(entityId);
    }
    
    @Override
    public void attack(LivingEntity entity) {

    }
    
}
