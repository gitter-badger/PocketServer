package com.pocketserver.example.plugin;

import com.pocketserver.event.player.PlayerChatEvent;
import com.pocketserver.impl.player.PocketPlayer;
import com.pocketserver.plugin.Dependency;
import com.pocketserver.plugin.Description;
import com.pocketserver.plugin.Name;
import com.pocketserver.plugin.Plugin;
import com.pocketserver.plugin.Version;

@Name("Example")
@Version("1.0.1")
@Description("An example plugin showing off what can be done!")
@Dependency("BaseExample")
public class ExamplePlugin extends Plugin {

    @Override
    public void onEnable() {
        System.out.println("[Example] Hello, there!");
        getEventBus().registerListener(this, new ExampleListener());

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
            getEventBus().post(new PlayerChatEvent(new PocketPlayer(0, null).setName("PlayerName"), "ChatMessage"));
        }).start();
    }

}
