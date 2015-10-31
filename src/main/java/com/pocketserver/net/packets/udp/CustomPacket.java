package com.pocketserver.net.packets.udp;

import com.google.common.base.Preconditions;
import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketID;
import com.pocketserver.net.PacketManager;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID({ 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x86, 0x87, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8D, 0x8E, 0x8F })
public class CustomPacket extends Packet {
	
	public static enum EncapsulationStrategy {
		BARE(0x00, false, false), COUNT(0x40, true, false), COUNT_UNKNOWN(0x80, true, true);
		
		private int id;
		private boolean count, unknown;
		
		EncapsulationStrategy(int id, boolean count, boolean unknown) {
			this.id = id;
			this.count = count;
			this.unknown = unknown;
		}
		
		public static EncapsulationStrategy get(int id) {
			for (EncapsulationStrategy st : values())
				if (st.id == id)
					return st;
			return null;
		}
		
	}
	
	private int packetId = 0x80, cap_count = 0, cap_unknown = 0;
	private EncapsulationStrategy strategy;
	private Packet packet = null;
	
	private CustomPacket(int packetId, EncapsulationStrategy strategy, int cap_count, int cap_unknown, Packet packet) {
		Preconditions.checkArgument(0x80 <= packetId && packetId <= 0x8F, "Packet ID must be in range 0x80 to 0x8F for custom packets.");
		Preconditions.checkNotNull(packet, "Packet cannot be null.");
		this.packetId = packetId;
		this.strategy = strategy;
		this.cap_count = cap_count;
		this.cap_unknown = cap_unknown;
		this.packet = packet;
	}
	
	public static CustomPacket newBarePacket(int packetId, Packet packet) {
		return new CustomPacket(packetId, EncapsulationStrategy.BARE, 0, 0, packet);
	}
	
	public static CustomPacket newCountPacket(int packetId, int cap_count, Packet packet) {
		return new CustomPacket(packetId, EncapsulationStrategy.COUNT, cap_count, 0, packet);
	}
	
	public static CustomPacket newCountUnknownPacket(int packetId, int cap_count, int cap_unknown, Packet packet) {
		return new CustomPacket(packetId, EncapsulationStrategy.COUNT_UNKNOWN, cap_count, cap_unknown, packet);
	}

	public CustomPacket() {} // no-args for decoding
	
	@Override
	public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		int num = dg.content().readMedium();
		byte encapsulation = dg.content().readByte();
		short packet_bits = dg.content().readShort();
		short packet_bytes = (short) (packet_bits / 8);
		strategy = EncapsulationStrategy.get(encapsulation);
		if (strategy != null) {
			cap_count = strategy.count ? dg.content().readMedium() : 0;
			cap_unknown = strategy.unknown ? dg.content().readInt() : 0;
			byte packet_id = dg.content().readByte();
			byte[] packet_data = new byte[packet_bytes+1];
			packet_data[0] = packet_id;
			dg.content().readBytes(packet_data, 1, packet_bytes);
			packet = PacketManager.getInstance().createGamePacket(packet_id);
			if (packet != null)
				packet.decode(ctx, new DatagramPacket(Unpooled.copiedBuffer(packet_data).readerIndex(1), dg.recipient(), dg.sender()));
			ctx.writeAndFlush(AcknowledgedPacket.one(0, num).encode(new DatagramPacket(Unpooled.buffer(), dg.sender())));
		}
	}

	@Override
	public DatagramPacket encode(DatagramPacket dg) {
		PacketManager.getInstance().save(packet);
		dg.content().writeByte(packetId);
		dg.content().writeMedium(PacketManager.getInstance().getSentAmount());
		dg.content().writeByte(strategy.id);
		if (strategy.count) {
			dg.content().writeMedium(cap_count);
		}
		if (strategy.unknown) {
			dg.content().writeInt(cap_unknown);
		}
		DatagramPacket p = packet.encode(new DatagramPacket(Unpooled.buffer(), dg.recipient(), dg.sender()));
		dg.content().writeShort(p.content().writerIndex());
		dg.content().writeBytes(p.content());
		return dg;
	}

}
