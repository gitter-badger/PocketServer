package com.pocketserver.example.plugin;

import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.player.Player;
import com.pocketserver.plugin.Plugin;

public class ExamplePlugin extends Plugin {
	
    @Override
    public void onEnable() {
    	getEventBus().registerListener(this, new ExampleListener());
    	
    	new Thread(() -> {
    		try {
    			Thread.sleep(500);
    		} catch (Exception ex) {}
    		getEventBus().post(new PlayerChatEvent(new Player(0, null).setName("PlayerName"), "ChatMessage"));
    	}).start();
    }
    
}
