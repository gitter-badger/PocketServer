package com.pocketserver.impl.entity.living;

import com.pocketserver.entity.living.LivingEntity;
import com.pocketserver.impl.entity.PocketEntity;

public class PocketLivingEntity extends PocketEntity implements LivingEntity {

	private double health;
	
	public PocketLivingEntity(int entityId) {
		super(entityId);
	}

	@Override
	public double getHealth() {
		return health;
	}

	@Override
	public void setHealth(double health) {
		this.health = health;
	}

	@Override
	public void kill() {
		this.health = 0;
		// TODO: Send death packets & remove from memory
	}

}
