package com.pocketserver.example.plugin;

import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.player.Player;
import com.pocketserver.plugin.Dependency;
import com.pocketserver.plugin.Description;
import com.pocketserver.plugin.Name;
import com.pocketserver.plugin.Plugin;

@Name("Example")
@Description("An example plugin showing off what can be done!")
@Dependency("BaseExamplePlugin")
public class ExamplePlugin extends Plugin {
	
    @Override
    public void onEnable() {
		System.out.println("[Example] Hello, there!");
    	getEventBus().registerListener(this, new ExampleListener());
    	
    	new Thread(() -> {
    		try {
    			Thread.sleep(500);
    		} catch (Exception ex) {}
    		getEventBus().post(new PlayerChatEvent(new Player(0, null).setName("PlayerName"), "ChatMessage"));
    	}).start();
    }
    
}
