package com.pocketserver.net.packets.login;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x13)
public class ClientHandshakePacket extends InPacket {

	private static final int COOKIE = 0x43f57fe;
	private static final byte SECURITY = (byte) 0xcd;
	
	private short port;
	private short timestamp;
	private long session, session2;
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		if (dg.content().readInt() != COOKIE && dg.content().readByte() != SECURITY)
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
