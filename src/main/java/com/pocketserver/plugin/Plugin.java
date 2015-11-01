package com.pocketserver.plugin;

public abstract class Plugin {
	
	public String getName() {
		return getClass().getSimpleName();
	}
	
    public void onEnable() {}
    public void onDisable() {}
    
}
