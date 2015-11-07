package com.pocketserver.impl.net.packets.login.connect;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;

import com.pocketserver.impl.net.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x01)
public class UnconnectedPingPacket extends InPacket {

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        ByteBuf content = dg.content();
        UnconnectedPongPacket packet = new UnconnectedPongPacket(0x1C, content.readLong());
        if (content.readLong() == Protocol.MAGIC_1 && content.readLong() == Protocol.MAGIC_2)
            packet.sendLogin(ctx, dg.sender());
    }

}