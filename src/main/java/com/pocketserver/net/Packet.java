package com.pocketserver.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public abstract class Packet {
	
    public final int getPacketID() {
    	PacketID id = getClass().getAnnotation(PacketID.class);
    	return id == null ? -1 : id.value();
    }
    
    public final void send(ChannelHandlerContext ctx) {
    	ByteBuf buf = Unpooled.buffer();
    	buf.writeByte(getPacketID());

    	ctx.write(buf);
    }
    
    public abstract void decode(ChannelHandlerContext ctx, DatagramPacket dg);
    public abstract DatagramPacket encode(DatagramPacket buf);

}