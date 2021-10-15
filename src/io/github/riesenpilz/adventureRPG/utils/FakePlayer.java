package io.github.riesenpilz.adventureRPG.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.mojang.authlib.GameProfile;

import io.github.riesenpilz.nmsUtilities.world.ServerWorld;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;

public abstract class FakePlayer extends CraftPlayer {
	private static final List<FakePlayer> FAKE_PLAYERS = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public FakePlayer(ServerWorld world, UUID uuid, String name) {
		super((CraftServer) Bukkit.getServer(), new EntityPlayer(MinecraftServer.getServer(), world.getNMS(),
				new GameProfile(uuid, name), new PlayerInteractManager(world.getNMS())));
		FAKE_PLAYERS.add(this);
	}

	public void remove() {
		super.remove();
		FAKE_PLAYERS.remove(this);
	}
	
	public abstract void onIteract(PlayerInteractEntityEvent e);

	public static List<FakePlayer> getFakePlayers() {
		return FAKE_PLAYERS;
	}
	public static class PlayerListener implements Listener{
		@EventHandler
		public void onPlayerInteract(PlayerInteractEntityEvent e) {
			for (FakePlayer fakePlayer :FAKE_PLAYERS)
				if (fakePlayer.equals(e.getRightClicked()))
					fakePlayer.onIteract(e);
		}
	}
}
