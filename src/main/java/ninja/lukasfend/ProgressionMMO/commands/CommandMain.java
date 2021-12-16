package ninja.lukasfend.ProgressionMMO.commands;

import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandHandler for handling the /pm command
 */
public class CommandMain implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if(!(cs instanceof Player)) {
            cs.sendMessage("This command needs to be run as a player!");
            return true;
        }

        Player p = (Player)cs;
        MMOPlayer mp = MMOPlayer.get(p);
        if(cmd.getName().equalsIgnoreCase("pm")) {
            if(args.length == 0) {
                //TODO: Show help
                return true;
            }

            if(args[0].equalsIgnoreCase("toggleScoreboard")) {
                boolean wasEnabled = mp.isUsingScoreboard();
                mp.toggleScoreboard();
                if(wasEnabled) {
                    p.sendMessage("§6Scoreboard is now §cturned off.");
                } else {
                    p.sendMessage("§6Scoreboard is now §aturned on.");
                }
                return true;
            }
        }

        return false;
    }

}
