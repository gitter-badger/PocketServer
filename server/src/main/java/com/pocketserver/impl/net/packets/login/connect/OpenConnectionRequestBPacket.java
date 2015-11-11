package com.pocketserver.impl.net.packets.login.connect;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.PacketID;

import com.pocketserver.impl.net.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x07)
public class OpenConnectionRequestBPacket extends InPacket {

    byte sec;
    int cookie;
    short port, mtu;
    long clientId;

    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        ByteBuf buf = dg.content();
        if (buf.readLong() == Protocol.MAGIC_1 && buf.readLong() == Protocol.MAGIC_2) {
            sec = buf.readByte();
            cookie = buf.readInt();
            port = buf.readShort();
            mtu = buf.readShort();
            clientId = buf.readLong();

            new OpenConnectionReplyBPacket(mtu, dg.sender().getPort()).sendPacket(ctx, dg.sender());
        }
    }

}
