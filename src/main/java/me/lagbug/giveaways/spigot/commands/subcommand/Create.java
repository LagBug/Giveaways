package me.lagbug.giveaways.spigot.commands.subcommand;

import org.bukkit.command.CommandSender;

import me.lagbug.giveaways.spigot.common.commands.SubCommand;
import me.lagbug.giveaways.spigot.utils.Permissions;

public class Create extends SubCommand {

	//private final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public Create() {
		super("create:c", Permissions.CREATE);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		/*
		 * if (args.length <= 1) {
		 * player.sendMessage(main.getMessage("wrongUsage").replace("%usage%",
		 * "/ga create <id>")); return; }
		 * 
		 * if (main.getCreators().containsKey(player)) {
		 * player.sendMessage(main.getMessage("alreadyCreating")); return; }
		 * 
		 * Giveaway ga = new Giveaway(); ga.setId(args[1]); ga.setPhase(Phase.ZERO);
		 * 
		 * main.getCreators().put(player, ga); main.getActionbar().send(player, true,
		 * main.getMessage("phaseZero").replace("%name%", ga.getId()));
		 */
	}

}
