package com.pocketserver.example.listener;

import com.pocketserver.event.Listener;
import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.player.Player;
import com.pocketserver.plugin.Plugin;

public class ListenerExample extends Plugin {
    @Override
    public void onEnable() {
        getEventBus().registerListener(this, new ListenerExample());
    }

    @Listener //This would be called on the PlayerChatEvent
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer(); //Get the player
        if (player.getHealth() != 20) { //Check if they do not have full health
            player.setHealth(20); //Set it to full health
        }
    }
}
