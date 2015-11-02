package com.pocketserver.impl.net.packets.login.connect;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;

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
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        ByteBuf buf = dg.content();
        if (buf.readLong() == Packet.MAGIC_1 && buf.readLong() == Packet.MAGIC_2) {
            sec = buf.readByte();
            cookie = buf.readInt();
            port = buf.readShort();
            mtu = buf.readShort();
            clientId = buf.readLong();

            new OpenConnectionReplyBPacket(mtu, dg.sender().getPort()).sendLogin(ctx, dg.sender());
        }
    }

}
