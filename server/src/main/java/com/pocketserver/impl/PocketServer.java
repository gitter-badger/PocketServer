package com.pocketserver.impl;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

//import com.pocketserver.impl.event.EventBus;
import com.pocketserver.impl.net.netty.PocketServerHandler;
//import com.pocketserver.plugin.PluginLoader;

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
    
    //private final EventBus eventBus;
    //private final PluginLoader pluginLoader;
    private boolean running = true;

    private PocketServer() {

    	// Recreate STDOUT and STDERR to have Auto-Flush on, so any newline chars flush them.
    	System.setOut(new PrintStream(System.out, true));
    	System.setErr(new PrintStream(System.err, true));
    	
        //this.eventBus = new EventBus();
        //this.pluginLoader = new PluginLoader();
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
            //pluginLoader.loadPlugins();
            System.out.println("Done loading plugins.");
            Channel ch = boot.bind(19132).sync().channel();
            System.out.println("Successfully bound to *:19132");
            System.out.println("Server is done loading!");
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter(Pattern.compile("[\\r\\n]"));
            while (true) {
            	String line = scanner.nextLine().trim().replaceAll("\\s+", " ");
            	if (line.equals("stop")) {
            		break;
            	}
            }
            //ch.closeFuture().await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            //pluginLoader.disablePlugins();
            System.out.println("Goodbye.");
            group.shutdownGracefully();
            running = false;
        }
    }
	
	public boolean isRunning() {
		return running;
	}
	
	//public EventBus getEventBus() {
	//	return eventBus;
	//}
}
