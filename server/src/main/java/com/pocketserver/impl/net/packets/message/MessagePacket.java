package com.pocketserver.impl.net.packets.message;

import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x85)
public class MessagePacket extends Packet {

    private String message;

    public MessagePacket(String message) {
        this.message = message;
    }

    public MessagePacket() {
    } // no-args for decoding

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        message = readString(dg.content());
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        dg.content().writeByte(getPacketID());
        writeString(dg.content(), message);
        return dg;
    }

}
