package com.pocketserver.impl.net.packets.message;

import com.pocketserver.impl.net.InPacket;
import com.pocketserver.impl.net.PacketID;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

@PacketID(0xB5)
public class ChatPacket extends InPacket {
	
    @Override
    public void decode(ChannelHandlerContext ctx, DatagramPacket dg) {
    	String message = readString(dg.content());
        /*Player player = PlayerRegistry.get().getPlayer(dg.sender());
        if (player == null)
        	return;
        PlayerChatEvent event = new PlayerChatEvent(player, message);
        PocketServer.getServer().getEventBus().post(event);
        if (event.isCancelled())
        	return;
        player.chat(event.getMessage());
        */
    }
    
}
