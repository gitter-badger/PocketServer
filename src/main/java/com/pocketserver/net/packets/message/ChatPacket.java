package com.pocketserver.net.packets.message;

import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;

import com.pocketserver.player.Player;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xB5)
public class ChatPacket extends InPacket {
	
    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
    	String message = readString(dg.content());
        Player player = null;
        PlayerChatEvent event = new PlayerChatEvent(player,message);
        if (event.isCancelled()) return;
        player.chat(message);
    }
}
