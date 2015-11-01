package com.pocketserver.net.packets.login;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x15)
public class ClientCancelConnectPacket extends InPacket {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		for (int i = 0; i < 100; i++) System.out.println(dg.sender().getHostName() + " has cancelled the login.");
	}

}
