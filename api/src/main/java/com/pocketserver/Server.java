package com.pocketserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pocketserver.command.CommandManager;
import com.pocketserver.command.ConsoleCommandExecutor;
import com.pocketserver.event.EventBus;
import com.pocketserver.plugin.PluginLoader;

public class Server {

    private static Server server;

    private final Logger logger;
    private final EventBus eventBus;
    private final PluginLoader pluginLoader;
    private final CommandManager commandManager;

    private ConsoleCommandExecutor console = new ConsoleCommandExecutor();

    private boolean loaded = false;
    private boolean running = true;

    public static synchronized Server getServer() {
        if (server == null) {
            server = new Server();
            server.load();
        }
        return server;
    }

    private Server() {
        eventBus = new EventBus();
        pluginLoader = new PluginLoader();
        commandManager = new CommandManager();
        logger = LoggerFactory.getLogger("PocketServer");
    }

    private void load() {
        if (loaded)
            return;
        loaded = true;
        logger.info("--- Loading plugins ---");
        pluginLoader.loadPlugins();
        logger.info("--- Loaded plugins ---");
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

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConsoleCommandExecutor getConsoleExecutor() {
        return console;
    }

    public void shutdown() {
        pluginLoader.disablePlugins();
    }

}
