package xyz.clan.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import xyz.clan.api.AccountAPI;
import xyz.clan.api.ClanAPI;
import xyz.clan.main.Main;

public class Clan implements CommandExecutor {

public static Map<String, String> clanchat = new HashMap<String, String>();
	
	public static ArrayList<Player> clan = new ArrayList<>();
	public static HashMap<String, String> clanNome = new HashMap<>();
	public static ArrayList<String> convidado = new ArrayList<>();
	public static HashMap<Player, Player> cPlayer = new HashMap<>();

	public static Map<String, String> getClanchat() {
		return clanchat;
	}

	public static void setClanchat(Map<String, String> clanchat) {
		Clan.clanchat = clanchat;
	}

	public static ArrayList<Player> getClan() {
		return clan;
	}

	public static void setClan(ArrayList<Player> clan) {
		Clan.clan = clan;
	}

	public static HashMap<String, String> getClanNome() {
		return clanNome;
	}

	public static void setClanNome(HashMap<String, String> clanNome) {
		Clan.clanNome = clanNome;
	}

	public static ArrayList<String> getConvidado() {
		return convidado;
	}

	public static void setConvidado(ArrayList<String> convidado) {
		Clan.convidado = convidado;
	}

	public static HashMap<Player, Player> getcPlayer() {
		return cPlayer;
	}

	public static void setcPlayer(HashMap<Player, Player> cPlayer) {
		Clan.cPlayer = cPlayer;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clan")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cComando apenas para jogadores.");
				return true;
			}
			final Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("");
				p.sendMessage("    §a§lCLAN §f- §6§lUTILIDADES");
				p.sendMessage("");
				p.sendMessage(" §c/clan criar (nome) §f- §7Criar um clan.");
				p.sendMessage("");
				p.sendMessage(" §c/clan deletar §f- §7Deletar seu clan.");
				p.sendMessage("");
				p.sendMessage(" §c/clan kick (jogador) §f- §7Expulsar um jogador.");
				p.sendMessage("");
				p.sendMessage(" §c/clan convidar (jogador) §f- §7Convidar jogadores.");
				p.sendMessage("");
				p.sendMessage(" §c/clan sair §f- §7Sair do clan.");
				p.sendMessage("");
				p.sendMessage(" §c/c (mensagem) §f- §7Comunicar-se com o clan.");
				p.sendMessage(" ");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("sair")) {
					if (ClanAPI.temClan(p)) {
						if (!ClanAPI.isDono(p)) {
							p.sendMessage("§a§lCLAN: §fVocê saiu do clan §a" + ClanAPI.getClanNome((OfflinePlayer) p)
									+ " §fcom sucesso!");
							ClanAPI.kickJogador((OfflinePlayer) p);
						} else {
							p.sendMessage(
									"§4§lCLAN: §fVocê não pode sair deste clan, pois você é dono. Digite §c/clan deletar §fpara deletar este clan.");
						}
					} else {
						p.sendMessage("§c§lCLAN: §fVocê não está em um clan.");
					}
				} else if (args[0].equalsIgnoreCase("deletar")) {
					if (ClanAPI.temClan(p)) {
						if (ClanAPI.isDono(p)) {
							ClanAPI.deleteClan(p, ClanAPI.getClanNome((OfflinePlayer) p));
							p.sendMessage("§a§lCLAN: §fSeu clan foi deletado com sucesso!");
						} else {
							p.sendMessage("§c§lCLAN: §fVocÃª não é dono deste clan.");
						}
					} else {
						p.sendMessage("§c§lCLAN: §fVocê não possui um clan.");
					}
				} else {
					p.sendMessage("");
					p.sendMessage("    §a§lCLAN §f- §6§lUTILIDADES");
					p.sendMessage("");
					p.sendMessage(" §c/clan criar (nome) §f- §7Criar um clan.");
					p.sendMessage("");
					p.sendMessage(" §c/clan deletar §f- §7Deletar seu clan.");
					p.sendMessage("");
					p.sendMessage(" §c/clan kick (jogador) §f- §7Expulsar um jogador.");
					p.sendMessage("");
					p.sendMessage(" §c/clan convidar (jogador) §f- §7Convidar jogadores.");
					p.sendMessage("");
					p.sendMessage(" §c/clan sair §f- §7Sair do clan.");
					p.sendMessage("");
					p.sendMessage(" §c/c (mensagem) §f- §7Comunicar-se com o clan.");
					p.sendMessage(" ");
				}
				return true;
			}
			if (args.length >= 2) {
				if (args[0].equalsIgnoreCase("criar")) {
					if (!ClanAPI.temClan(p)) {
						if (AccountAPI.getCoins(p) >= 1000) {
							if (args[1].length() >= 3) {
								if (args[1].length() <= 5) {
									ClanAPI.createClan((OfflinePlayer) p, args[1].toUpperCase());
									AccountAPI.removeCoins(p, Integer.valueOf(1000));
									p.sendMessage("§6§lCLAN: §fVocê criou o clan §6" + args[1].toUpperCase()
											+ " §fpor §61000 §fcoins!");
								} else {
									p.sendMessage("§c§lCLAN: §fVocê só pode criar um clan entre §c3 §fe §c5 §fletras.");
								}
							} else {
								p.sendMessage("§c§lCLAN: §fVocê só pode criar um clan entre §c3 §fe §c5 §fletras.");
							}
						} else {
							p.sendMessage("§c§lCLAN: §fVocê precisa de §c1000 coins §fpara criar um clan.");
						}
					} else {
						p.sendMessage("§c§lCLAN: §fVocê já possui um clan.");
					}
				} else if (args[0].equalsIgnoreCase("kick")) {

					@SuppressWarnings("deprecation")
					final OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
					if (ClanAPI.temClan(p)) {
						if (ClanAPI.isDono(p)) {
							if (ClanAPI.temClan((Player) t)) {
								if ((Main.getInstance()).clan.getString("Clans." + t.getName().toLowerCase() + ".clan")
										.equalsIgnoreCase(ClanAPI.getClanNome((OfflinePlayer) p))) {
									ClanAPI.kickJogador(t);
									ClanAPI.AnunciarClan((OfflinePlayer) p,
											"§3§lCLAN: §fO jogador §3" + t.getName() + " §ffoi expulso do clan.");
									if (t.isOnline()) {
										((CommandSender) t).sendMessage("§4§lCLAN: §fVocê foi expulso do clan §c"
												+ ClanAPI.getClanNome((OfflinePlayer) p) + " §fpelo §c" + p.getName()
												+ "§f.");
									}
								} else {
									p.sendMessage("§c§lCLAN: §fEste jogador não está no seu clan.");
								}
							} else {
								p.sendMessage("§c§lCLAN: §fEste jogador não possui um clan.");
							}
						} else {
							p.sendMessage("§c§lCLAN: §fVocê não é o dono do clan.");
						}
					} else {
						p.sendMessage("§c§lCLAN: §fVocê não possui um clan.");
					}
				} else if (args[0].equalsIgnoreCase("aceitar")) {
					final Player t = Bukkit.getPlayer(args[1]);
					if (t != null) {
						if (!ClanAPI.temClan(p)) {
							if (convidado.contains(p.getName())) {
								if (((Player) cPlayer.get(p)).getName().equalsIgnoreCase(t.getName())) {
									ClanAPI.colocarClan((OfflinePlayer) p, ClanAPI.getClanNome((OfflinePlayer) t));
									cPlayer.put(p, null);
									convidado.remove(p.getName());
									ClanAPI.AnunciarClan((OfflinePlayer) t,
											"§6§lCLAN: §fO jogador §6" + p.getName() + " §fentrou no clan!");
								} else {
									p.sendMessage("§c§lCLAN: §fVocÃª nÃ£o possui nenhum convite deste jogador.");
								}
							} else {
								p.sendMessage("§c§lCLAN: §fVocê não possui nenhum convite.");
							}
						} else {
							p.sendMessage("§c§lCLAN: §fVocê já está em um clan.");
						}
					} else {
						p.sendMessage("§c§lCLAN: §fEste jogador não está online.");
					}
				} else if (args[0].equalsIgnoreCase("convidar")) {
					final Player t = Bukkit.getPlayer(args[1]);
					if (t != null) {
						if (ClanAPI.temClan(p)) {
							if (ClanAPI.isDono(p)) {
								if (!ClanAPI.temClan(t)) {
									if (!convidado.contains(t.getName())) {
										convidado.add(t.getName());
										t.sendMessage("");
										t.sendMessage("§e§lCLAN: §fVocê foi convidado para entrar no clan §e"
												+ ClanAPI.getClanNome((OfflinePlayer) p) + "§f!");
										t.sendMessage("§e§lCLAN: §fDigite §e/clan aceitar " + p.getName()
												+ " §fpara aceitar.");
										t.sendMessage("");
										p.sendMessage("§a§lCLAN: §fVocê convidou o jogador §a" + t.getName()
												+ " §fpara o clan.");
										cPlayer.put(t, p);
										Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) Main.getInstance(),
												new Runnable() {
													public void run() {
														if (Clan.convidado.contains(t.getName())) {
															Clan.convidado.remove(t.getName());
															p.sendMessage("");
															p.sendMessage("§c§lCLAN: §fO convite do clan §c"
																	+ ClanAPI.getClanNome((OfflinePlayer) t)
																	+ " §fexpirou!");
															p.sendMessage("");
														}
													}
												}, 6000L);
									}
								} else {

									p.sendMessage("§c§lCLAN: §fEste jogador já está em um clan.");
								}
							} else {
								p.sendMessage("§c§lCLAN: §fVocê não é o dono do clan.");
							}
						} else {
							p.sendMessage("§c§lCLAN: §fVocê não possui um clan.");
						}
					} else {
						p.sendMessage("§c§lCLAN: §fEste jogador não está online.");
					}
				} else {
					p.sendMessage("");
					p.sendMessage("    §a§lCLAN §f- §6§lUTILIDADES");
					p.sendMessage("");
					p.sendMessage(" §c/clan criar (nome) §f- §7Criar um clan.");
					p.sendMessage("");
					p.sendMessage(" §c/clan deletar §f- §7Deletar seu clan.");
					p.sendMessage("");
					p.sendMessage(" §c/clan kick (jogador) §f- §7Expulsar um jogador.");
					p.sendMessage("");
					p.sendMessage(" §c/clan convidar (jogador) §f- §7Convidar jogadores.");
					p.sendMessage("");
					p.sendMessage(" §c/clan sair §f- §7Sair do clan.");
					p.sendMessage("");
					p.sendMessage(" §c/c (mensagem) §f- §7Comunicar-se com o clan.");
					p.sendMessage(" ");
				}
			}
		}
		return true;
	}
}
