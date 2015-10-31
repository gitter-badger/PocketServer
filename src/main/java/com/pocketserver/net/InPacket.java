package com.pocketserver.net;

import io.netty.channel.socket.DatagramPacket;

public abstract class InPacket extends Packet {

	@Override
	public final DatagramPacket encode(DatagramPacket dg) {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " is an InPacket, so does not support encoding.");
	}

}
