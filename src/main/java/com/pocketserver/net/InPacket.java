package com.pocketserver.net;

import io.netty.buffer.ByteBuf;

public abstract class InPacket extends Packet {

	@Override
	public final void encode(ByteBuf buf) {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " is an InPacket, so does not support encoding.");
	}

}
