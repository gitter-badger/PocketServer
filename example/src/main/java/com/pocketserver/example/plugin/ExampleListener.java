package com.pocketserver.example.plugin;

import com.pocketserver.event.Listener;
import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.player.Player;

public class ExampleListener {

    @Listener // This would be called on the PlayerChatEvent
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer(); // Get the player
        if (player.getHealth() != 20) { // Check if they do not have full health
            player.setHealth(20); // Set it to full health
        }
        System.out.format("[ExamplePlugin] Found a chat event: %s %s\n", player.getName(), event.getMessage());
    }

}
