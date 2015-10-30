package com.pocketserver.net.packets.login;

import com.pocketserver.net.InPacket;
import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x05)
public class OpenConnectionRequestAPacket extends InPacket {

	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		ByteBuf buf = dg.content();
        if (buf.readLong() == Packet.MAGIC_1 && buf.readLong() == Packet.MAGIC_2) {
        	byte proto = buf.readByte();
        	int mtu = 0;
        	while (buf.readableBytes() > 0 && buf.readByte() == 0x00)
        		mtu++;
        	if (proto == Protocol.RAKNET) {
        		ctx.write(new OpenConnectionReplyAPacket(mtu).encode(new DatagramPacket(Unpooled.buffer(), dg.sender())));
        	} else {
        		ctx.write(new IncompatibleProtocolPacket().encode(new DatagramPacket(Unpooled.buffer(), dg.sender())));
        	}
        	ctx.flush();
        }
	}

}