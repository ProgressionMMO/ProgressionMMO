package ninja.lukasfend.ProgressionMMO.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.handlers.MMOPlayer;
import ninja.lukasfend.ProgressionMMO.handlers.StringHelper;
import ninja.lukasfend.ProgressionMMO.handlers.TableGenerator;
import ninja.lukasfend.ProgressionMMO.handlers.TableGenerator.Alignment;
import ninja.lukasfend.ProgressionMMO.handlers.TableGenerator.Receiver;
import ninja.lukasfend.ProgressionMMO.skills.Skill;

public class CommandSkill implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pmskill")) {
			if(args.length != 1) {
				return false;
			}
			if(!(cs instanceof Player)) {
				cs.sendMessage("Console doesn't have a player Profile!");
				return false;
			}
			cs.sendMessage(" ");
			MMOPlayer mp = MMOPlayer.get((Player)cs);
			SkillType skill = SkillType.fromString(args[0]);
			if(skill==null) return false;
			TableGenerator tg = new TableGenerator(Alignment.RIGHT, Alignment.LEFT);
			tg.addRow("§6Skill", skill.getColor() + skill.getSkillName());
			boolean wasDescriptionTitleShown = false;
			for(String row : skill.getDescriptionLore().split("\n")) {
				tg.addRow(wasDescriptionTitleShown ? " " : "§6Description", row);
				wasDescriptionTitleShown = true;
			}
			int level = mp.getLevel(skill);
			int currentXP = mp.getXP(skill);
			int targetXP = Skill.getTotalXPOfLevel(level+1);
			int remainingXP = targetXP - currentXP;
			double percentage = mp.getProgress(skill);
			if(level == 99) {
				remainingXP = 0;
			}
			tg.addRow("§6Current Level: ", mp.getLevel(skill)+"");
			tg.addRow("§6Current XP:", StringHelper.thousandSpacers(currentXP));
			if(level < 99) {
				tg.addRow("§6XP for Level " + (level+1) + ":", StringHelper.thousandSpacers(targetXP));
			}
			tg.addRow("§6XP Remaining:", StringHelper.thousandSpacers(remainingXP));
			tg.addRow("§6Progress:", StringHelper.progressBar(30, percentage) + "§r" +String.format(" %.02f", percentage*100).replace(",", "."));
			
			for(String line : tg.generate(Receiver.CLIENT, true, true)) {
				cs.sendMessage(line);
			}
				
			return true;
			
		}
		return false;
	}

}
