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

	private long session;
	
	public ServerHandshakePacket(long session) {
		this.session = session;
	}

	@Override
	public DatagramPacket encode(DatagramPacket dg) {

        //THIS IS PROBABLY SO WRONG OMFG 

        ByteBuf content = Unpooled.buffer();
        content.writeByte(0x10);
        // 04 85 6A 55 6E D7 10
        content.writeBytes(ByteBuffer.allocate(4).putInt(0x043f57fe));
        content.writeByte(0x6E);
        content.writeShort(19132);
        writeDataArray(content);
        content.writeShort(0x0000);
        content.writeLong(session);
        for (int i = 0; i < 4; i++)
            content.writeByte(0x00);
        content.writeInt(0x00699B14);
        ByteBuf outSidePacket = dg.content();
        System.out.println("Cap: " + content.readableBytes());
        System.out.println("Cap in bits: " + content.readableBytes()*8);
        System.out.println("Content: " + content.toString(Charset.defaultCharset()));
        outSidePacket.writeShort(content.capacity()*8);

        outSidePacket.writeBytes(content.readBytes(new byte[content.readableBytes()]));
        return dg;
        /*

		return dg;
		*/

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
