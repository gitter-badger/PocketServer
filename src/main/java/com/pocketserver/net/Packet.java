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
    
    public final void send(ChannelHandlerContext ctxt) {
    	ByteBuf buf = Unpooled.buffer();
    	buf.writeByte(getPacketID());
    	encode(buf);
    	ctxt.write(buf);
    }
    
    public abstract void decode(ChannelHandlerContext ctxt, DatagramPacket dg);
    public abstract void encode(ByteBuf buf);

}