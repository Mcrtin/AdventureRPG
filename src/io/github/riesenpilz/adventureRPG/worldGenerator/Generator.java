package io.github.riesenpilz.adventureRPG.worldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class Generator extends ChunkGenerator {
	
	public Generator(long seed, int size) {
		
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		List<BlockPopulator> populators = new ArrayList<>();
		
		return populators;
	}
	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		final ChunkData chunkData = createChunkData(world);
		return chunkData;
	}
	
}
