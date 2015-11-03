package com.pocketserver.example.plugin;

import com.pocketserver.plugin.Plugin;
import com.pocketserver.plugin.PluginData;

@PluginData(name = "Circular", version = "1.0.0", description = "A low example dependency", dependency = "BaseExample")
public class CircularDependencyTestPlugin extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("Enabled!");
    }

}
