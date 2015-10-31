package com.pocketserver.net;

import java.util.HashMap;
import java.util.Map;

import com.pocketserver.net.packets.connect.ClientConnectPacket;
import com.pocketserver.net.packets.login.OpenConnectionRequestAPacket;
import com.pocketserver.net.packets.login.OpenConnectionRequestBPacket;
import com.pocketserver.net.packets.login.UnconnectedPingPacket;
import com.pocketserver.net.packets.message.ChatPacket;
import com.pocketserver.net.packets.ping.PingPacket;
import com.pocketserver.net.packets.udp.AcknowledgedPacket;
import com.pocketserver.net.packets.udp.CustomPacket;
import com.pocketserver.net.packets.udp.NotAcknowledgedPacket;

public class PacketManager {
	
    private static final PacketManager INSTANCE = new PacketManager();

    public static PacketManager getInstance() {
        return INSTANCE;
    }

    private final Map<Integer, Class<? extends Packet>> loginPacketIds = new HashMap<>();
    private final Map<Integer, Class<? extends Packet>> gamePacketIds = new HashMap<>();
    
    private final Map<Integer, Packet> sentPackets = new HashMap<>();
    private int lastSent = 0;

    {
    	registerLoginPacket(PingPacket.class);
    	registerLoginPacket(UnconnectedPingPacket.class);
    	registerLoginPacket(OpenConnectionRequestAPacket.class);
    	registerLoginPacket(OpenConnectionRequestBPacket.class);
    	registerLoginPacket(CustomPacket.class);
    	registerLoginPacket(AcknowledgedPacket.class);
    	registerLoginPacket(NotAcknowledgedPacket.class);
    	
    	registerGamePacket(ChatPacket.class);
    	registerGamePacket(ClientConnectPacket.class);
    }
    
    void registerLoginPacket(Class<? extends Packet> clazz) {
    	PacketID id = clazz.getAnnotation(PacketID.class);
    	if (id != null)
    		for (int i : id.value())
    			loginPacketIds.put(i, clazz);
    }
    
    public Class<? extends Packet> getLoginPacketClass(int id) {
        return loginPacketIds.get(id);
    }

	public Packet createLoginPacket(int id) {
		Class<? extends Packet> clazz = getLoginPacketClass(id);
		if (clazz == null)
			return null;
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
    
    void registerGamePacket(Class<? extends Packet> clazz) {
    	PacketID id = clazz.getAnnotation(PacketID.class);
    	if (id != null)
    		for (int i : id.value())
    			gamePacketIds.put(i, clazz);
    }
    
    public Class<? extends Packet> getGamePacketClass(int id) {
        return gamePacketIds.get(id);
    }

	public Packet createGamePacket(int id) {
		Class<? extends Packet> clazz = getGamePacketClass(id);
		if (clazz == null)
			return null;
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
	
	public void save(Packet packet) {
		sentPackets.put(this.lastSent++, packet);
	}

	public int getSentAmount() {
		return lastSent;
	}

	public Packet getSavedPacket(int id) {
		return sentPackets.get(id);
	}
	
}