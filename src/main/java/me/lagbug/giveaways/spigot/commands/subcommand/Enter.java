package me.lagbug.giveaways.spigot.commands.subcommand;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.common.commands.SubCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class Enter extends SubCommand {

	//private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public Enter() {
		super("enter:e", Permissions.ENTER);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		/*
		 * boolean found = false;
		 * 
		 * if (args.length <= 1) {
		 * player.sendMessage(main.getMessage("wrongUsage").replace("%usage%",
		 * "/ga enter <id>")); return; }
		 * 
		 * for (Giveaway ga : main.getGiveaways()) { if (ga.getId().equals(args[1]) &&
		 * !ga.getEntered().contains(player.getUniqueId().toString())) {
		 * GiveawayEnterEvent gee = new GiveawayEnterEvent(ga, player);
		 * Bukkit.getPluginManager().callEvent(gee);
		 * 
		 * if (gee.isCancelled()) { return; }
		 * 
		 * if (!player.hasPermission(ga.getPermission())) {
		 * player.sendMessage(main.getMessage("noPermissions")); return; }
		 * 
		 * player.sendMessage(main.getMessage("entered"));
		 * ga.getEntered().add(player.getUniqueId().toString());
		 * Utils.log(player.getName() + " has entered " + ga.getId() + " giveaway"); }
		 * else { player.sendMessage(main.getMessage("alreadyEntered")); }
		 * 
		 * found = true; break; }
		 * 
		 * if (!found) {
		 * player.sendMessage(main.getMessage("giveawayNotFound").replace("%id%",
		 * args[1])); }
		 */
	}

}
