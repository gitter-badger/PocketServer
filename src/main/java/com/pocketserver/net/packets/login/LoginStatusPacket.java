package com.pocketserver.net.packets.login;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x83)
public class LoginStatusPacket extends OutPacket {
    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        return null;
    }
}
