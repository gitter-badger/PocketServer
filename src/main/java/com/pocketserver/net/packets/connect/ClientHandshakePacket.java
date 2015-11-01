package com.pocketserver.net.packets.connect;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x13)
public class ClientHandshakePacket extends InPacket {

	final int cookie = 0x43f57fe;
	final byte security = (byte) 0xcd;
	
	short port;
	short timestamp;
	long session, session2;
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		if (dg.content().readInt() != 0x43f57fe)
			return;
		if (dg.content().readByte() != 0xcd)
			return;
		port = dg.content().readShort();
		dg.content().readBytes(new byte[(int) dg.content().readByte()]);
		for (int i = 0; i < 9; i++) {
			int n = dg.content().readMedium();
			dg.content().readBytes(new byte[n]);
		}
		timestamp = dg.content().readShort();
		session = dg.content().readLong();
		session2 = dg.content().readLong();
	}

}
