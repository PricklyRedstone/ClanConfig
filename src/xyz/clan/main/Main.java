package xyz.clan.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.clan.commands.ChatClan;
import xyz.clan.commands.Clan;

public class Main extends JavaPlugin {
	
	public static Main instance;
	public static Plugin plugin;
	public File clan2;
	public YamlConfiguration clan;
	public File accounts2;
	public YamlConfiguration accounts;
	
	public File getClan2() {
		return clan2;
	}
	public void setClan2(File clan2) {
		this.clan2 = clan2;
	}
	public YamlConfiguration getClan() {
		return clan;
	}
	public void setClan(YamlConfiguration clan) {
		this.clan = clan;
	}
	public File getAccounts2() {
		return accounts2;
	}
	public void setAccounts2(File accounts2) {
		this.accounts2 = accounts2;
	}
	public YamlConfiguration getAccounts() {
		return accounts;
	}
	public void setAccounts(YamlConfiguration accounts) {
		this.accounts = accounts;
	}
	public static Main getInstance() {
		return instance;
	}
	public static void setInstance(Main instance) {
		Main.instance = instance;
	}
	public static Plugin getPlugin() {
		return plugin;
	}
	public static void setPlugin(Plugin plugin) {
		Main.plugin = plugin;
	}
	
	public void onLoad() {
		instance = this;
		plugin = this;
		registerCommands();
		registerClanFile();
		registerAccountsFile();
		saveFiles();
		Bukkit.getConsoleSender().sendMessage("§e[Clan] O plugin está sendo iniciado.");
	}
	
	private void registerAccountsFile() {
		accounts2 = new File(getDataFolder(), "accounts.yml");
		accounts = YamlConfiguration.loadConfiguration(accounts2);
		saveFiles();
		
	}
	public void saveFiles() {
		try {
			clan.save(clan2);
			accounts.save(accounts2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void registerClanFile() {
		clan2 = new File(getDataFolder(), "clan.yml");
		clan = YamlConfiguration.loadConfiguration(clan2);
		saveFiles();
		
	}
	private void registerCommands() {
		getCommand("c").setExecutor(new ChatClan());
		getCommand("clan").setExecutor(new Clan());
		
	}
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§a[Clan] O plugin foi ativado com sucesso.");
	}
	
	public void onDisable() {
		instance = this;
		plugin = this;
		Bukkit.getConsoleSender().sendMessage("§c[Clan] O plugin foi desativado com sucesso.");
	}

}
