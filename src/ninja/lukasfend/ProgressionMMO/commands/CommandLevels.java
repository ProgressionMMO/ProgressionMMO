package ninja.lukasfend.ProgressionMMO.commands;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.handlers.MMOPlayer;
import ninja.lukasfend.ProgressionMMO.handlers.StringHelper;
import ninja.lukasfend.ProgressionMMO.handlers.TableGenerator;
import ninja.lukasfend.ProgressionMMO.handlers.TableGenerator.Receiver;
import ninja.lukasfend.ProgressionMMO.skills.Skill;

public class CommandLevels implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pmlevels")) {
			Player p = (Player)cs;
			Player target;
			if(args.length == 0) {
				if(!(cs instanceof Player)){
					cs.sendMessage("The console doesn't have a profile...");
					return false;
				}
				target = p;
			} else {
				try {					
					target = Bukkit.getServer().getPlayer(args[0]);
				} catch(Exception e) {
					cs.sendMessage("Error: Player '" + args[0] + "' not found. (Only online players can be checked)");
					return true;
				}
			}
			
			MMOPlayer mp = MMOPlayer.get(target);
			Bukkit.broadcastMessage("Sending...\n");

			//Formatter formatter = new Formatter();
			//cs.sendMessage("\n"+formatter.format("| %-18s | %-3s | %-10s | %-10s | %-12s |%n", "Skill", "Lvl", "Current XP", "Next XP", "Progress").toString());
			TableGenerator tg = new TableGenerator(
				TableGenerator.Alignment.LEFT, 
				TableGenerator.Alignment.CENTER, 
				TableGenerator.Alignment.RIGHT, 
				TableGenerator.Alignment.RIGHT, 
				TableGenerator.Alignment.LEFT
			);
			
			tg.addRow("§2 Skill", "§2Lvl", "§2XP", "§2Next XP", "§2%");
			tg.addRow("§7-----------","§7--","§7-------","§7-------");
			for(SkillType skill : SkillType.values()) {
				int xp = mp.getXP(skill);
				int nextXp = Skill.getTotalXPOfLevel(mp.getLevel(skill)+1);
				double percentageCompleted = (double) xp / (double) nextXp; 
				if(percentageCompleted >= 1.0f) {
					percentageCompleted = 0.99f;
				}
				tg.addRow(
					"§6 "+skill.getSkillName(), 
					mp.getLevel(skill)+"",
					StringHelper.thousandSpacers(xp)+"",
					StringHelper.thousandSpacers(nextXp)+"",
					"§l"+StringHelper.progressBar(22, percentageCompleted) + " §r§7" + String.format("%.02f", percentageCompleted*100).replace(",", ".") + "%"
				);
			}
			List<String> lines = tg.generate(Receiver.CLIENT, true, true);
			for(int i = 0; i < SkillType.values().length; i++) {
				if(lines.get(i).contains("||")) {
					TextComponent message = new TextComponent( lines.get(i) );
					message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmskill " + SkillType.values()[i-2].getSkillName().toLowerCase() ) );
					message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("§oClick to show extened information about §6" + SkillType.values()[i-2].getSkillName()).create()) );
					
					cs.spigot().sendMessage(message);					
			 	} else {
			 		cs.sendMessage(lines.get(i));
			 	}
			}
			cs.sendMessage(" §dTotal Level: §r" + mp.getTotalLevel() + "                  §dTotal XP: §r" + mp.getXP());
			//cs.sendMessage(formatter.format("|=%-12s=|=%-3s=|=%-10s=|=%-10s=|=%-12s=|", space12, space3, space10, space10, space12).toString());
			return true;
		}
		return false;
	}

}
