package com.pocketserver;

import java.io.PrintStream;
import java.net.BindException;

import com.pocketserver.event.EventBus;
import com.pocketserver.net.netty.PocketServerHandler;
import com.pocketserver.plugin.PluginLoader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class PocketServer {
	
	private static PocketServer server;
	
	public static PocketServer getServer() {
		return server;
	}

    public static void main(String[] args) {
        server = new PocketServer();
    }
    
    private final EventBus eventBus;
    private final PluginLoader pluginLoader;
    private boolean running = true;

    private PocketServer() {

    	// Recreate STDOUT and STDERR to have Auto-Flush on, so any newline chars flush them.
    	System.setOut(new PrintStream(System.out, true));
    	System.setErr(new PrintStream(System.err, true));
    	
        this.eventBus = new EventBus();
        this.pluginLoader = new PluginLoader();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            {
                boot.group(group)
                    .handler(new PocketServerHandler())
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true);
            }
            System.out.println("Loading plugins..");
            pluginLoader.loadPlugins();
            System.out.println("Done loading plugins.");
            Channel ch = boot.bind(19132).sync().channel();
            System.out.println("Successfully bound to *:19132");
            System.out.println("Server is done loading!");
            ch.closeFuture().await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
        	if (ex instanceof BindException) {
        		System.out.println("Failed to bind to port (19132 is in use)");
        	} else {
        		throw ex;
        	}
        } finally {
            System.out.println("Goodbye.");
            pluginLoader.disablePlugins();
            group.shutdownGracefully();
            running = false;
        }
    }
	
	public boolean isRunning() {
		return running;
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
}
