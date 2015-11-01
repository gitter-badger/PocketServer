package com.pocketserver.impl.net.packets.login.connect;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x1A)
public class IncompatibleProtocolPacket extends OutPacket {

	@Override
	public DatagramPacket encode(DatagramPacket buf) {
		buf.content().writeByte(this.getPacketID());
		buf.content().writeByte(Protocol.RAKNET_VERSION);
		this.writeMagic(buf.content());
		buf.content().writeLong(TEMP_SERVER_ID);
		return buf;
	}

}
