package com.pocketserver.net.packets.login;

import com.pocketserver.net.Packet;

public class UnconnectedPongPacket extends Packet {
    protected UnconnectedPongPacket() {
        super(0x1C);
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {

    }
}