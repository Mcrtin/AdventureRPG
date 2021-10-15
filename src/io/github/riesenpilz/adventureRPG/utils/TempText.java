package io.github.riesenpilz.adventureRPG.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import io.github.riesenpilz.nmsUtilities.entity.DataWatcherItem;
import io.github.riesenpilz.nmsUtilities.entity.Entity;
import io.github.riesenpilz.nmsUtilities.entity.livingEntity.ArmorStand;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityDestroyEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutEntityMetadataEvent;
import io.github.riesenpilz.nmsUtilities.packet.playOut.PacketPlayOutSpawnLivingEntityEvent;

public class TempText implements Listener {

	public static final List<TempText> ExsistingTempTexts = new ArrayList<>();

	private String text;
	private int distanceToPlayer;
	private final Location location;
	private boolean exsist = false;
	private final int entityId = Entity.getNewEntityId();
	private final UUID uuid = UUID.randomUUID();

	public TempText(String text, int distanceToPlayer, Location location, boolean exsist) {
		this.text = text;
		this.distanceToPlayer = distanceToPlayer;
		this.location = location;
		this.exsist = exsist;
	}

	public void setDistanceToPlayer(int distanceToPlayer) {
		this.distanceToPlayer = distanceToPlayer;
	}

	/**
	 * doest update emediently.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	public static List<TempText> getExsistingtemptexts() {
		return ExsistingTempTexts;
	}

	public String getText() {
		return text;
	}

	public int getDistanceToPlayer() {
		return distanceToPlayer;
	}

	public Location getLocation() {
		return location;
	}

	public boolean isExsist() {
		return exsist;
	}

	public int getEntityId() {
		return entityId;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void spawn() {
		exsist = true;
		ExsistingTempTexts.add(this);
	}

	public static class TempTextListener implements Listener {
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent e) {
			List<Integer> removals = new ArrayList<>();
			final Player player = e.getPlayer();
			for (TempText tempText : TempText.ExsistingTempTexts) {
				final Location to = e.getTo();
				if (!to.getWorld().equals(tempText.getLocation().getWorld()))
					return;
				if (e.getFrom().distance(tempText.getLocation()) > tempText.getDistanceToPlayer()) {
					if (to.distance(tempText.getLocation()) <= tempText.getDistanceToPlayer()) {

						new PacketPlayOutSpawnLivingEntityEvent(player, tempText.getDistanceToPlayer(),
								tempText.getUuid(), tempText.getLocation(), EntityType.ARMOR_STAND, new Vector(), 0F)
										.sendToClient();

						List<DataWatcherItem<?>> dataWatcherItems = new ArrayList<>();
						dataWatcherItems.add(ArmorStand.getDWArmorStandStates(true, false, true, true));
						dataWatcherItems.add(ArmorStand.getDWHasNoGravity(true));
						dataWatcherItems.add(ArmorStand.getDWStates(false, false, false, false, true, false));
						dataWatcherItems.add(ArmorStand
								.getDWCustomName(Optional.of(CraftChatMessage.fromStringOrNull(tempText.getText()))));
						dataWatcherItems.add(ArmorStand.getDWIsCustomNameVisible(true));
						new PacketPlayOutEntityMetadataEvent(player, tempText.getEntityId(), dataWatcherItems)
								.sendToClient();
					}
					continue;
				}
				if (to.distance(tempText.getLocation()) > tempText.getDistanceToPlayer())
					removals.add(tempText.getEntityId());
			}
			if (!removals.isEmpty())
				new PacketPlayOutEntityDestroyEvent(player, removals.stream().mapToInt(i -> i).toArray());
		}

		@EventHandler
		public void onPlayerTP(PlayerTeleportEvent e) {
			List<Integer> removals = new ArrayList<>();
			final Player player = e.getPlayer();
			for (TempText tempText : TempText.ExsistingTempTexts) {
				final Location to = e.getTo();
				final Location location = tempText.getLocation();
				final World world = location.getWorld();
				final World toWorld = to.getWorld();
				final int distanceToPlayer = tempText.getDistanceToPlayer();
				final Location from = e.getFrom();
				final World fromWorld = from.getWorld();

				if ((!fromWorld.equals(world) || from.distance(location) > distanceToPlayer)
						&& to.distance(location) <= distanceToPlayer && toWorld.equals(world)) {

					new PacketPlayOutSpawnLivingEntityEvent(player, distanceToPlayer, tempText.getUuid(), location,
							EntityType.ARMOR_STAND, new Vector(), 0F).sendToClient();
					List<DataWatcherItem<?>> dataWatcherItems = new ArrayList<>();
					dataWatcherItems.add(ArmorStand.getDWArmorStandStates(true, false, true, true));
					dataWatcherItems.add(ArmorStand.getDWHasNoGravity(true));
					dataWatcherItems.add(ArmorStand.getDWStates(false, false, false, false, true, false));
					dataWatcherItems.add(ArmorStand
							.getDWCustomName(Optional.of(CraftChatMessage.fromStringOrNull(tempText.getText()))));
					dataWatcherItems.add(ArmorStand.getDWIsCustomNameVisible(true));
					new PacketPlayOutEntityMetadataEvent(player, tempText.getEntityId(), dataWatcherItems)
							.sendToClient();
					continue;
				}

				if (fromWorld.equals(world) && from.distance(location) <= distanceToPlayer
						&& (to.distance(location) > distanceToPlayer || !toWorld.equals(world)))
					removals.add(tempText.getEntityId());
			}
			if (!removals.isEmpty())
				new PacketPlayOutEntityDestroyEvent(player, removals.stream().mapToInt(i -> i).toArray());
		}
	}
}
