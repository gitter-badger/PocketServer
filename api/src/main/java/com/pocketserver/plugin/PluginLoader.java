package com.pocketserver.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

public class PluginLoader {

    private final Set<URLClassLoader> loaders = new HashSet<>();
    private final Stack<Plugin> plugins = new Stack<>();

    public Stack<Plugin> loadPlugins() {
        if (!plugins.isEmpty()) // already loaded!
            return plugins;
        File pluginDirectory = new File("plugins");
        if (!pluginDirectory.exists()) {
            pluginDirectory.mkdir();
            return new Stack<>(); // No plugins so do not bother
        }

        File[] files = pluginDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".jar");
            }
        });
        Set<Plugin> found = new HashSet<>();
        for (File file : files) {
            Set<Plugin> plgs = loadPlugins(file);
            if (plgs.size() > 0) {
                System.out.format("Found %d plugins in %s: %s\n", plgs.size(), file.getName(), plgs.stream().map(p -> p.getName()).collect(Collectors.toList()));
                found.addAll(plgs);
            }
        }
        Map<String, Integer> duplicates = new HashMap<>();
        Map<String, Plugin> names = new HashMap<>();
        Map<String, Boolean> loaded = new HashMap<>();
        Map<String, Set<String>> deps = new WeakHashMap<>();
        found.forEach(p -> {
            if (names.containsKey(p.getName())) {
                duplicates.put(p.getName(), duplicates.getOrDefault(p.getName(), 1) + 1);
            } else {
                names.put(p.getName(), p);
                loaded.put(p.getName(), false);
                deps.put(p.getName(), Sets.newHashSet(p.getDependencies()));
            }
        });
        duplicates.forEach((s, i) -> {
            System.err.format("Found %d occurrences of plugin %s. Not loading any.\n", i, s);
        });
        found.removeIf(p -> duplicates.containsKey(p.getName()));
        Set<String> circular = new HashSet<>();
        for (Plugin p : found) {
            String pn = p.getName();
            if (circular.contains(pn))
                continue;
            for (Plugin q : found) {
                String qn = q.getName();
                if (circular.contains(qn))
                    continue;
                if (deps.get(pn).contains(qn) && deps.get(qn).contains(pn)) {
                    circular.add(pn);
                    circular.add(qn);
                    if (pn.equals(qn)) {
                        System.err.format("Error loading plugin %s: Depends on itself.\n", pn);
                    } else {
                        System.err.format("Error loading plugins %s and %s: Depend on eachother.\n", pn, qn);
                    }
                }
            }
        }
        found.removeIf(p -> circular.contains(p.getName()));
        int numLoaded;
        do {
            numLoaded = 0;
            List<Plugin> nodeps = found.stream().filter(p -> deps.get(p.getName()).isEmpty()).collect(Collectors.toList());
            for (Plugin p : nodeps) {
                try {
                    found.remove(p);
                    p.onEnable();
                    deps.forEach((s, l) -> {
                        l.remove(p.getName());
                        deps.put(s, l);
                    });
                    plugins.add(p);
                    numLoaded++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } while (numLoaded > 0);
        found.forEach(p -> {
            System.err.format("Error loading plugin %s: Dependencies missing: %s\n", p.getName(), deps.get(p.getName()).toString());
        });
        return plugins;
    }

    private Set<Plugin> loadPlugins(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            URLClassLoader cl = new URLClassLoader(new URL[] { file.toURI().toURL() });
            loaders.add(cl);
            Set<Plugin> found = new HashSet<>();
            Enumeration<JarEntry> entries = jarFile.entries();
            double highest = 0;
            while (entries.hasMoreElements()) {
                String entry = entries.nextElement().getName();
                if (entry.endsWith(".class")) {
                    try {
                        Class<?> clazz = cl.loadClass(entry.replace('/', '.').substring(0, entry.length() - 6));
                        if (Plugin.class.isAssignableFrom(clazz)) {
                            try {
                                found.add(clazz.asSubclass(Plugin.class).newInstance());
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
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
            if (highest == 0)
                if (found.isEmpty())
                    System.err.format("No classes extending Plugin found in file %s. Not loading.\n", file.getName());
                else {
                    found.forEach(p -> p.file = file);
                    return found;
                }
            else {
                String version;
                if (highest == 52.0)
                    version = "Java 8";
                else if (highest == 53.0)
                    version = "Java 9";
                else
                    version = "an unsupported version of Java";
                System.err.format("Not loading plugins in file %s because it requires %s.\n", file.getName(), version);
            }
            cl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    public void disablePlugins() {
        while (plugins.size() > 0) {
            Plugin plg = plugins.pop();
            plg.onDisable();
        }
        plugins.clear();
        loaders.clear();
    }

}
