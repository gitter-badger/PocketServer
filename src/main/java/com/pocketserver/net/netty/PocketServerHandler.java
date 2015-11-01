package com.pocketserver.net.netty;

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
		ByteBuf buf = msg.content();
        byte id = buf.readByte();
        //for (int i = 0; i < buf.readableBytes(); i++)
        //	System.out.print(buf.readByte());
        //System.out.println();
        System.out.println("PacketID sent: " + id);
		Packet packet = PacketManager.getInstance().createLoginPacket(id);
		if (packet != null) {// if null, then there's no packet with that id!
			System.out.println("Received " + packet.getClass().getSimpleName());
			packet.decode(ctx, msg);
		}
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
