package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;
import com.pocketserver.net.PacketManager;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xA0)
public class NotAcknowledgedPacket extends Packet {

	private int unknown, pkt_id1, pkt_id2;
	private boolean single;
	
	private NotAcknowledgedPacket(int unknown, int pkt_id1, int pkt_id2, boolean single) {
		this.pkt_id1 = pkt_id1;
		this.pkt_id2 = pkt_id2;
		this.single = single;
	}
	
	public static NotAcknowledgedPacket one(int unknown, int pkt_id1) {
		return new NotAcknowledgedPacket(unknown, pkt_id1, -1, true);
	}
	
	public static NotAcknowledgedPacket two(int unknown, int pkt_id1, int pkt_id2) {
		return new NotAcknowledgedPacket(unknown, pkt_id1, pkt_id2, false);
	}
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		unknown = dg.content().readShort();
		single = dg.content().readBoolean();
		pkt_id1 = dg.content().readMedium();
		pkt_id2 = single ? -1 : dg.content().readMedium();
		Packet pkt1 = PacketManager.getInstance().getSavedPacket(pkt_id1);
		Packet pkt2 = PacketManager.getInstance().getSavedPacket(pkt_id2);
		if (pkt1 != null)
			ctx.writeAndFlush(pkt1.encode(new DatagramPacket(Unpooled.buffer(), dg.sender())));
		if (pkt2 != null)
			ctx.writeAndFlush(pkt2.encode(new DatagramPacket(Unpooled.buffer(), dg.sender())));
	}

	@Override
	public DatagramPacket encode(DatagramPacket dg) {
		dg.content().writeByte(getPacketID());
		dg.content().writeShort(unknown);
		dg.content().writeBoolean(single);
		dg.content().writeMedium(pkt_id1);
		if (!single)
			dg.content().writeMedium(pkt_id2);
		return dg;
	}

}
