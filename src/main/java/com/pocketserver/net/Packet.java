package com.pocketserver.net;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public abstract class Packet {
	
	public static final long TEMP_SERVERID = 0x00000000372cdc9eL;
	public static final String TEMP_IDENTIFIER = "MCPE;Steve;2 7;0.11.0;0;20";
	
	public static final Pattern ALLOWED_CHARS = Pattern.compile("[" + Pattern.quote("!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~") + "]");
	public static final Pattern DISALLOWED_CHARS = Pattern.compile("[^" + Pattern.quote("!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~") + "]");
	public static final long MAGIC_1 = 0x00ffff00fefefefeL;
	public static final long MAGIC_2 = 0xfdfdfdfd12345678L;
	
	public final void writeMagic(ByteBuf buf) {
		buf.writeLong(MAGIC_1);
		buf.writeLong(MAGIC_2);
	}

	public final void writeString(ByteBuf buf, String str) {
		buf.writeShort(str.length());
		str = DISALLOWED_CHARS.matcher(str).replaceAll("");
		buf.writeBytes(str.getBytes(StandardCharsets.US_ASCII));
	}
	
	public final String readString(ByteBuf buf) {
		short len = buf.readShort();
		byte[] bytes = new byte[len];
		buf.readBytes(bytes);
		return new String(bytes, StandardCharsets.US_ASCII);
	}
	
    public final int getPacketID() {
    	PacketID id = getClass().getAnnotation(PacketID.class);
    	return id == null ? -1 : id.value()[0];
    }
    
    public abstract void decode(ChannelHandlerContext ctx, DatagramPacket dg);
    public abstract DatagramPacket encode(DatagramPacket buf);

}