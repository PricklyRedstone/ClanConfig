package xyz.clan.api;

import org.bukkit.entity.Player;

import xyz.clan.main.Main;

public class AccountAPI {
	
	public static void addCoins(Player p, final int i) {
		final int Value = Main.getInstance().accounts.getInt(String.valueOf(p.getName()).toLowerCase() + ".coins");
		Main.getInstance().accounts.set(String.valueOf(p.getName()).toLowerCase() + ".coins", (Object) (Value + i));
		Main.getInstance().saveFiles();
	}
	
	public static void removeCoins(Player p, final int i) {
		final int Value = Main.getInstance().accounts.getInt(String.valueOf(p.getName()).toLowerCase() + ".coins");
		Main.getInstance().accounts.set(String.valueOf(p.getName()).toLowerCase() + ".coins", (Object) (Value - i));
		Main.getInstance().saveFiles();
	}
	
	public static int getCoins(final Player p) {
		return Main.getInstance().accounts.getInt(String.valueOf(p.getName()).toLowerCase() + ".coins");

	}

}
