package me.lagbug.giveaways.spigot.commands.subcommand;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.common.commands.SubCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class Cancel extends SubCommand {

	//private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public Cancel() {
		super("cancel:ca", Permissions.CANCEL);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {	
		
		
/*		boolean found = false;
 * 
 * if (args.length <= 1) {
			player.sendMessage(main.getMessage("wrongUsage").replace("%usage%", "/ga cancel <id>"));
			return;
		}
		
		for (Giveaway ga : main.getGiveaways()) {
			if (ga.getId().equals(args[1])) {
				if (main.getGiveaways().contains(ga)) {
					main.getGiveaways().remove(ga);
					player.sendMessage(main.getMessage("cancel").replace("%giveaway_name%", ChatColor.translateAlternateColorCodes('&', ga.getId())));
					ga.cancel();
				}
				found = true;
				break;
			}
		}
		
		if (!found) {
			player.sendMessage(main.getMessage("giveawayNotFound").replace("%id%", args[1]));
		}	*/	
	}

}
