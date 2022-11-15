package me.lagbug.giveaways.spigot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.lagbug.giveaways.global.enums.Phase;
import me.lagbug.giveaways.spigot.Giveaways;
import me.lagbug.giveaways.spigot.common.utils.util.CommonUtils;
import net.md_5.bungee.api.ChatColor;

public class Giveaway {

	private final Giveaways main = Giveaways.getPlugin(Giveaways.class);
	private final FileConfiguration gafile = main.getFile("giveaways.yml");

	private int winners;
	private List<String> entered = new ArrayList<>(), rewards = new ArrayList<>();
	private List<OfflinePlayer> choosenWinners = new ArrayList<>();
	private long date;
	private String permission, lore, id;
	private Material material;
	private Phase phase;

	public Giveaway() {
		this.permission = "giveaways.enter.current";
		this.material = Material.GRASS;
		this.lore = "%date%%n%%hasEntered%";
		this.phase = Phase.ZERO;
		this.winners = 1;
	}

	public void end() {
		pickWinners();

		/*
		 * GiveawayEndEvent gee = new GiveawayEndEvent(this, EndReason.ENDED);
		 * Bukkit.getPluginManager().callEvent(gee);
		 * 
		 * if (gee.isCancelled()) { return; }
		 */
		
		if (!choosenWinners.isEmpty()) {
			String[] msg = main.getMessage("giveawayEnd").replace("%giveaway_name%", ChatColor.translateAlternateColorCodes('&', id)).split("%winners%");
			
			Bukkit.broadcastMessage(msg[0]);
			choosenWinners.forEach(p -> {
				Bukkit.broadcastMessage(main.getMessage("giveawayEndWinnersFormat").replace("%player%", p.getName()));
				
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.getName();
					//main.getActionbar().send(pl, false, main.getMessage("giveawayEndWinnersFormat").replace("%player%", pl.getName()));
				}
				
				if (p.isOnline()) { 
					for (String r : rewards) {
						r = r.replace("%player%", p.getName());
						
						if (r.startsWith("consolecmd ")) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), r.replace("consolecmd ", ""));
						} else if (r.startsWith("playercmd ")) {
							p.getPlayer().performCommand(r.replace("playercmd ", ""));
						}
						
					}
				}
			} );
			if (msg.length > 1) { Bukkit.broadcastMessage(msg[1]); }

		} else {
			Bukkit.broadcastMessage(main.getMessage("noEntered").replace("%giveaway_name%", ChatColor.translateAlternateColorCodes('&', id)));
		}
		
		CommonUtils.log(id + " giveaway has ended");
		
		gafile.set("giveaways." + this.id, null);
		//main.saveGiveawaysFile();
	}

	public void cancel() {
		/*
		 * GiveawayEndEvent gee = new GiveawayEndEvent(this, EndReason.CANCELLED);
		 * Bukkit.getPluginManager().callEvent(gee);
		 * 
		 * if (gee.isCancelled()) { return; }
		 */
		
		gafile.set("giveaways." + this.id, null);
		//main.saveGiveawaysFile();
	}

	private void pickWinners() {
		Random rnd = new Random();

		if (winners > entered.size()) {
			winners = entered.size();
		}

		for (int i = 0; i < winners; i++) {
			OfflinePlayer choosen = Bukkit.getOfflinePlayer(UUID.fromString(entered.get(rnd.nextInt(entered.size()))));
			if (!choosenWinners.contains(choosen)) {
				choosenWinners.add(choosen);
			}
		}
	}
	
	public long getDate() { return date;}
	public void setDate(long l) { this.date = l; }

	public List<String> getEntered() { return entered; }
	public void setEntered(List<String> entered) { this.entered = entered; }

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public int getWinners() { return winners; }
	public void setWinners(int winners) { this.winners = winners; }

	public List<String> getRewards() { return rewards; }
	public void setRewards(List<String> rewards) { this.rewards = rewards; }
	
	public String getPermission() { return permission; }
	public void setPermission(String permission) { this.permission = permission; }

	public Material getMaterial() { return material; }
	public void setMaterial(Material material) { this.material = material; }

	public String getLore() { return lore; }
	public void setLore(String lore) { this.lore = lore; }

	public Phase getPhase() { return phase; }
	public void setPhase(Phase phase) { this.phase = phase; }
	
	public List<OfflinePlayer> getChoosenWinners() { return choosenWinners; }
	
}
