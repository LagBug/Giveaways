package me.lagbug.giveaways.spigot.commands;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.commands.subcommand.Cancel;
import me.lagbug.giveaways.spigot.commands.subcommand.Create;
import me.lagbug.giveaways.spigot.commands.subcommand.Edit;
import me.lagbug.giveaways.spigot.commands.subcommand.Enter;
import me.lagbug.giveaways.spigot.commands.subcommand.ForceEnd;
import me.lagbug.giveaways.spigot.commands.subcommand.List;
import me.lagbug.giveaways.spigot.commands.subcommand.Reload;
import me.lagbug.giveaways.spigot.common.commands.SpigotCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class GiveawayCommand extends SpigotCommand {

	public GiveawayCommand() {
		super(Permissions.USE, 1, new Edit(), new Enter(), new Reload(), new Create(), new List(), new ForceEnd(), new Cancel());
		super.setUsage("/giveaway");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
