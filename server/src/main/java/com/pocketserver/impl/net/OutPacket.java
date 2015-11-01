package com.pocketserver.impl.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public abstract class OutPacket extends Packet {

	@Override
	public final void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " is an OutPacket, so does not support decoding.");
	}

}
