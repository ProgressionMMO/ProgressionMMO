package ninja.lukasfend.ProgressionMMO.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.handlers.MMOPlayer;

public class CommandDev implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pmdev")) {
			Player p = (Player)cs;
			if(args[0].equalsIgnoreCase("addxp")) {
				if(args[1].equalsIgnoreCase("strength")) {
					if(args.length == 2)
						MMOPlayer.get(p).addXP(SkillType.STRENGTH, 80);
					else
						MMOPlayer.get(p).addXP(SkillType.STRENGTH, Integer.valueOf(args[2]));
				}
			} else if(args[0].equalsIgnoreCase("reload")) {
				MMOPlayer.unloadAll();
			}
		}
		return true;
	}

}
