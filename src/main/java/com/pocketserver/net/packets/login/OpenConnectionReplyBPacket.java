package com.pocketserver.net.packets.login;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x08)
public class OpenConnectionReplyBPacket extends OutPacket {

	private int mtu, clientPort;
	
	protected OpenConnectionReplyBPacket(int mtu, int clientPort) {
		this.mtu = mtu;
		this.clientPort = clientPort;
	}
	
	@Override
	public DatagramPacket encode(DatagramPacket dg) {
		writeMagic(dg.content());
		dg.content().writeLong(TEMP_SERVERID);
		dg.content().writeShort(clientPort);
		dg.content().writeShort(mtu);
		dg.content().writeByte(0);
		return dg;
	}

}
