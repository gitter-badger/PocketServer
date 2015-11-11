package com.pocketserver.impl.net.packets.login;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import com.pocketserver.impl.net.util.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;

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
        content.writeByte((byte)0x10);
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

    @Deprecated
    public void writeTriad(ByteBuf buf,int i) {
        buf.writeBytes(PacketUtils.getTriad(i));
    }

    @Override
    public Packet sendPacket(ChannelHandlerContext ctx, InetSocketAddress address) {
        DatagramPacket encode = encode(new DatagramPacket(Unpooled.buffer(), address));
        ByteBuf encodedBuf = encode.content();

        DatagramPacket encapsulated = new DatagramPacket(Unpooled.buffer(),address);
        ByteBuf content = encapsulated.content();
        content.writeByte(0x00);
        content.writeShort(encodedBuf.readableBytes()-1);
        content.writeBytes(encodedBuf);
        ctx.writeAndFlush(encapsulated);
        return this;
    }
}
