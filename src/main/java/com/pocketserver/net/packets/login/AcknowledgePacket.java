package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xC0)
public class AcknowledgePacket extends Packet {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		
	}

	@Override
	public DatagramPacket encode(DatagramPacket buf) {
		
		return buf;
	}

}
