package com.pocketserver.net.packets.login.connect;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x07)
public class OpenConnectionRequestBPacket extends InPacket {

	private byte sec;
	private int cookie;
	private short port, mtu;
	private long clientId;
	
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