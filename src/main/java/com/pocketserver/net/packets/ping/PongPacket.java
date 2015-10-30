package com.pocketserver.net.packets.ping;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x03)
public class PongPacket extends OutPacket {
	
	private long identifier;
	
    protected PongPacket(long identifier) {
    	this.identifier = identifier;
    }

    @Override
    public DatagramPacket encode(DatagramPacket buf) {
    	buf.content().writeByte(getPacketID());
    	buf.content().writeLong(identifier);
        return buf;
    }
}