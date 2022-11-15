package me.lagbug.giveaways.spigot.commands.subcommand;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.common.commands.SubCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class Edit extends SubCommand {
	
	//private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public Edit() {
		super("edit", Permissions.EDIT);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		/*
		 * boolean found = false;
		 * 
		 * if (args.length <= 1) {
		 * player.sendMessage(main.getMessage("wrongUsage").replace("%usage%",
		 * "/ga edit <id>")); return; }
		 * 
		 * for (Giveaway ga : main.getGiveaways()) { if (ga.getId().equals(args[1])) {
		 * if (main.getGiveaways().contains(ga)) { GiveawayEditEvent gee = new
		 * GiveawayEditEvent(ga, player);
		 * 
		 * if (gee.isCancelled()) { return; }
		 * 
		 * new EditGui(player, ga); } found = true; break; } }
		 * 
		 * if (!found) {
		 * player.sendMessage(main.getMessage("giveawayNotFound").replace("%id%",
		 * args[1])); }
		 */
	}

}
