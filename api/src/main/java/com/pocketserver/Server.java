package com.pocketserver;

import com.pocketserver.event.EventBus;
import com.pocketserver.plugin.PluginLoader;

public class Server {
	
	private static Server server;
    
    private final EventBus eventBus;
    private final PluginLoader pluginLoader;
    private boolean running = true;
    
	public static Server getServer() {
		if (server == null) {
			server = new Server();
			server.load();
		}
		return server;
	}
	
	private Server() {
		eventBus = new EventBus();
		pluginLoader = new PluginLoader();
	}
	
	private void load() {		
		System.out.println("--- Loading plugins ---");
		pluginLoader.loadPlugins();
		System.out.println("--- Loaded plugins ---");
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public PluginLoader getPluginLoader() {
		return pluginLoader;
	}
	
}
