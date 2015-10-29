package com.pocketserver.net.netty.channel;

import com.google.inject.Inject;
import com.pocketserver.net.Packet;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class PacketAdapter extends ChannelHandlerAdapter {
    private final Logger logger;

    @Inject
    public PacketAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Channel active for " + ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;

    }
}