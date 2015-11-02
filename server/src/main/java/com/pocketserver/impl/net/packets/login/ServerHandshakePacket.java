package com.pocketserver.impl.net.packets.login;

import java.util.Date;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.PacketID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x10)
public class ServerHandshakePacket extends OutPacket {

    private final long guid;
    private long timestamp;

    public ServerHandshakePacket(long guid, long timestamp) {
        this.guid = guid;
        this.timestamp = timestamp;
    }

    // Hypixel: 84 00 00 00 40 02 F0 01 00 00 10 04 80 FF FF FE 4A BC 04 80 FF
    // FF FE 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A
    // BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF
    // FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 00 00 00 00 00
    // 69 97 2C 00 00 39 D3 DA D4 46 CB
    // Ours : 84 00 00 00 40 02 F0 01 00 5E 10 04 80 FF FF FE 4A BC 04 80 FF FF
    // FE 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC
    // 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF
    // FF FF 4A BC 04 FF FF FF FF 4A BC 04 FF FF FF FF 4A BC 00 00 00 00 00 69
    // 97 2C 00 00 01 50 C8 BB 32 F4

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        ByteBuf content = dg.content();
        content.writeByte(getPacketID());
        writeDataArray(content);
        content.writeByte(0x4A);
        content.writeByte(0xBC);
        content.writeLong(timestamp); // https://github.com/NiclasOlofsson/MiNET/blob/master/src/MiNET/MiNET/Net/Package.cs
        content.writeLong(new Date().getTime());
        return dg;
    }

    private void writeDataArray(ByteBuf buf) {
        byte[] unknown1 = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE };
        byte[] unknown2 = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        buf.writeByte(unknown1.length);
        buf.writeBytes(unknown1);
        buf.writeByte(0x4A);
        buf.writeByte(0xBC);
        buf.writeByte(unknown1.length);
        buf.writeBytes(unknown1);
        for (int i = 0; i < 9; i++) {
            buf.writeByte(0x4A);
            buf.writeByte(0xBC);
            buf.writeByte(unknown2.length);
            buf.writeBytes(unknown2);
        }
    }
}
