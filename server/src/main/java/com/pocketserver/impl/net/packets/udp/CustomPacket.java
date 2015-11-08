package com.pocketserver.impl.net.packets.udp;

import com.pocketserver.impl.net.Packet;
import com.pocketserver.impl.net.PacketID;
import com.pocketserver.impl.net.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID({ 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x86, 0x87, 0x88, 0x89, 0x8A, 0x8B, 0x8C, 0x8D, 0x8E, 0x8F })
public class CustomPacket extends Packet {
    private static int counter = 0;

    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        ByteBuf content = dg.content();

        counter =
                content.readMedium();
        byte encapsulation = content.readByte();
        short packet_bits = content.readShort();
        short packet_bytes = (short) (packet_bits / 8);

        EncapsulationStrategy strategy = EncapsulationStrategy.getById(encapsulation);
        DatagramPacket packet = new DatagramPacket(content,dg.recipient(),dg.sender());
        if (strategy != null) {
            strategy.decode(ctx, packet,packet_bytes);
        }
        System.out.println(strategy != null ? strategy.name() : null);
        System.out.println(String.valueOf(encapsulation));
        String sid = String.valueOf(encapsulation);
        System.out.format("0x%s\n", sid.length() == 1 ? "0" + sid : sid);
    }

    /*
    @Override
    public void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        ByteBuf content = dg.content();
        int len = content.readableBytes();
        System.out.println(len);
        content.readByte();
        int i = content.readMedium();
        System.out.println("Ctr: " + i);

        len = content.readableBytes();
        System.out.println(len);
        byte[] packet = new byte[len-4];
        ByteBuf readBytes = content.readBytes(packet);

        byte id = readBytes.readByte();

        EncapsulationStrategy strategy = EncapsulationStrategy.getById(id);
        if (strategy == null) {
            strategy = EncapsulationStrategy.BARE;
            String sid = String.format("%X", id);
            System.out.format("Packet1 received: 0x%s\n", sid.length() == 1 ? "0" + sid : sid);
        }
        String sid = String.format("%X", id);
        System.out.format("Packet2 received: 0x%s\n", sid.length() == 1 ? "0" + sid : sid);
        if (strategy != null) {
            DatagramPacket decode = new DatagramPacket(readBytes, dg.recipient(), dg.sender());
            strategy.decode(ctx,decode);
        }
    }
    */

    @Override
    public DatagramPacket encode(DatagramPacket dg) {
        return null;
    }

    enum EncapsulationStrategy {
        BARE(0x00) {
            /*
            @Override
            void decode(ChannelHandlerContext ctx,DatagramPacket packet) {
                ByteBuf content = packet.content();
                byte packetId = content.readByte();
                Packet savedPacket = PacketManager.getInstance().getSavedPacket(packetId);
                if (savedPacket == null) {
                    String sid = String.valueOf(packetId);
                    System.out.format("Packet not received: 0x%s\n", sid.length() == 1 ? "0" + sid : sid);
                }
                savedPacket.decode(new DatagramPacket(content,packet.recipient(),packet.sender()),ctx);
            }
            */

            @Override
            void decode(ChannelHandlerContext ctx, DatagramPacket packet,int bytes) {
                byte packet_id = packet.content().readByte();
                byte[] packet_data = new byte[bytes + 1];
                String sid = String.valueOf(packet_id);
                System.out.format("Packet received: 0x%s\n", sid.length() == 1 ? "0" + sid : sid);
                System.out.println(name());
            }
        },
        COUNT(0x40) {
            @Override
            void decode(ChannelHandlerContext ctx, DatagramPacket packet, int bytes) {
                ByteBuf content = packet.content();
                content.readBytes(3);
                byte packetId = content.readByte();

                byte[] data = new byte[bytes + 1];
                String sid = String.valueOf(packetId);
                System.out.format("Packet received: 0x%s\n", sid.length() == 1 ? "0" + sid : sid);

                Packet savedPacket = PacketManager.getInstance().initializePacketById(packetId);
                System.out.println("Saved " + savedPacket);
                System.out.println(name());
            }
        },
        COUNT_UNKNOWN(0x60) {
            @Override
            void decode(ChannelHandlerContext ctx, DatagramPacket packet, int bytes) {
                ByteBuf content = packet.content();
                content.readBytes(7);
                byte packetId = content.readByte();

                byte[] data = new byte[bytes + 1];
                String sid = String.valueOf(packetId);
                System.out.format("Packet received: 0x%s\n", sid.length() == 1 ? "0" + sid : sid);

                Packet savedPacket = PacketManager.getInstance().initializePacketById(packetId);
                System.out.println("Saved " + savedPacket);
                System.out.println(name());
            }
        };

        private final int id;

        EncapsulationStrategy(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static EncapsulationStrategy getById(int id) {
            for (EncapsulationStrategy encapsulationStrategy : values())
                if (encapsulationStrategy.id == id) return encapsulationStrategy;
            return null;
        }

        abstract void decode(ChannelHandlerContext ctx, DatagramPacket packet, int bytes);
    }
}
