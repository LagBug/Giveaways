package me.lagbug.giveaways.spigot.common.utils.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.lagbug.giveaways.spigot.Giveaways;
import me.lagbug.giveaways.spigot.common.utils.util.CommonUtils;
import me.lagbug.giveaways.spigot.utils.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class UpdateChecker {

	private Giveaways plugin;
	private int projectID;
	private String newVersion, currentVersion;
	private URL url;
	
	public UpdateChecker(Giveaways plugin, int projectID) {
		this.projectID = projectID;
		this.currentVersion = plugin.getDescription().getVersion();
		this.plugin = plugin;
		
		try {
			url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
		} catch (MalformedURLException ex) { 
			return;
		}
	}
	
	/* 
	 * Asynchronously schedules an updater. 
	 * If an update is found, all online
	 * players who are opped will be notified.
	 * 
	 * Delay time unit is minutes.
	*/	
	public void schedule(int delay) {
		new BukkitRunnable() {
			@Override
			public void run() {
				UpdateResult result = getResult();
				plugin.updateResult = result;
				switch (result) {
				case FOUND:
					CommonUtils.forceLog("Found a new update! Download it using " + "https://www.spigotmc.org/resources/" + projectID + "/ to stay updated");
					Bukkit.getOnlinePlayers().forEach(player -> {
						if (player.isOp() || player.hasPermission(Permissions.UPDATE)) {
							TextComponent msg = new TextComponent(plugin.getMessage("general.updateAvailable"));
							msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/" + projectID + "/"));
							player.spigot().sendMessage(msg);
						}
					});
					break;
				default:
					break;
				}
				
			}
		}.runTaskTimerAsynchronously(plugin, 0, delay * 1200);
	
	}
	
    private UpdateResult getResult() {
    	try {
         	URLConnection con = url.openConnection();
            this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            
        	int currentV = Integer.parseInt(currentVersion.replace(".", ""));
        	int newV = Integer.parseInt(newVersion.replace(".", "").replace("-", ""));
            
            if (newV > currentV) {
            	return UpdateResult.FOUND;
            } else if (newV < currentV) {
            	return UpdateResult.DEVELOPMENT;
            }
            return UpdateResult.NOT_FOUND;
            
    	} catch (IOException ex) {
    		return UpdateResult.ERROR;
    	}
    }
    
    public int getProjectID() {
    	return projectID;
    }
    
    public String getCurrentVersion() {
    	return currentVersion;
    }
    
    public String getNewVersion() {
    	return newVersion;
    }
    
    public enum UpdateResult {
    	ERROR, FOUND, NOT_FOUND, DEVELOPMENT

    }
}