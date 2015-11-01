package com.pocketserver.net.packets.login.connect;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;

import io.netty.channel.socket.DatagramPacket;

@PacketID({ 0x1C, 0x1D })
public class UnconnectedPongPacket extends OutPacket {

	private final int packetId;
    private final long id;

    protected UnconnectedPongPacket(long id) {
        this.packetId = getPacketID();
        this.id = id;
    }

    protected UnconnectedPongPacket(int packetId, long id) {
    	this.packetId = (packetId == 0x1C || packetId == 0x1D) ? packetId : 0x1C;
        this.id = id;
    }

    @Override
    public DatagramPacket encode(DatagramPacket buf) {
        buf.content().writeByte(packetId);
        buf.content().writeLong(id);
        buf.content().writeLong(TEMP_SERVERID);
        writeMagic(buf.content());
        writeString(buf.content(), TEMP_IDENTIFIER);
        System.out.println(TEMP_IDENTIFIER);
        return buf;
    }
    
}