package com.pocketserver.event.entity;

import com.pocketserver.entity.Entity;
import com.pocketserver.event.Event;

public class EntityEvent extends Event {
    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
