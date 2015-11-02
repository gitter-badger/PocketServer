package com.pocketserver.impl.net.packets.login.connect;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID(0x08)
public class OpenConnectionReplyBPacket extends OutPacket {

    private final int mtu, clientPort;

    protected OpenConnectionReplyBPacket(int mtu, int clientPort) {
        this.mtu = mtu;
        this.clientPort = clientPort;
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        dg.content().writeByte(this.getPacketID());
        System.out.println(this.getPacketID());
        System.out.println(0x08);
        writeMagic(dg.content());
        dg.content().writeLong(TEMP_SERVER_ID);
        dg.content().writeShort(clientPort);
        dg.content().writeShort(mtu);
        dg.content().writeByte(0);
        return dg;
    }

}
