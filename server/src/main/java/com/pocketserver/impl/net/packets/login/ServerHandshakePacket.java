package com.pocketserver.impl.net.packets.login;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.PacketID;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x10)
public class ServerHandshakePacket extends OutPacket {

    private static final byte[] CONSTANT_BYTES = new byte[]{0x00, 0x00, 0x00, 0x00, 0x04, 0x44, 0x0b, (byte) 0xa9};
    private static final byte[] UNKNOWN_1 = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE };
    private static final byte[] UNKNOWN_2 = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    private final long timestamp;

    public ServerHandshakePacket(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        ByteBuf content = dg.content();
        content.writeByte(getPacketID());
        content.writeInt(0x043f57fe);
        content.writeByte(0xcd);
        content.writeShort(19132);
        writeDataArray(content);
        content.writeByte(0x00);
        content.writeByte(0x00);
        content.writeLong(timestamp);
        content.writeBytes(CONSTANT_BYTES);
        return dg;
    }

    private void writeDataArray(ByteBuf buf) {
        writeTriad(buf, UNKNOWN_1.length);
        buf.writeBytes(UNKNOWN_1);
        for (int i = 0; i < 9; i++)
        {
            writeTriad(buf,UNKNOWN_2.length);
            buf.writeBytes(UNKNOWN_2);
        }
    }

    public void writeTriad(ByteBuf buf,int i) {
        byte b1 = (byte)((i >> 16) & 0xFF);
        byte b2 = (byte)((i >> 8) & 0xFF);
        byte b3 = (byte)(i & 0xFF);

        buf.writeByte(b1);
        buf.writeByte(b2);
        buf.writeByte(b3);
    }
}
