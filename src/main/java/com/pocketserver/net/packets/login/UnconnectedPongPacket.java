package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;

import com.pocketserver.net.PacketID;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x1C)
public class UnconnectedPongPacket extends Packet {

    private final long id;

    protected UnconnectedPongPacket(long id) {
        this.id = id;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {

    }

    @Override
    public DatagramPacket encode(DatagramPacket buf) {
        buf.content().writeInt(this.getPacketID());
        buf.content().writeLong(id);
        System.out.println("Encoding this, " + buf.content().duplicate().readInt());
        return buf;
    }
}