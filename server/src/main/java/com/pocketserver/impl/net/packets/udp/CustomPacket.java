package com.pocketserver.impl.net.packets.udp;

import com.google.common.base.Preconditions;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import com.pocketserver.impl.net.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import java.util.Arrays;

@PacketID({ 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x86, 0x87, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8D, 0x8E, 0x8F })
public class CustomPacket extends Packet {
    private static int counter = 0;

    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
        ByteBuf content = dg.content();
        for (int i = 0; i < 4; i++) content.readByte();
        int len = content.readableBytes();
        byte[] packet = new byte[len-4];

        ByteBuf readBytes = content.readBytes(packet);
        byte id = readBytes.readByte();
        Packet savedPacket = PacketManager.getInstance().getSavedPacket(id);
        if (savedPacket == null) {
            System.out.println(Arrays.toString(PacketManager.getInstance().getPackets().keySet().toArray()));
            return;
        }
        savedPacket.decode(ctx,new DatagramPacket(readBytes,dg.sender()));
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        return null;
    }
}
