package io.github.riesenpilz.adventureRPG;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.riesenpilz.adventureRPG.commands.CreateLvlCommand;
import io.github.riesenpilz.adventureRPG.listeners.WorldListener;

public class Main extends JavaPlugin {
	private static JavaPlugin plugin;

	public void onEnable() {
		plugin = this;
		getCommand("createlvl").setExecutor(new CreateLvlCommand());
		Bukkit.getPluginManager().registerEvents(new WorldListener(), plugin);
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
