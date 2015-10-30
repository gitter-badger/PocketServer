package com.pocketserver.net.packets.message;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xB5)
public class ChatPacket extends InPacket {
	
    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
    	String message = readString(dg.content());
    }
    
}
