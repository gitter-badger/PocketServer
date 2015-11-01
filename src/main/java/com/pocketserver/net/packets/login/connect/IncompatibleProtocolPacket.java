package com.pocketserver.net.packets.login.connect;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x1A)
public class IncompatibleProtocolPacket extends OutPacket {

	@Override
	public DatagramPacket encode(DatagramPacket buf) {
		buf.content().writeByte(this.getPacketID());
		buf.content().writeByte(Protocol.RAKNET);
		this.writeMagic(buf.content());
		buf.content().writeLong(TEMP_SERVERID);
		return buf;
	}

}
