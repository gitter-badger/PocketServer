package com.pocketserver.impl.net.packets.udp;

import com.google.common.base.Preconditions;
import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import com.pocketserver.impl.net.util.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.apache.commons.lang3.ArrayUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PacketID(0xC0)
public class ACKPacket extends Packet {
    private static int ctr = 0;
    private int[] packets;

    public ACKPacket(int[] packets) {
        this.packets = packets;
    }

    @SuppressWarnings("unused") //This will be used through reflection.
    public ACKPacket() {
        this(null);
    }

    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        ByteBuf content = dg.content();
        ctr++;
        int count = content.readShort();
        List<Integer> packets = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < count && !content.isReadable() && cnt < 4096; i++) {
            if (content.readByte() == 0) {
                int start = PacketUtils.readTriad(content.readBytes(new byte[3]));
                int end = PacketUtils.readTriad(content.readBytes(new byte[3]));
                if ((end - start) > 512) {
                    end = start + 512;
                }
                for (int c = start; c <= end; c++) {
                    cnt = cnt + 1;
                    packets.add(c);
                }
            } else {
                packets.add(PacketUtils.readTriad(content.readBytes(new byte[3])));
            }
        }
        this.packets = ArrayUtils.toPrimitive(packets.stream().toArray(Integer[]::new));
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        Preconditions.checkNotNull(packets);

        ByteBuf content = dg.content();
        ByteBuffer payload = ByteBuffer.allocate(1024);
        int count = packets.length;
        int records = 0;

        if (count > 0) {
            int pointer = 0;
            int start = packets[0];
            int last = packets[0];

            while (pointer + 1 < count) {
                int current = packets[pointer++];
                int diff = current - last;
                if (diff == 1) {
                    last = current;
                } else if (diff > 1) { //Forget about duplicated packets (bad queues?)
                    if (start == last) {
                        payload.put((byte) 0x01);
                        payload.put(PacketUtils.getTriad(start));
                        start = last = current;
                    } else {
                        payload.put((byte) 0x00);
                        payload.put(PacketUtils.getTriad(start));
                        payload.put(PacketUtils.getTriad(last));
                        start = last = current;
                    }
                    records = records + 1;
                }
            }

            if (start == last) {
                payload.put((byte) 0x01);
                payload.put(PacketUtils.getTriad(start));
            } else {
                payload.put((byte) 0x00);
                payload.put(PacketUtils.getTriad(start));
                payload.put(PacketUtils.getTriad(last));
            }
            records = records + 1;
        }
        content.writeShort((short) records);
        content.writeBytes(Arrays.copyOf(payload.array(), payload.position()));
        return dg;
    }

    @Override
    public Packet sendPacket(ChannelHandlerContext ctx, InetSocketAddress address) {
        System.out.println("This SHOULD be sending.");
        return super.sendPacket(ctx, address);
    }
}
    /*
    private short unknown;
    private boolean additionalPacket;
    private byte[] packetNumber;
    private byte[] packetNumberTwo;

    public ACKPacket(short unknown, boolean additionalPacket, byte[] packetNumber, byte[] packetNumberTwo) {
        this.unknown = unknown;
        this.additionalPacket = additionalPacket;
        this.packetNumber = packetNumber;
        this.packetNumberTwo = packetNumberTwo;
    }

    private byte[] count;
    private byte[] secondCount;

    public ACKPacket(byte[] count) {
        this.count = count;
    }

    public ACKPacket(byte[] count,byte[] secondCount) {
        this(count);
        this.secondCount = secondCount;
    }

    /*
    private final Packet packet;
    private final Packet packet2;
    private short unknown;
    private boolean additionalPacket;
    private byte[] packetNumber;
    private byte[] packetNumberTwo;

    public ACKPacket() {
        this(null);
    }

    public ACKPacket() {

    }

    public ACKPacket(Packet packet) {
        this(packet,null);
    }

    public ACKPacket(Packet packet,Packet packet2) {
        this.packet = packet;
        this.packet2 = packet2;
        this.additionalPacket = packet2 != null;
    }


    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        System.out.println("The client lost the packet.");
        ByteBuf content = dg.content();
        unknown = content.readShort();
        additionalPacket = content.readBoolean();
        packetNumber = new byte[3];
        for (int i = 0; i < 3; i++) {
            packetNumber[i] = content.readByte();
        }

        if (additionalPacket) {
            packetNumberTwo = new byte[3];
            for (int i = 0; i < 3; i++) {
                packetNumberTwo[i] = content.readByte();
            }
        }
    }

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        ByteBuf content = dg.content();
        content.writeByte(getPacketID());
        content.writeShort(0);
        content.writeBoolean(secondCount == null);
        content.writeBytes(count);
        if (secondCount != null) {
            content.writeBytes(secondCount);
        }
        return dg;
    }

    public short getUnknown() {
        return unknown;
    }

    public boolean isAdditionalPacket() {
        return additionalPacket;
    }

    public byte[] getPacketNumber() {
        return packetNumber;
    }

    public byte[] getPacketNumberTwo() {
        return packetNumberTwo;
    }
}
*/
