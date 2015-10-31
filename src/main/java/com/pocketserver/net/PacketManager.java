package com.pocketserver.net;

import java.util.HashMap;
import java.util.Map;

import com.pocketserver.net.packets.login.OpenConnectionRequestAPacket;
import com.pocketserver.net.packets.login.OpenConnectionRequestBPacket;
import com.pocketserver.net.packets.login.UnconnectedPingPacket;
import com.pocketserver.net.packets.ping.PingPacket;

public class PacketManager {
	
    private static final PacketManager INSTANCE = new PacketManager();

    public static PacketManager getInstance() {
        return INSTANCE;
    }
    
    private final Map<Integer, Class<? extends Packet>> packetIds = new HashMap<>();

    {
    	register(PingPacket.class);
    	register(UnconnectedPingPacket.class);

    	register(OpenConnectionRequestAPacket.class);
    	register(OpenConnectionRequestBPacket.class);
    }
    
    void register(Class<? extends Packet> clazz) {
    	PacketID id = clazz.getAnnotation(PacketID.class);
    	if (id != null)
    		for (int i : id.value())
    			packetIds.put(i, clazz);
    }
    
    public Class<? extends Packet> getPacketClass(int id) {
        return packetIds.get(id);
    }

	public Packet createPacket(int id) {
		Class<? extends Packet> clazz = getPacketClass(id);
		Packet pack = null;
		try {
			pack = clazz.getConstructor().newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (pack == null) {
			try {
				pack = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pack != null) {
			pack.id = id;
		}
		return pack;
	}
}