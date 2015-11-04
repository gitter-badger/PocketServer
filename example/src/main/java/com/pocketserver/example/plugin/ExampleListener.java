package com.pocketserver.example.plugin;

import com.pocketserver.event.Listener;
import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.player.Player;

public class ExampleListener {

    private ExamplePlugin plg;

    ExampleListener(ExamplePlugin plg) {
        this.plg = plg;
    }

    @Listener // This would be called on the PlayerChatEvent
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer(); // Get the player
        if (player.getHealth() != 20) { // Check if they do not have full health
            player.setHealth(20); // Set it to full health
        }
        plg.getLogger().info("Found a chat event: {} {}", player.getName(), event.getMessage());
    }

}
