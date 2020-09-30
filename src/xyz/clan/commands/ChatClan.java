package xyz.clan.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.clan.api.ClanAPI;

public class ChatClan implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cComando apenas para jogadores.");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("c")) {
			if (args.length == 0) {
				p.sendMessage("§c§lERRO: §fComando incorreto, utilize: /c (mensagem)");
				return true;
			}
			String msg = "";
			for (String msg2 : args) {
				msg = ((msg)) + msg2 + " ";
			}
			if (!ClanAPI.temClan(p)) {
				ClanAPI.sendMesssageClan((OfflinePlayer) p, msg);
			} else {
				p.sendMessage("§c§lCLAN: §fVocê não possui um clan.");
			}
		}
		return false;
	}

}
