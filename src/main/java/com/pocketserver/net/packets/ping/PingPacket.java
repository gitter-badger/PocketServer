package com.pocketserver.net.packets.ping;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x00)
public class PingPacket extends InPacket {

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        PongPacket pong = new PongPacket(dg.content().readLong());
        ctx.write(pong.encode(new DatagramPacket(Unpooled.buffer(), dg.sender())));
        ctx.flush();
    }
}