package com.pocketserver.impl.net.packets.udp;

import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xA0)
public class NACKPacket extends Packet {
    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        System.out.println("The client lost the packet: " + dg.content().readByte());
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        System.out.println("We lost the packet: " + dg.content().readByte());
        return dg;
    }
}
