package io.github.riesenpilz.adventureRPG;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.riesenpilz.adventureRPG.commands.CreateLvlCommand;
import io.github.riesenpilz.adventureRPG.listeners.WorldListener;
import io.github.riesenpilz.adventureRPG.utils.FakePlayer.PlayerListener;
import io.github.riesenpilz.adventureRPG.utils.TempText.TempTextListener;

public class Main extends JavaPlugin {
	private static JavaPlugin plugin;

	public void onEnable() {
		plugin = this;
		getCommand("createlvl").setExecutor(new CreateLvlCommand());
		final PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new WorldListener(), plugin);
		pluginManager.registerEvents(new PlayerListener(), plugin);
		pluginManager.registerEvents(new TempTextListener(), plugin);
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
