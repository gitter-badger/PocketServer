package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;

public class UnconnectedPingPacket extends Packet {
    protected UnconnectedPingPacket() {
        super(0x01);
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {

    }
}