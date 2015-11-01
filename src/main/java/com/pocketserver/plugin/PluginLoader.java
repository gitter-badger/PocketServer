package com.pocketserver.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
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
                return file.getName().toLowerCase().endsWith(".jar");
            }
        });
        for (File file : files) {
        	System.out.println("Found plugin file: " + file.getName());
            loadPlugin(file);
        }
    }

    private void loadPlugin(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            URLClassLoader cl = new URLClassLoader(new URL[] { file.toURI().toURL() } );
            List<Plugin> plugins = new ArrayList<>();
            Enumeration<JarEntry> entries = jarFile.entries();
            double highest = 0;
            while (entries.hasMoreElements()) {
            	String entry = entries.nextElement().getName();
            	if (entry.endsWith(".class")) {
            		try {
						Class<?> clazz = cl.loadClass(entry.replace('/', '.').substring(0, entry.length() - 6));
						if (Plugin.class.isAssignableFrom(clazz)) {
							try {
								plugins.add(clazz.asSubclass(Plugin.class).newInstance());
							} catch (InstantiationException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					} catch (UnsupportedClassVersionError error) {
						try {
							String[] words = error.getMessage().trim().split("\\s+");
							double ver = Double.parseDouble(words[words.length - 1]);
							highest = Math.max(ver, highest);
						} catch (Exception ex) {
							error.printStackTrace();
						}
					} catch (ClassNotFoundException e) {}
            	}
            }
            if (highest == 0)
            	if (plugins.isEmpty())
            		System.err.format("No classes extending Plugin found in %s. Not loading.\n", file.getName());
            	else
            		for (Plugin plugin : plugins)
            			plugin.onEnable();
            else {
            	String version;
            	if (highest == 52.0)
            		version = "Java 8";
            	else if (highest == 52.0)
            		version = "Java 9";
            	else
            		version = "an unsupported version of Java";
            	System.err.format("Not loading %s because it requires %s.\n", file.getName(), version);
            }
            cl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
