package com.pocketserver.impl.net.packets.login;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.PacketID;
import com.pocketserver.impl.net.packets.udp.CustomPacket.EncapsulationStrategy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x09)
public class ClientConnectPacket extends InPacket {

    long clientId, session;
    byte unknown;

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
       // System.out.println("0x09 is a decode packet.");
        clientId = dg.content().readLong();
        session = dg.content().readLong();
        unknown = dg.content().readByte();

        ctx.writeAndFlush(new ServerHandshakePacket(session));
        //.sendGame(0x84, EncapsulationStrategy.COUNT, 0x02F001, 0, ctx, dg.sender());
    }

}
