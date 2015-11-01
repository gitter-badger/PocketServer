package com.pocketserver.impl.net.packets.login.connect;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x05)
public class OpenConnectionRequestAPacket extends InPacket {

	private byte proto;
	private int mtu;
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		ByteBuf buf = dg.content();
		long magic1 = buf.readLong();
		long magic2 = buf.readLong();
        if (magic1 == Packet.MAGIC_1 && magic2 == Packet.MAGIC_2) {
        	proto = buf.readByte();
        	mtu = buf.readableBytes();
        	System.out.println("Proto = " + proto + ", MTU = " + mtu);
        	if (proto == Protocol.RAKNET_VERSION) {
				System.out.println("Sent?");
        		new OpenConnectionReplyAPacket(mtu).sendLogin(ctx, dg.sender());
        	} else {
        		new IncompatibleProtocolPacket().sendLogin(ctx, dg.sender());
        	}
        }
	}

}