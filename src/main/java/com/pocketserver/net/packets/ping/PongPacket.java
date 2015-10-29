package com.pocketserver.net.packets.ping;

import com.pocketserver.net.Packet;

public class PongPacket extends Packet {
    protected PongPacket() {
        super(0x03);
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {

    }
}