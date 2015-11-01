package com.pocketserver.net.packets.login;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x82)
public class LoginPacket extends InPacket {
    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        ByteBuf content = dg.content();
        String name = readString(content);
        System.out.println("Username: " + name);
    }
}
