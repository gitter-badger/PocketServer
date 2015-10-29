package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class UnconnectedPingPacket extends Packet {
	
    protected UnconnectedPingPacket() {}

    @Override
    public void decode(ChannelHandlerContext ctxt, DatagramPacket buf) {

    }

    @Override
    public void encode(ByteBuf buf) {

    }
}