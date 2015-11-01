package com.pocketserver.net.packets.login;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x10)
public class ServerHandshakePacket extends OutPacket {

	private long session;
	
	public ServerHandshakePacket(long session) {
		this.session = session;
	}
	
	@Override
	public DatagramPacket encode(DatagramPacket dg) {
		dg.content().writeInt(getPacketID());
		dg.content().writeInt(0x043f57fe);
		dg.content().writeByte(0xcd);
		dg.content().writeShort(19132);
		writeDataArray(dg.content());
		dg.content().writeShort(0x0000);
		dg.content().writeLong(session);
		for (int i = 0; i < 4; i++)
			dg.content().writeByte(0x00);
		dg.content().writeInt(0x04440BA9);
		return dg;
	}
	
	private void writeDataArray(ByteBuf buf) {
        byte[] unknown1 = new byte[] { (byte) 0xF5, (byte) 0xFF, (byte) 0xFF, (byte) 0xF5 };
        byte[] unknown2 = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        buf.writeMedium(unknown1.length);
        buf.writeBytes(unknown1);
        for (int i = 0; i < 9; i++) {
        	buf.writeMedium(unknown2.length);
        	buf.writeBytes(unknown2);
        }
    }
}
