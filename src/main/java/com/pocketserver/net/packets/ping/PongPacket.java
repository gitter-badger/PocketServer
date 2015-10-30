package com.pocketserver.net.packets.ping;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.buffer.ByteBuf;

@PacketID(0x03)
public class PongPacket extends OutPacket {
	
	private long identifier;
	
    protected PongPacket(long identifier) {
    	this.identifier = identifier;
    }

    @Override
    public void encode(ByteBuf buf) {
    	buf.writeLong(identifier);
    }
    
}