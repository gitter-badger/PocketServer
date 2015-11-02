package com.pocketserver.impl.net.packets.login;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@PacketID(0x10)
public class ServerHandshakePacket extends OutPacket {
    private final long guid;
	private long session;
	
	public ServerHandshakePacket(long guid, long session) {
        this.guid = guid;
        this.session = session;
	}

	@Override
	public DatagramPacket encode(DatagramPacket dg) {
        ByteBuf content = dg.content();
        content.writeByte(getPacketID());
        writeDataArray(content);
        content.writeLong(guid); //https://github.com/NiclasOlofsson/MiNET/blob/master/src/MiNET/MiNET/Net/Package.cs
        content.writeLong(session);
        return dg;
    }
	
	private void writeDataArray(ByteBuf buf) {
        byte[] unknown1 = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE };
        byte[] unknown2 = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        buf.writeMedium(unknown1.length);
        buf.writeBytes(unknown1);
        for (int i = 0; i < 9; i++) {
        	buf.writeMedium(unknown2.length);
        	buf.writeBytes(unknown2);
        }
    }
}
