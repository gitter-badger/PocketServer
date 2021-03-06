package com.pocketserver.impl.net.packets.oldudp;

import java.net.InetSocketAddress;

import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xC0)
public class AcknowledgedPacketOld extends Packet {

    private int unknown, pkt_id1, pkt_id2;
    private boolean single;

    public AcknowledgedPacketOld() {
    } // no-args for decoding

    private AcknowledgedPacketOld(int unknown, int pkt_id1, int pkt_id2, boolean single) {
        this.pkt_id1 = pkt_id1;
        this.pkt_id2 = pkt_id2;
        this.single = single;
    }

    public static AcknowledgedPacketOld one(int unknown, int pkt_id1) {
        return new AcknowledgedPacketOld(unknown, pkt_id1, -1, true);
    }

    public static AcknowledgedPacketOld two(int unknown, int pkt_id1, int pkt_id2) {
        return new AcknowledgedPacketOld(unknown, pkt_id1, pkt_id2, false);
    }

    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
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

    @Override
    public Packet sendPacket(ChannelHandlerContext ctx, InetSocketAddress addr) {
        super.sendPacket(ctx, addr);
        System.out.format("ACK send: unknown = %d, pkt_id1 = %d, pkt_id2 = %d\n", unknown, pkt_id1, pkt_id2);
        return this;
    }

}
