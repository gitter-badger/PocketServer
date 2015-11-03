package com.pocketserver.example.plugin;

import com.pocketserver.plugin.Plugin;
import com.pocketserver.plugin.PluginData;

@PluginData(name = "BaseExample", version = "1.0.0", description = "A low example dependency")
public class BaseExamplePlugin extends Plugin {

    @Override
    public void onEnable() {
        System.out.println("[Base] Hello, there!");
    }

}
