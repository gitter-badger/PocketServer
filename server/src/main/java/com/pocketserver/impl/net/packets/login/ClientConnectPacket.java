package com.pocketserver.impl.net.packets.login;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.PacketID;

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

		new ServerHandshakePacket(clientId, session).sendGame(0x84, ctx, dg.sender());
	}

}
