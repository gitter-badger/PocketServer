package com.pocketserver.net.packets.login;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x09)
public class ClientConnectPacket extends InPacket {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		long clientId = dg.content().readLong();
		long session = dg.content().readLong();
		byte unknown = dg.content().readByte();
        
		new ServerHandshakePacket(session).sendGame(0x84, ctx, dg.sender());
	}

}
