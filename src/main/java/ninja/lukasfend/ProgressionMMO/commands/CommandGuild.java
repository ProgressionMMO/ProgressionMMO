package ninja.lukasfend.ProgressionMMO.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOGuild;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * CommandHandler for the /guild command
 */
public class CommandGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(args.length == 0) return false;
        if(!(cs instanceof Player)) {
            cs.sendMessage("This command needs to be run as a player.");
            return false;
        }
        Player p = (Player)cs;
        MMOPlayer mp = MMOPlayer.get(p);



        // Help command to display info
        if(args[0].equalsIgnoreCase("help")) {
            cs.sendMessage("§a========== [ §2Guild Commands§a ] ==========");
            cs.sendMessage("§a/guild create <name>§r - Creates a new guild");
            cs.sendMessage("§a/guild chat §r- Toggles Guild chat");
            cs.sendMessage("§a/guild invite <name>§r - Invites a player to your guild");
            cs.sendMessage("§a/guild leave §r- Leaves your current guild");
            cs.sendMessage("§a/guild accept <guildname> §r- Accept a guild invite");
            cs.sendMessage("§a/guild kick <player> §r- Kick a player from");
            cs.sendMessage("§a/guild home §r- Teleports you to the guild home.");
            cs.sendMessage("§a/guild sethome §r- Sets the guild home");
            cs.sendMessage("§a/guild info §r- Shows guild information");
            cs.sendMessage("§a/guild setleader §r- Sets a new guild leader");
            return true;





        // Creates a new guild
        } else if(args[0].equalsIgnoreCase("create")) {

            if(MMOPlayer.get(p).hasGuild()) {
                p.sendMessage("§cYou already have a guild and can't create a new one.");
                return true;
            }

            if(args.length == 1) {
                p.sendMessage("§cYou need to give the guild a name!");
                return true;
            }

            String guildName = "";
            for( int i = 1; i < args.length; i++ ) {
                guildName += args[i] + " ";
            }
            guildName = guildName.trim();

            if(MMOGuild.doesExist(guildName)) {
                p.sendMessage("§cError: A guild with the name §r" + guildName + "§c already exists.");
                return true;
            }

            MMOGuild mg = MMOGuild.create(guildName, p);
            mp.setGuild(mg);
            Bukkit.broadcastMessage("§2" + p.getDisplayName() + "§r created the guild §a" + mg.getName() + "§r!");
            return true;





        // Leave guild
        } else if(args[0].equalsIgnoreCase("leave")) {
            if(!mp.hasGuild()) {
                p.sendMessage("§cYou are currently not in a guild.");
                return true;
            }
            if(args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                if(Objects.equals(mp.getGuildRank(), "LEADER")) {
                    // DELETE GUILD
                    mp.getGuild().delete();
                    p.sendMessage("§4Your guild was deleted.");
                    return true;
                } else {
                    MMOGuild oldGuild = mp.getGuild();
                    mp.leaveGuild();
                    p.sendMessage("You left " + oldGuild.getName() + ".");
                    oldGuild.sendMessageToAllPlayers("§6" + p.getDisplayName() + "§c left the guild.");
                    return true;
                }
            } else {
                p.sendMessage("§cYou're about to leave your guild: ");
                if(Objects.equals(mp.getGuildRank(), "LEADER")) {
                    p.sendMessage("§4§lWarning: §r§4Since you are a guild leader, your guild will be deleted if you leave.\nYou can promote a new leader with /guild setleader <player>");
                }
                TextComponent message = new TextComponent( "§4[Click here to confirm leaving the guild]" );
                message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmguild leave confirm" ) );
                message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("Click to confirm leaving your guild.").create()) );
                p.spigot().sendMessage(message);
                return true;
            }


        // Toggle guild chat
        } else if(args[0].equalsIgnoreCase("chat")) {
            boolean isNowEnabled;
            if(!ProgressionMMO.guildChatEnabled.containsKey(p.getUniqueId().toString())) {
                ProgressionMMO.guildChatEnabled.put(p.getUniqueId().toString(), true);
                isNowEnabled = true;
            } else {
                isNowEnabled = !ProgressionMMO.guildChatEnabled.get(p.getUniqueId().toString());
                ProgressionMMO.guildChatEnabled.put(p.getUniqueId().toString(), isNowEnabled);
            }

            if(isNowEnabled) {
                p.sendMessage("§6Guild chat is now §2enabled.");
            } else {
                p.sendMessage("§6Guild chat is now §cdisabled.");
            }
            return true;


        // Guild home teleport
        } else if(args[0].equalsIgnoreCase("home")) {
            if(!mp.hasGuild()) {
                p.sendMessage("§cYou are currently not in a guild.");
                return true;
            }
            MMOGuild mg = mp.getGuild();
            p.sendMessage("Teleporting...");
            p.teleport(mg.getHomeLocation());
            return true;



        // Guild sethome teleport
        } else if(args[0].equalsIgnoreCase("sethome")) {
            if(!mp.hasGuild()) {
                p.sendMessage("§cYou are currently not in a guild.");
                return true;
            }
            if(!mp.getGuildRank().equalsIgnoreCase("leader")) {
                p.sendMessage("§cOnly the guild leader can set a new home.");
                return true;
            }
            MMOGuild mg = mp.getGuild();
            mg.setHomeLocation(p.getLocation());
            mg.sendMessageToAllPlayers("§6" + p.getDisplayName() + "§r set a new guild home.");
            p.sendMessage("New guild home set!");
            return true;



        // Invites a new player
        } else if (args[0].equalsIgnoreCase("invite")) {
            if(!mp.hasGuild()) {
                p.sendMessage("§cYou are currently not in a guild.");
                return true;
            }
            if(!mp.getGuildRank().equalsIgnoreCase("leader")) {
                p.sendMessage("§cOnly the guild leader can invite new players.");
                return true;
            }
            if(args.length != 2) {
                p.sendMessage("§cPlease enter the name of the player to invite.");
                return true;
            }
            MMOGuild mg = mp.getGuild();
            Player target = Bukkit.getServer().getPlayer(args[1]);
            if(target == null || !target.isOnline()) {
                p.sendMessage("§cError: Player §6" + args[0] + "§c not found. Note that you can only invite online players.");
                return true;
            }
            if(MMOPlayer.get(target).hasGuild()) {
                p.sendMessage("§6" + target.getDisplayName() + "§c already has a guild.");
                return true;
            }
            mg.invitePlayer(p, target);
            mg.sendMessageToAllPlayers("§6"+p.getName() + "§r invited §e" + target.getDisplayName() + "§r to the guild.");
            return true;





        // Accepts a guild invite
        } else if(args[0].equalsIgnoreCase("accept")) {
            if(args.length < 2) {
                p.sendMessage("§cCommand usage: §r/guild accept <guild name to join> §7 - After receiving an invite.");
                return true;
            }
            if(mp.hasGuild()) {
                p.sendMessage("§cYou already have a guild!");
                return true;
            }
            String guildName = "";
            for( int i = 1; i < args.length; i++ ) {
                guildName += args[i] + " ";
            }
            guildName = guildName.trim();
            MMOGuild invitingGuild = MMOGuild.get(guildName);
            if(invitingGuild == null) {
                p.sendMessage("§cError: A guild with the name §6" + guildName + "§r was not found.");
                return true;
            }

            if(invitingGuild.hasActiveInvite(p)) {
                invitingGuild.removeInvite(p);
                invitingGuild.addPlayer(p);
                invitingGuild.sendMessageToAllPlayers("§2" + p.getDisplayName() + "§a joined the guild!");
                return true;
            }

            p.sendMessage("§cYou don't have an active invite from that guild.");
            return true;

        }


        return false;
    }

}
