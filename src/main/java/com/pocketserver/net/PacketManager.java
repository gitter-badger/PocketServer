package com.pocketserver.net;

import java.util.HashMap;
import java.util.Map;

import com.pocketserver.net.packets.login.UnconnectedPingPacket;
import com.pocketserver.net.packets.login.UnconnectedPongPacket;
import com.pocketserver.net.packets.ping.PingPacket;
import com.pocketserver.net.packets.ping.PongPacket;

public class PacketManager {
	
    private static final PacketManager INSTANCE = new PacketManager();

    public static PacketManager getInstance() {
        return INSTANCE;
    }
    
    private final Map<Integer, Class<? extends Packet>> packetIds = new HashMap<>();

    {
    	register(PingPacket.class);
    	register(PongPacket.class);

    	register(UnconnectedPingPacket.class);
    	register(UnconnectedPongPacket.class);
    }
    
    void register(Class<? extends Packet> clazz) {
    	PacketID id = clazz.getAnnotation(PacketID.class);
    	if (id != null)
    		packetIds.put(id.value(), clazz);
    }
    
    public Class<? extends Packet> getPacketClass(int id) {
        return packetIds.get(id);
    }

	public Packet createPacket(int id) {
		Class<? extends Packet> clazz = getPacketClass(id);
		Packet pack = null;
		try {
			pack = clazz.getConstructor().newInstance();
		} catch (Exception ex) {}
		if (pack == null) {
			try {
				pack = clazz.newInstance();
			} catch (Exception ex) {}
		}
		return pack;
	}
}