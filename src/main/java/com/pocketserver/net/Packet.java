package com.pocketserver.net;

public abstract class Packet {
    private final int packetId;

    protected Packet(int packetId) {
        this.packetId = packetId;
    }

    public int getPacketId() {
        return packetId;
    }

    public abstract void decode();
    public abstract void encode();

}