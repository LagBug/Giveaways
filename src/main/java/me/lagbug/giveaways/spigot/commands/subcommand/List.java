package me.lagbug.giveaways.spigot.commands.subcommand;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.Giveaways;
import me.lagbug.giveaways.spigot.common.commands.SubCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class List extends SubCommand {
	
	private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public List() {
		super("list:l", Permissions.LIST);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (plugin.getGiveaways().isEmpty()) {
			sender.sendMessage(plugin.getMessage("noGiveaways"));
			return;
		}
		
		//open GUI todo
	}

}
