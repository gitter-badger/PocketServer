package com.pocketserver.plugin;

import com.pocketserver.event.EventBus;

public abstract class Plugin {
	
	public String getName() {
		return getClass().getSimpleName();
	}
	public EventBus getEventBus() {
		return null;
	}
	
    public void onEnable() {}
    public void onDisable() {}
    
}
