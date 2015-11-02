package com.pocketserver.impl;

import com.pocketserver.impl.concurrent.ConsoleThread;
import com.pocketserver.impl.net.netty.PocketServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

//import com.pocketserver.impl.event.EventBus;
//import com.pocketserver.plugin.PluginLoader;

public class PocketServer {

    public static void main(String[] args) {
        new PocketServer();
    }

    private final Logger logger;

    private PocketServer() {

        // Recreate STDOUT and STDERR to have Auto-Flush on, so any newline
        // chars flush them.
        System.setOut(new PrintStream(System.out, true));
        System.setErr(new PrintStream(System.err, true));
        this.logger = LoggerFactory.getLogger("PocketServer");
        new ConsoleThread(isRunning()).start();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            {
                boot.group(group).handler(new PocketServerHandler()).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true);
            }

            ChannelFuture future = boot.bind(19132).sync();
            System.out.println("Successfully bound to *:19132");
            System.out.println("Server is done loading!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            // Server.getServer().shutdown();
            System.out.println("Goodbye.");
            group.shutdownGracefully();
        }
    }

    public boolean isRunning() {
        return true;
    }
}
