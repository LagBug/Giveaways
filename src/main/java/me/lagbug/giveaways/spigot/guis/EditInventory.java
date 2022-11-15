package me.lagbug.giveaways.spigot.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import me.lagbug.giveaways.spigot.Giveaways;
import me.lagbug.giveaways.spigot.common.builders.CustomInventory;
import me.lagbug.giveaways.spigot.common.utils.communication.Title;

public class EditInventory extends CustomInventory {

	private static final Giveaways plugin = Giveaways.getPlugin(Giveaways.class);
	
	public EditInventory() {
		super(plugin.getFile("guis/edit.yml"));
	}

	@Override
	public void onClick(Player player, String action, ItemStack item, int slot, ClickType click) {
		switch (action) {
		case "CREATE_GIVEAWAY":
			//We destroy the current GUI
			destroy();
			//And then open the new GUI
			
			break;
		case "GOTO_LIST":
			// We destroy the current GUI
			destroy();
			
			player.closeInventory();
			
			//Send the searching title to notify the player
			Title.sendForever(player, "&bPlayer Search", "&7Enter a player name to continue");
			break;
		case "RELOAD_PLUGIN":
			// We reload the files & data
			plugin.reloadFiles();
			plugin.initiate();
			
			// And then let the player kknow it was successful
			player.sendMessage(plugin.getMessage("commands.reload.success"));
			break;
		default:
			break;
		}
	}

	@Override
	public void onUpdate(Player player) {
		//PlaceholderManager.get().forEach(key -> replace(key.getKey(), key.getValue()));
	}

	@Override
	public void onClose(Player player) {
		//Empty as the GUI will auto-destroy itself.
	}
}
