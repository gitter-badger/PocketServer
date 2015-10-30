package com.pocketserver;

import com.pocketserver.net.netty.PocketServerHandler;
import com.pocketserver.net.netty.PocketServerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class PocketServer {

    private boolean running;

    public static void main(String[] args) {
        new PocketServer();
    }

    private PocketServer() {
        this.running = true;
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            {
                bootstrap.group(group);
                bootstrap.handler(new PocketServerInitializer());
                bootstrap.channel(NioDatagramChannel.class);
                bootstrap.option(ChannelOption.SO_BROADCAST, true);
            }
            bootstrap.bind(19132).sync().channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            this.running = false;
        }
    }
	
	private static PocketServer server = new PocketServer();
	
	public static PocketServer getServer() {
		return server;
	}

    public boolean isRunning() {
        return running;
    }
}
