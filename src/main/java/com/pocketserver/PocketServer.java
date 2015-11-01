package com.pocketserver;

import java.net.BindException;

import com.pocketserver.event.EventBus;
import com.pocketserver.net.netty.PocketServerHandler;

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
    private boolean running = true;

    private PocketServer() {
        this.eventBus = new EventBus();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            {
                boot.group(group)
                    .handler(new PocketServerHandler())
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true);
            }
            Channel ch = boot.bind(19132).sync().channel();
            System.out.println("Successfully bound to *:19132");
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
