package com.pocketserver.plugin;

import com.pocketserver.Server;
import com.pocketserver.event.EventBus;

public abstract class Plugin {
	
	public String getName() {
		return getClass().getSimpleName();
	}
	
	public Server getServer() {
		return Server.getServer();
	}
	
	public EventBus getEventBus() {
		return Server.getServer().getEventBus();
	}
	
    public void onEnable() {}
    public void onDisable() {}
    
}
