package com.pocketserver.net;

import java.util.HashMap;
import java.util.Map;

public class PacketManager {
    private static final PacketManager INSTANCE = new PacketManager();
    private final Map<Integer,Packet> packetIds = new HashMap<>();

    public static PacketManager getInstance() {
        return INSTANCE;
    }

    public Packet getPacket(int id) {
        return packetIds.get(id);
    }
}