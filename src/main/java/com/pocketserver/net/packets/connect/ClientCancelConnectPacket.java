package com.pocketserver.net.packets.connect;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x15)
public class ClientCancelConnectPacket extends InPacket {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		// nothing to read, only the ID is sent here
	}

}
