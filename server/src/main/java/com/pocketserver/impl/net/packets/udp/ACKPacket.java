package com.pocketserver.impl.net.packets.udp;

import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xC0)
public class ACKPacket extends Packet {
    private short unknown;
    private boolean additionalPacket;
    private byte[] packetNumber;
    private byte[] packetNumberTwo;

    public ACKPacket() {}

    public ACKPacket(Packet packet) {

    }

    public ACKPacket(Packet packet,Packet packet2) {
        
    }

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        ByteBuf content = dg.content();
        unknown = content.readShort();
        additionalPacket = content.readBoolean();
        packetNumber = new byte[3];
        for (int i = 0; i < 3; i++) {
            packetNumber[i] = content.readByte();
        }

        if (additionalPacket) {
            packetNumberTwo = new byte[3];
            for (int i = 0; i < 3; i++) {
                packetNumberTwo[i] = content.readByte();
            }
        }
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        System.out.println("The client lost the packet: " + dg.content().readByte());
        return dg;
    }

    public short getUnknown() {
        return unknown;
    }

    public boolean isAdditionalPacket() {
        return additionalPacket;
    }

    public byte[] getPacketNumber() {
        return packetNumber;
    }

    public byte[] getPacketNumberTwo() {
        return packetNumberTwo;
    }
}
