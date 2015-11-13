package com.pocketserver.impl.net.packets.login;

import com.google.common.primitives.Bytes;
import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.Arrays;

@PacketID(0x10)
public class ServerHandshakePacket extends OutPacket {
    private static final InetSocketAddress LOCAL_ADDRESS = new InetSocketAddress("127.0.0.1",0);
    private static final InetSocketAddress SYSTEM_ADDRESS = new InetSocketAddress("0.0.0.0",0);

    private final long timeStamp;
    private final long serverTimeStamp;

    public InetSocketAddress address;
    public InetSocketAddress[] systemAddresses = new InetSocketAddress[] {
            new InetSocketAddress("127.0.0.1", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
            new InetSocketAddress("0.0.0.0", 0),
    };

    public ServerHandshakePacket(long timeStamp) {
        this.timeStamp = timeStamp;
        serverTimeStamp = timeStamp+50L;  //When ya just don't care anymore
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        ByteBuf content = dg.content();
        this.address = dg.recipient();
        content.writeByte(getPacketID());
        System.out.println(content.readableBytes());
        content.writeBytes(writeAddress(address));
        System.out.println(content.readableBytes());
        content.writeShort(0);
        System.out.println(content.readableBytes());
        for (InetSocketAddress systemAddress : systemAddresses) {
            content.writeBytes(writeAddress(systemAddress));
            System.out.println("Loop: " + content.readableBytes());
        }
        content.writeLong(timeStamp);
        System.out.println(content.readableBytes());
        content.writeLong(serverTimeStamp);
        System.out.println(content.readableBytes());
        /*content.writeBytes(writeAddress(dg.recipient()));
        content.writeBytes(writeAddress(LOCAL_ADDRESS));
        for (int i = 0; i < 9; i++) {
            content.writeBytes(writeAddress(SYSTEM_ADDRESS));
        }
        content.writeLong(timeStamp);
        content.writeLong(serverTimeStamp);
        return dg;
        */
        return dg;
    }

    private byte[] writeAddress(InetSocketAddress systemAddress) {
        String hostString = systemAddress.getHostString();
        int port = systemAddress.getPort();
        byte[] bytes = new byte[6];
        bytes[0] = 4;
        String[] split = hostString.split(".");
        for (int i = 0; i < split.length; i++) {
            bytes[i+1] = (byte) ~Byte.parseByte(split[i]);
        }
        bytes[5] = (byte) port;
        return bytes;
    }

    @Override
    public Packet sendPacket(ChannelHandlerContext ctx, InetSocketAddress address) {
        DatagramPacket encode = encode(new DatagramPacket(Unpooled.buffer(96), address));
        ByteBuf encodedBuf = encode.content();
        System.out.println(encodedBuf.readableBytes());

        DatagramPacket encapsulated = new DatagramPacket(Unpooled.buffer(99),address);
        ByteBuf content = encapsulated.content();
        content.writeByte(0x00);
        content.writeShort(encodedBuf.readableBytes());
        content.writeBytes(encodedBuf);
        ctx.writeAndFlush(encapsulated);
        System.out.println("Adios new packet.");
        System.out.println(encodedBuf.array().length);
        System.out.println(Arrays.toString(encodedBuf.array()));
        return this;
    }
}
