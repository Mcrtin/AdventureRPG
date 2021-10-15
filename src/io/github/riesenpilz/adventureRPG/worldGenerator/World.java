package io.github.riesenpilz.adventureRPG.worldGenerator;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import io.github.riesenpilz.nmsUtilities.world.ServerWorld;

public class World extends ServerWorld {

	protected World(org.bukkit.World world) {
		super(world);
	}

	public static World getWorld(String name, long seed) {
		final WorldCreator creator = new WorldCreator(name);
		creator.generator(new Generator(seed, 0));
		creator.seed(seed);
		return new World(Bukkit.createWorld(creator));
	}

}
