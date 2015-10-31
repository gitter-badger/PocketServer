package com.pocketserver.net.packets.message;

import com.pocketserver.PocketServer;
import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.net.InPacket;
import com.pocketserver.net.PacketID;
import com.pocketserver.player.Player;
import com.pocketserver.player.PlayerRegistry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xB5)
public class ChatPacket extends InPacket {
	
	String message;
	
    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
    	message = readString(dg.content());
        Player player = PlayerRegistry.get().getPlayer(dg.sender());
        if (player == null)
        	return;
        PlayerChatEvent event = new PlayerChatEvent(player, message);
        PocketServer.getServer().getEventBus().post(event);
        if (event.isCancelled())
        	return;
        player.chat(event.getMessage());
    }
    
}
