package com.pocketserver.example.plugin;

import com.pocketserver.plugin.Dependency;
import com.pocketserver.plugin.Description;
import com.pocketserver.plugin.Name;
import com.pocketserver.plugin.Plugin;

@Name("Example")
@Description("I'm the lowest dependency out there!")
@Dependency("BaseExamplePlugin")
public class BaseExamplePlugin extends Plugin {
	
	@Override
	public void onEnable() {
		System.out.println("[Base] Hello, there!");
	}

}
