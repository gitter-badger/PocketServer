package com.pocketserver.net.packets.message;

import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x85)
public class MessagePacket extends Packet {

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {

    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        return null;
    }
}
