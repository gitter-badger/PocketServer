package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xA0)
public class NotAcknowledgePacket extends Packet {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DatagramPacket encode(DatagramPacket buf) {
		// TODO Auto-generated method stub
		return null;
	}

}
