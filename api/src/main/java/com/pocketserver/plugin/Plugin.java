package com.pocketserver.plugin;

import java.io.File;

import com.pocketserver.Server;
import com.pocketserver.event.EventBus;

public abstract class Plugin {
	
	File file;
	
	public final String getName() {
		Name name = getClass().getAnnotation(Name.class);
		if (name != null)
			return name.value();
		return getClass().getSimpleName();
	}
	
	public final String getDescription() {
		Description desc = getClass().getAnnotation(Description.class);
		if (desc != null)
			return desc.value();
		return "";
	}
    
    public String[] getDependencies() {
    	Dependency dep = getClass().getAnnotation(Dependency.class);
    	return dep == null ? new String[0] : dep.value();
    }
	
	protected final File getFile() {
		return file;
	}
	
	public final File getDataFolder() {
		return new File("plugins/" + getName() + "/");
	}
	
	public final Server getServer() {
		return Server.getServer();
	}
	
	public final EventBus getEventBus() {
		return Server.getServer().getEventBus();
	}
	
    public void onEnable() {}
    public void onDisable() {}
    
}
