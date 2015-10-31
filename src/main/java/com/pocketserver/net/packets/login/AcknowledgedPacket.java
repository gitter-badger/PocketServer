package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xC0)
public class AcknowledgedPacket extends Packet {

	private int unknown, pkt_id1, pkt_id2;
	private boolean single;
	
	private AcknowledgedPacket(int unknown, int pkt_id1, int pkt_id2, boolean single) {
		this.pkt_id1 = pkt_id1;
		this.pkt_id2 = pkt_id2;
		this.single = single;
	}
	
	public static AcknowledgedPacket one(int unknown, int pkt_id1) {
		return new AcknowledgedPacket(unknown, pkt_id1, -1, true);
	}
	
	public static AcknowledgedPacket two(int unknown, int pkt_id1, int pkt_id2) {
		return new AcknowledgedPacket(unknown, pkt_id1, pkt_id2, false);
	}
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		unknown = dg.content().readShort();
		single = dg.content().readBoolean();
		pkt_id1 = dg.content().readMedium();
		pkt_id2 = single ? -1 : dg.content().readMedium();
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
