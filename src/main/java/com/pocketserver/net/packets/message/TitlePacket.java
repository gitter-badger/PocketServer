package com.pocketserver.net.packets.message;

import com.pocketserver.net.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class TitlePacket extends Packet {
    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {

    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        return null;
    }
}
