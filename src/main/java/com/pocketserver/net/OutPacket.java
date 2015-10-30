package com.pocketserver.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public abstract class OutPacket extends Packet {

	@Override
	public final void decode(ChannelHandlerContext ctxt, DatagramPacket dg) {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " is an OutPacket, so does not support decoding.");
	}

}
