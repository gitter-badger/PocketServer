package com.pocketserver.net.packets.connect;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x09)
public class ClientConnectPacket extends InPacket {

	long clientId, session;
	byte unknown;
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		clientId = dg.content().readLong();
		session = dg.content().readLong();
		unknown = dg.content().readByte();
	}

}
