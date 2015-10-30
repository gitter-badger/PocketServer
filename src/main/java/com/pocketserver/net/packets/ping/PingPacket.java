package com.pocketserver.net.packets.ping;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x00)
public class PingPacket extends InPacket {
	
    protected PingPacket() {}


    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        new PongPacket(dg.content().readLong()).send(ctx);
    }
}