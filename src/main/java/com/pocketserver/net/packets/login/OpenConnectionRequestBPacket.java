package com.pocketserver.net.packets.login;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x07)
public class OpenConnectionRequestBPacket extends InPacket {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		ByteBuf buf = dg.content();
        if (buf.readLong() == Packet.MAGIC_1 && buf.readLong() == Packet.MAGIC_2) {
        	byte sec = buf.readByte();
        	int cookie = buf.readInt();
        	short port = buf.readShort();
        	short mtu = buf.readShort();
        	long clientID = buf.readLong();
        	
        	OpenConnectionReplyBPacket reply = new OpenConnectionReplyBPacket(mtu, dg.sender().getPort());
        	ctx.flush();
        }
	}

}
