package ninja.lukasfend.ProgressionMMO.commands;

import ninja.lukasfend.ProgressionMMO.features.ActionBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.Color;

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
				cs.sendMessage("Reloaded");
			} else if(args[0].equalsIgnoreCase("testeffect")) {
				cs.sendMessage("Testing effect");
				/*Location loc = p.getLocation();
				loc.add(new Vector(0,1.3,0));
				loc.add(loc.getDirection().normalize().multiply(0.4f));
				new ParticleBuilder(ParticleEffect.GLOW, loc)
						.setColor(Color.GREEN)
						.setAmount(50)
						.setSpeed(0.1f)
						.display();*/

				new ParticleBuilder(ParticleEffect.WHITE_ASH, p.getLocation().add(new Vector(1.5, 1, 1.5)))
						.setAmount(50)
						.setColor(Color.GREEN)
						.setSpeed(0.1f)
						.display();

			} else if (args[0].equalsIgnoreCase("sendactionbar")) {
				ActionBar.sendActionBar(p, "§athis is a test!");
				p.sendMessage("test!");
			}
		}
		return true;
	}

}
