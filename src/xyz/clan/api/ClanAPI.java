package xyz.clan.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import xyz.clan.main.Main;

public class ClanAPI {
	
	public static String getClanNome(OfflinePlayer p) {
		return Main.getInstance().clan.getString("clan." + p.getName().toLowerCase() + ".clan");

	}

	public static boolean isDono(Player p) {
		return Main.getInstance().clan.getBoolean("clan." + p.getName().toLowerCase() + ".dono");
	}

	public static boolean temClan(Player p) {
		return Main.getInstance().clan.contains("clan." + p.getName().toLowerCase());

	}

	public static void AnunciarClan(OfflinePlayer player, String mensagem) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (temClan(all) && getClanNome((OfflinePlayer) all).equalsIgnoreCase(getClanNome(player))) {
				all.sendMessage(mensagem);
			}
		}
	}

	public static String getDono(String clanName) {
		String p1 = "";
		for (String p2 : Main.getInstance().clan.getConfigurationSection("clan").getKeys(false)) {
			p1 = String.valueOf(p1) + p2;
		}
		if (Main.getInstance().clan.getString("clan." + p1.toLowerCase() + ".clan").equalsIgnoreCase(clanName)
				&& Main.getInstance().clan.getBoolean("clan." + p1.toLowerCase() + ".dono")) {
			return p1;
		}
		return p1;
	}

	public static boolean existeClanNome(String name) {
		String p1 = "";
		for (String p2 : Main.getInstance().clan.getConfigurationSection("clan").getKeys(false)) {
			p1 = String.valueOf(p1) + p2;
		}
		return Main.getInstance().clan.getString("clan." + p1.toLowerCase() + ".clan").contains(name);
	}

	public static void kickJogador(OfflinePlayer p) {
		Main.getInstance().clan.set("clan." + p.getName().toLowerCase(), null);
		Main.getInstance().saveFiles();
	}

	public static void colocarClan(OfflinePlayer p, String clan) {
		Main.getInstance().clan.set("clan." + p.getName().toLowerCase() + ".clan", clan);
		Main.getInstance().clan.set("clan." + p.getName().toLowerCase() + ".dono", Boolean.valueOf(false));
		Main.getInstance().saveFiles();
	}

	public static void sendMesssageClan(OfflinePlayer p, String msg) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (temClan(all) || getClanNome((OfflinePlayer) all).equalsIgnoreCase(getClanNome(p))) {
				if (!isDono((Player) p)) {
					all.sendMessage("§7§l[§b§lCLAN§7§l] §7" + p.getName() + ": §b" + msg);
					continue;
				}
				all.sendMessage("§7§l[§b§lCLAN§7§l] §7" + p.getName() + ": §b" + msg);
			}
		}
	}

	public static void deleteClan(Player p, String clan) {
		Main.getInstance().clan.set("clan." + p.getName().toLowerCase(), null);
		for (String p1 : Main.getInstance().clan.getConfigurationSection("clan").getKeys(false)) {
			if (Main.getInstance().clan.getString("clan" + p1.toLowerCase() + ".clan").equalsIgnoreCase(clan)) {
				Main.getInstance().clan.set("clan" + p1.toLowerCase(), null);
			}
		}
		Main.getInstance().saveFiles();
	}

	public static void createClan(OfflinePlayer owner, String nome) {
		Main.getInstance().clan.set("clan." + owner.getName().toLowerCase() + ".clan", nome);
		Main.getInstance().clan.set("clan." + owner.getName().toLowerCase() + ".dono", Boolean.valueOf(true));
		Main.getInstance().saveFiles();
	}

}
