package com.pocketserver.net.netty;

import java.util.Arrays;

import com.pocketserver.net.Packet;
import com.pocketserver.net.PacketManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class PocketServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		System.out.println("Sender: " + msg.sender().getAddress().getHostAddress());
		System.out.println("Message: " + Arrays.toString(msg.content().array()));
		
		ByteBuf buf = msg.content();
		Packet packet = PacketManager.getInstance().createPacket(buf.readByte());
		if (packet != null) // if null, then there's no packet with that id!
			packet.decode(ctx, new DatagramPacket(buf.copy(1, buf.readableBytes()).resetReaderIndex(), msg.recipient(), msg.sender()));
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
	}

}
