package com.pocketserver.impl.net.packets.login;

import com.pocketserver.impl.net.OutPacket;
import com.pocketserver.impl.net.PacketID;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0x10)
public class ServerHandshakePacket extends OutPacket {

    private long timestamp;

    public ServerHandshakePacket(long timestamp) {
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
        /*
        ByteBuf content = dg.content();
        content.writeByte(getPacketID());
        writeDataArray(content);
        content.writeByte(0x4A);
        content.writeByte(0xBC);
        content.writeLong(timestamp); // https://github.com/NiclasOlofsson/MiNET/blob/master/src/MiNET/MiNET/Net/Package.cs
        content.writeLong(new Date().getTime());
        return dg;
        */
        ByteBuf content = dg.content();
        content.writeByte(getPacketID());
        content.writeInt(0x043f57fe);
        content.writeByte(0xcd);
        content.writeShort(19132);
        writeDataArray(content);
        content.writeByte(0x00);
        content.writeByte(0x00);
        content.writeLong(timestamp);
        content.writeBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x04, 0x44, 0x0b, (byte) 0xa9});
        return dg;
    }

    private void writeDataArray(ByteBuf buf) {
        byte[] unknown1 = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE };
        byte[] unknown2 = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

        this.writeTriad(buf, unknown1.length);
        buf.writeBytes(unknown1);
        for (int i = 0; i < 9; i++)
        {
            this.writeTriad(buf,unknown2.length);
            buf.writeBytes(unknown2);
        }
    }

    public void writeTriad(ByteBuf buf,int i) {
        byte b1 = (byte)((i >> 16) & 0xFF);
        byte b2 = (byte)((i >> 8) & 0xFF);
        byte b3 = (byte)(i & 0xFF);

        buf.writeByte(b1);
        buf.writeByte(b2);
        buf.writeByte(b3);
    }
}
