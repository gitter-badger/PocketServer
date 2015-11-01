package com.pocketserver.net.packets.login.connect;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x06)
public class OpenConnectionReplyAPacket extends OutPacket {

	private int mtu;
	
	protected OpenConnectionReplyAPacket(int mtu) {
		this.mtu = mtu;
	}
	
	@Override
	public DatagramPacket encode(DatagramPacket dg) {
		dg.content().writeByte(this.getPacketID());
		writeMagic(dg.content());
		dg.content().writeLong(TEMP_SERVERID);
		dg.content().writeByte(0);
		dg.content().writeShort(mtu);
		return dg;
	}

}
