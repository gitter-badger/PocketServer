package com.pocketserver.impl.net;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.pocketserver.impl.exception.InvalidPacketException;
import com.pocketserver.impl.net.packets.login.*;
import com.pocketserver.impl.net.packets.login.connect.OpenConnectionRequestAPacket;
import com.pocketserver.impl.net.packets.login.connect.OpenConnectionRequestBPacket;
import com.pocketserver.impl.net.packets.login.connect.UnconnectedPingPacket;
import com.pocketserver.impl.net.packets.message.ChatPacket;
import com.pocketserver.impl.net.packets.oldudp.AcknowledgedPacketOld;
import com.pocketserver.impl.net.packets.oldudp.NotAcknowledgedPacketOld;
import com.pocketserver.impl.net.packets.ping.PingPacket;
import com.pocketserver.impl.net.packets.udp.ACKPacket;
import com.pocketserver.impl.net.packets.udp.CustomPacket;
import com.pocketserver.impl.net.packets.udp.NACKPacket;
import io.github.jython234.jraklibplus.protocol.raknet.AcknowledgePacket;

public class PacketManager {

    private static final PacketManager INSTANCE = new PacketManager();
    public static PacketManager getInstance() {
        return INSTANCE;
    }

    private final Map<Byte, Class<? extends Packet>> packetIds = new HashMap<>();

    {
        registerPacket(UnconnectedPingPacket.class);
        registerPacket(OpenConnectionRequestAPacket.class);
        registerPacket(OpenConnectionRequestBPacket.class);
        registerPacket(CustomPacket.class);
        registerPacket(ACKPacket.class);
        registerPacket(NACKPacket.class);

        registerPacket(PingPacket.class);
        registerPacket(ChatPacket.class);
        registerPacket(ClientConnectPacket.class);
        registerPacket(ClientHandshakePacket.class);
        registerPacket(ClientCancelConnectPacket.class);
        registerPacket(LoginInfoPacket.class);
    }

    public void registerPacket(Class<? extends Packet> packet) {
        Preconditions.checkNotNull(packet);
        PacketID id = packet.getDeclaredAnnotation(PacketID.class);
        if (id == null) {
            throw new InvalidPacketException("All packets must be annotated with @PacketID.",packet);
        }
        for (int i : id.value()) {
            System.out.println(packet.getName() + " === " + i);
            Preconditions.checkNotNull(packetIds);
            packetIds.put((byte) i,packet);
        }
    }

    public Class<? extends Packet> getPacketById(byte id) {
        return packetIds.get(id);
    }

    public Packet initializePacketById(byte id) {
        return initializePacket(getPacketById(id));
    }

    public Packet initializePacket(Class<? extends Packet> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*

    private Map<Object, Object> packets;
    private final Map<Byte, Class<? extends Packet>> loginPacketIds = new HashMap<>();
    private final Map<Byte, Class<? extends Packet>> gamePacketIds = new HashMap<>();

    private final Map<Integer, Packet> sentPackets = new HashMap<>();
    private int lastSent = 0;

    {
        registerLoginPacket(UnconnectedPingPacket.class);
        registerLoginPacket(OpenConnectionRequestAPacket.class);
        registerLoginPacket(OpenConnectionRequestBPacket.class);
        registerLoginPacket(CustomPacket.class);
        registerLoginPacket(ACKPacket.class);
        registerLoginPacket(NACKPacket.class);

        registerGamePacket(PingPacket.class);
        registerGamePacket(ChatPacket.class);
        registerGamePacket(ClientConnectPacket.class);
        registerGamePacket(ClientHandshakePacket.class);
        registerGamePacket(ClientCancelConnectPacket.class);
        registerGamePacket(LoginInfoPacket.class);
    }

    void registerLoginPacket(Class<? extends Packet> clazz) {
        PacketID id = clazz.getAnnotation(PacketID.class);
        if (id != null)
            for (int i : id.value())
                loginPacketIds.put((byte) i, clazz);
    }

    public Class<? extends Packet> getLoginPacketClass(byte id) {
        return loginPacketIds.get(id);
    }

    public Packet createLoginPacket(byte id) {
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
        return pack;
    }

    void registerGamePacket(Class<? extends Packet> clazz) {
        PacketID id = clazz.getAnnotation(PacketID.class);
        if (id != null)
            for (int i : id.value())
                gamePacketIds.put((byte) i, clazz);
    }

    public Class<? extends Packet> getGamePacketClass(byte id) {
        return gamePacketIds.get(id);
    }

    public Packet createGamePacket(byte id) {
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
        return pack;
    }

    public int save(Packet packet) {
        if (packet == null)
            return -1;
        int id = lastSent++;
        sentPackets.put(id, packet);
        System.out.println("Saved " + packet.getClass().getSimpleName() + " as " + id);
        return id;
    }

    public int getSentAmount() {
        return lastSent;
    }

    public Packet getSavedPacket(int id) {
        return sentPackets.get(id);
    }

    public Map<Byte, Class<? extends Packet>> getPackets() {
        return gamePacketIds;
    }
    */
}