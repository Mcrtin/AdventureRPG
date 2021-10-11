package io.github.riesenpilz.adventureRPG.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.riesenpilz.adventureRPG.worldGenerator.Generator;

public class CreateLvlCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		WorldCreator worldCreator = new WorldCreator("test");
		worldCreator.generator(new Generator());
		worldCreator.seed(Long.valueOf(args[0]));
		Bukkit.createWorld(worldCreator);
		return false;
	}

}
