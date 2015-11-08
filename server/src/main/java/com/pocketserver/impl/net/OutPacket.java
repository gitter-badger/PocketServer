package com.pocketserver.impl.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public abstract class OutPacket extends Packet {

    @Override
    public final void decode(DatagramPacket dg, ChannelHandlerContext ctx) {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " is an OutPacket, so does not support decoding.");
    }

    protected final void writeMagic(ByteBuf buf) {
        buf.writeLong(Protocol.MAGIC_1);
        buf.writeLong(Protocol.MAGIC_2);
    }
}
