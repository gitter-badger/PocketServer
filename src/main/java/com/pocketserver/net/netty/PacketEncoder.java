package com.pocketserver.net.netty;

import com.pocketserver.net.packets.login.UnconnectedPingPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class PacketEncoder extends MessageToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        System.out.println("Got hit!" + channelHandlerContext.channel().remoteAddress());
        list.add(new UnconnectedPingPacket());
    }
}
