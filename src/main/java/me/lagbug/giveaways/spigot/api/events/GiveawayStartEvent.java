package me.lagbug.giveaways.spigot.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.lagbug.giveaways.spigot.utils.Giveaway;

public class GiveawayStartEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isCancelled;
	private Giveaway giveaway;
	private Player player;
	
	public GiveawayStartEvent(Giveaway giveaway, Player player) {
		this.giveaway = giveaway;
		this.isCancelled = false;
		this.player = player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
		
	}

	public Giveaway getGiveaway() {
		return this.giveaway;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	
	
}
