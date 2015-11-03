package com.pocketserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pocketserver.event.EventBus;
import com.pocketserver.plugin.PluginLoader;

public class Server {

    private static Server server;

    private final Logger logger;
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
        logger = LoggerFactory.getLogger("PocketServer");
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

    public Logger getLogger() {
        return logger;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public PluginLoader getPluginLoader() {
        return pluginLoader;
    }

    public void shutdown() {
        pluginLoader.disablePlugins();
    }

}
