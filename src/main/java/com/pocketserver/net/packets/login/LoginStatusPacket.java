package com.pocketserver.net.packets.login;

import com.pocketserver.net.OutPacket;
import com.pocketserver.net.PacketID;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x83)
public class LoginStatusPacket extends OutPacket {
    private final int statusCode;

    public LoginStatusPacket(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        dg.content().writeInt(getPacketID());
        dg.content().writeInt(statusCode);
        return dg;
    }
}
