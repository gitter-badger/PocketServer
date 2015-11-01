package com.pocketserver.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.jar.JarFile;

public class PluginLoader {
    public void loadPlugins() {
        File pluginDirectory = new File("plugins");
        if (!pluginDirectory.exists()) {
            pluginDirectory.mkdir();
            return; //No plugins so do not bother
        }

        File[] files = pluginDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".jar");
            }
        });
        for (File file : files) {
            loadPlugin(file);
        }
    }

    private void loadPlugin(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            Plugin plugin = null; //TODO load the file.

            plugin.onEnable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
