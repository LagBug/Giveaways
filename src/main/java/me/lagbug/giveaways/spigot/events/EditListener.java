package me.lagbug.giveaways.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.lagbug.giveaways.spigot.Giveaways;
import me.lagbug.giveaways.spigot.common.utils.util.CommonUtils;
import me.lagbug.giveaways.spigot.utils.Giveaway;

public class EditListener implements Listener {

	private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		String msg = e.getMessage();

		if (!plugin.getEditors().containsKey(player)) {
			return;
		}

		Giveaway ga = plugin.getEditors().get(player);
		e.setCancelled(true);

		if (msg.equalsIgnoreCase("cancel")) {
			// ab.send(player, false, plugin.getMessage("cancelled"));
			plugin.getEditors().remove(player);
			return;
		}

		/*
		 * switch (ga.getPhase()) {
		 * 
		 * case ZERO: ga.setId(msg); break; case ONE: try {
		 * ga.setWinners(Integer.parseInt(msg)); } catch (NumberFormatException ex) {
		 * //ab.send(player, true, plugin.getMessage("error").replace("%error%",
		 * "The amount given is not a number")); return; } break; case TWO: try {
		 * ga.setMaterial(Material.valueOf(msg.toUpperCase().replace(" ", "_"))); }
		 * catch (IllegalArgumentException ex) { //ab.send(player, true,
		 * plugin.getMessage("error").replace("%error%",
		 * "No material with that name could be found")); return; } break; case THREE:
		 * ga.setLore(msg); break; case FOUR: if (plugin.getTime(msg) == 0) {
		 * //ab.send(player, true, plugin.getMessage("error").replace("%error%",
		 * "The amount given is incorrect")); return; }
		 * 
		 * ga.setDate(plugin.getTime(msg) * 1000L); break; case FIVE: if
		 * (msg.contains(", ")) { ga.setRewards(Arrays.asList(msg.split(", "))); } else
		 * { List<String> r = new ArrayList<>(); r.add(msg); ga.setRewards(r); } break;
		 * 
		 * default: break; }
		 */

		saveGiveaway(ga);
		// ab.send(player, false, plugin.getMessage("edited"));
		plugin.getEditors().remove(player);
		CommonUtils.log(ga.getId() + " has been edited.");
	}

	private void saveGiveaway(Giveaway ga) {

		/*
		 * gafile.set("giveaways." + ga.getId() + ".id", ga.getId());
		 * gafile.set("giveaways." + ga.getId() + ".permission", ga.getPermission());
		 * gafile.set("giveaways." + ga.getId() + ".material", ga.getMaterial().name());
		 * gafile.set("giveaways." + ga.getId() + ".lore", ga.getLore());
		 * gafile.set("giveaways." + ga.getId() + ".winners",
		 * Integer.valueOf(ga.getWinners())); gafile.set("giveaways." + ga.getId() +
		 * ".entered", ga.getEntered()); gafile.set("giveaways." + ga.getId() + ".date",
		 * ga.getDate()); gafile.set("giveaways." + ga.getId() + ".rewards",
		 * ga.getRewards()); plugin.saveGiveawaysFile();
		 */

		plugin.reloadGiveaways();
		plugin.startGiveawayTasks();
	}
}
