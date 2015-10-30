package com.pocketserver.net.packets.login;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID({ 0x1C, 0x1D })
public class UnconnectedPongPacket extends OutPacket {

    private final long id;

    protected UnconnectedPongPacket(long id) {
        this.id = id;
    }

    @Override
    public DatagramPacket encode(DatagramPacket buf) {
        buf.content().writeInt(this.getPacketID());
        buf.content().writeLong(id);
        buf.content().writeLong(TEMP_SERVERID);
        writeMagic(buf.content());
        writeString(buf.content(), TEMP_IDENTIFIER);
        return buf;
    }
    
}