package com.pocketserver.net.packets.login;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x1A)
public class IncompatibleProtocolPacket extends OutPacket {

	@Override
	public DatagramPacket encode(DatagramPacket buf) {
		buf.content().writeInt(this.getPacketID());
		buf.content().writeByte(Protocol.RAKNET);
		this.writeMagic(buf.content());
		buf.content().writeLong(TEMP_SERVERID);
		return buf;
	}

}
