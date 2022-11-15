package me.lagbug.giveaways.spigot.commands.subcommand;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.Giveaways;
import me.lagbug.giveaways.spigot.common.commands.SubCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class Reload extends SubCommand {

	private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public Reload() {
		super("reload:r", Permissions.RELOAD);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		plugin.reloadFiles();
		plugin.initiate();
		
		sender.sendMessage(plugin.getMessage("reloaded"));
	}

}
