package com.pocketserver.impl.net;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.pocketserver.impl.net.packets.udp.CustomPacket;
import com.pocketserver.impl.net.packets.udp.CustomPacket.EncapsulationStrategy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public abstract class Packet {

    protected static final long TEMP_SERVER_ID = 0x00000000372cdc9e;
    protected static final String TEMP_IDENTIFIER = "MCPE;§cSurvival §dGames§e! ;34;0.12.3; 0;20;20";

    protected static final Pattern DISALLOWED_CHARS = Pattern.compile("[^" + Pattern.quote(" !\"§#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~") + "]");

    protected static final long MAGIC_1 = 0x00ffff00fefefefeL;
    protected static final long MAGIC_2 = 0xfdfdfdfd12345678L;
    private final int id;

    protected Packet() {
        id = getClass().getAnnotation(PacketID.class).value()[0];
    }

    protected Packet(int id) {
        this.id = id;
    }

    public final Object field(String name) {
        try {
            Field f = getClass().getField(name);
            if (!f.isAccessible())
                f.setAccessible(true);
            return f.get(this);
        } catch (Exception ex) {
            return null;
        }
    }

    public final Packet field(String name, Object obj) {
        try {
            Field f = getClass().getField(name);
            if (!f.isAccessible())
                f.setAccessible(true);
            f.set(this, obj);
        } catch (Exception ex) {
        }
        return this;
    }

    public final void writeMagic(ByteBuf buf) {
        buf.writeLong(MAGIC_1);
        buf.writeLong(MAGIC_2);
    }

    public final void writeString(ByteBuf buf, String str) {
        Preconditions.checkNotNull(str, "Cannot write a null string.");
        str = DISALLOWED_CHARS.matcher(str).replaceAll("");
        buf.writeShort(str.length());
        buf.writeBytes(str.getBytes(Charset.defaultCharset()));
    }

    public final String readString(ByteBuf buf) {
        short len = buf.readShort();
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return new String(bytes, Charset.defaultCharset());
    }

    public final int getPacketID() {
        return this.id;
    }

    public Packet sendLogin(ChannelHandlerContext ctx, InetSocketAddress addr) {
        System.out.println("Sending login packet " + getClass().getSimpleName());
        ctx.writeAndFlush(encode(new DatagramPacket(Unpooled.buffer(), addr)));
        return this;
    }

    public Packet sendGame(int customPacketId, EncapsulationStrategy strat, int count, int unk, ChannelHandlerContext ctx, InetSocketAddress address) {
        System.out.println("Sending game packet " + getClass().getSimpleName());
        new CustomPacket(customPacketId, strat, count, unk, this).sendLogin(ctx, address);
        return this;
    }

    public abstract void decode(ChannelHandlerContext ctx, DatagramPacket dg);

    public abstract DatagramPacket encode(DatagramPacket dg);
}