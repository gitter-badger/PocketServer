package com.pocketserver.net.packets.ping;

import com.pocketserver.net.Packet;

public class PingPacket extends Packet {
    protected PingPacket() {
        super(0x00);
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {

    }
}