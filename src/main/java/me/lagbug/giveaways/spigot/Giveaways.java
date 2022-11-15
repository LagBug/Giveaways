package me.lagbug.giveaways.spigot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.lagbug.giveaways.spigot.commands.GiveawayCommand;
import me.lagbug.giveaways.spigot.common.utils.general.Metrics;
import me.lagbug.giveaways.spigot.common.utils.general.MySQL;
import me.lagbug.giveaways.spigot.common.utils.general.UpdateChecker;
import me.lagbug.giveaways.spigot.common.utils.general.UpdateChecker.UpdateResult;
import me.lagbug.giveaways.spigot.common.utils.util.CommonUtils;
import me.lagbug.giveaways.spigot.common.utils.util.FileUtils;
import me.lagbug.giveaways.spigot.events.EditListener;
import me.lagbug.giveaways.spigot.utils.Giveaway;

public class Giveaways extends JavaPlugin {
	
	private List<Giveaway> giveaways = new ArrayList<>();
	private long lastServerClose;
	
	private FileUtils fileUtils = new FileUtils();
	private FileConfiguration configFile, langFile, giveawaysFile;

	private String user = "%%__USER__%%";
	
	public UpdateResult updateResult;
	public boolean mysql;
	
	private Map<Player, Giveaway> editors, creators = new HashMap<>();
	
	@Override
	public void onEnable() {
		// We register our only command
		getCommand("giveaways").setExecutor(new GiveawayCommand());

		// We register our events as well
		Bukkit.getPluginManager().registerEvents(new EditListener(), this);
		
		// We initiate everything that's required
		initiate();

		CommonUtils.forceLog(
				getDescription().getName() + " v" + getDescription().getVersion() + " has been enabled successfully",
				"Plugin licensed to [https://www.spigotmc.org/members/" + user + "/]");
		// We register the bStats Metrics
		new Metrics(this);

		// If the update checker is enabled, we schedule it
		if (configFile.getBoolean("updateChecker")) {
			new UpdateChecker(this, 66184).schedule(600);
		}
	}
	
	@Override
	public void onDisable() {
		for (Giveaway ga : giveaways) {
			List<String> entered = ga.getEntered();
			giveawaysFile.set("giveaways." + ga.getId() + ".entered", entered);
		}
		giveawaysFile.set("lastServerClose", System.currentTimeMillis());
		//saveGiveawaysFile();
		
		// If MySQL is enabled, we close the connection
		if (mysql) {
			try {
				MySQL.getConnection().close();
			} catch (SQLException e) {
				CommonUtils.log("Could not close the MySQL connection");
			}
		}

		CommonUtils.forceLog(
				getDescription().getName() + " v" + getDescription().getVersion() + " has been disabled successfully");
	}
	
	public void initiate() {
		if (!giveaways.isEmpty()) {
			giveaways.clear();
		}

		// We initiate the files needed for the plugin to run
		fileUtils.initiate(this, "config.yml", "giveaways.yml", "lang/en_US.yml", "guis/home.yml", "guis/enter.yml", "guis/edit.yml");

		// Assign each file to a variable
		configFile = getFile("config.yml");
		giveawaysFile = getFile("giveaways.yml");
		langFile = getFile("lang/" + configFile.getString("languageFile") + ".yml");

		// Setting some global booleans
		mysql = configFile.getString("storage.type").equals("MYSQL");

		CommonUtils.initiate(this, configFile.getBoolean("debug"));

		// If MySQL is enabled we try to connect asynchronously
		if (mysql) {
			Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
				// We initiate a new MySQL object with the login information
				MySQL.initiate(configFile.getString("storage.mysql.host"),
						configFile.getString("storage.mysql.database"),
						Arrays.asList(configFile.getString("storage.mysql.tables.verified")),
						configFile.getString("storage.mysql.username"), configFile.getString("storage.mysql.password"),
						configFile.getString("storage.mysql.statement"), configFile.getInt("storage.mysql.port"));

				// If the connection was not successful we automatically which to FLAT file
				// support
				if (!MySQL.connect()) {
					mysql = false;
					CommonUtils.forceLog("Automatically switched to FLAT file support");
				}
			});
		}

		// We register all of our email templates
		reloadGiveaways();
		startGiveawayTasks();
		
		if (lastServerClose > 0 && !giveawaysFile.getConfigurationSection("giveaways").getKeys(false).isEmpty()) {
			long periodDelay = System.currentTimeMillis() - lastServerClose;
			for (String s : giveawaysFile.getConfigurationSection("giveaways").getKeys(false)) {			
				giveawaysFile.set("giveaways." + s + ".date", giveawaysFile.getLong("giveaways." + s + ".date") + periodDelay);
			}
			saveFile("giveaways.yml");
		}
		

	}
	
	public void reloadGiveaways() {
		if (!giveaways.isEmpty()) {
			giveaways.clear();
		}
		
		for (String s : giveawaysFile.getConfigurationSection("giveaways").getKeys(false)) {
			Giveaway giveaway = new Giveaway();

			giveaway.setId(giveawaysFile.getString("giveaways." + s + ".id"));
			giveaway.setDate(giveawaysFile.getLong("giveaways." + s + ".date"));
			giveaway.setWinners(giveawaysFile.getInt("giveaways." + s + ".winners"));
			giveaway.setMaterial(Material.valueOf(giveawaysFile.getString("giveaways." + s + ".material")));
			giveaway.setPermission(giveawaysFile.getString("giveaways." + s + ".permission"));
			giveaway.setLore(giveawaysFile.getString("giveaways." + s + ".lore"));
			giveaway.setEntered(giveawaysFile.getStringList("giveaways." + s + ".entered"));
			giveaway.setRewards(giveawaysFile.getStringList("giveaways." + s + ".rewards"));

			giveaways.add(giveaway);
		}
	}

	public void startGiveawayTasks() {
		for (Giveaway ga : giveaways) {
			long runAfter = (ga.getDate() - System.currentTimeMillis()) / 1000;

			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					if (giveaways.contains(ga)) {
						giveaways.remove(ga);
						ga.end();
					}
				}
			}, 20 * runAfter);

		}
	}

	public String calculateTime(long ms) {
		ms = ms / 1000;

		String seconds = "", minutes = "", hours = "", days = "";
		long ls = ms % 60, lm = ms % 3600 / 60, lh = ms % 86400 / 3600, ld = ms / 86400;

		if (ls != 0) { seconds = ls + (ls == 1 ? " second " : " seconds "); }
		if (lm != 0) { minutes = lm + (lm == 1 ? " minute " : " minutes "); }
		if (lh != 0) { hours = lh + (lh == 1 ? " hour " : " hours "); }
		if (ld != 0) { days = ld + (ld == 1 ? " day " : " days "); }

		String res = days + hours + minutes + seconds; 
		return res.substring(0, res.length() - 1);
	}
	
	public int getTime(String text) {
		if (!text.contains(" ")) { return 0; }
		
		String[] splitted = text.split(" ");
		int time = 0;
		
		for (String cur : splitted) {
			time += cur.contains("d") ? Integer.valueOf(cur.split("d")[0]) * 86400 : 0;
			time += cur.contains("h") ? Integer.valueOf(cur.split("h")[0]) * 3600 : 0;
			time += cur.contains("m") ? Integer.valueOf(cur.split("m")[0]) * 60 : 0;
			time += cur.contains("s") ? Integer.valueOf(cur.split("s")[0]) : 0;		
		}
		return time;
	}

	public Map<Player, Giveaway> getCreators() {
		return creators; 
	}

	public Map<Player, Giveaway> getEditors() {
		return editors;
	}
	
	public List<Giveaway> getGiveaways() { 
		return this.giveaways;
	}
	
	public FileConfiguration getConfigFile() {
		return configFile;
	}

	public FileConfiguration getLangFile() {
		return langFile;
	}

	public YamlConfiguration getFile(String path) {
		return fileUtils.getFile(path);
	}

	public String getMessage(String path) {
		return langFile.contains(path)
				? ChatColor.translateAlternateColorCodes('&',
						langFile.getString(path).replace("%prefix%", configFile.getString("prefix")))
				: "Error: The specified path (lang/../" + path + ") could not be found.";
	}


	public void saveFile(String path) {
		fileUtils.saveFile(path);
	}

	public void reloadFiles() {
		fileUtils.initiate(this);
	}
}