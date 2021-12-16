package ninja.lukasfend.ProgressionMMO.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tab autocompleter for /guild command
 */
public class CommandGuildTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args) {
        if(cmd.getName().equalsIgnoreCase("pmguild")) {
            if(args.length == 1) {
                return new ArrayList<String>(Arrays.asList(
                        "help",
                        "create",
                        "leave",
                        "chat",
                        "invite",
                        "accept",
                        "kick",
                        "home",
                        "sethome",
                        "info",
                        "setleader"
                ));
            }
            if(args.length == 2) {
                if(
                        args[1].equalsIgnoreCase("invite") ||
                        args[1].equalsIgnoreCase("kick") ||
                        args[1].equalsIgnoreCase("setleader") ) {
                    ArrayList<String> players = new ArrayList<String>();
                    for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                        players.add(online.getDisplayName());
                    }
                    return players;
                }
            }
        }
        return null;
    }
}
