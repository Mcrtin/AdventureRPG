package io.github.riesenpilz.adventureRPG.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class WorldListener implements Listener {
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
}
