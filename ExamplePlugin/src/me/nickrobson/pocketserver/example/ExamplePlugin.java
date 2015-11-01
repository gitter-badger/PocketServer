package me.nickrobson.pocketserver.example;

import com.pocketserver.plugin.Plugin;

public class ExamplePlugin extends Plugin {
	
	public void onEnable() {
		System.out.println("Hello world!");
	}
	
	public void onDisable() {
		System.out.println("Awww... bye...");
	}

}
