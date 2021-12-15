package ninja.lukasfend.ProgressionMMO.datahandlers;


import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.defaults.DefaultGuildDataFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * An object that represents a guild
 */
public class MMOGuild {

    // Instance list
    private static HashMap<String, MMOGuild> guildInstances = new HashMap<String, MMOGuild>();
    private ArrayList<String> activeInvites = new ArrayList<String>();
    private String guildName;
    private String guildOwnerId;
    private String fileName;
    private File dataFile;
    private FileConfiguration data;

    private MMOGuild(String guildName) {
        this.guildName = guildName;
        fileName = guildName.replace(" ", "_").toLowerCase();
        dataFile = new File(ProgressionMMO.getInstance().getDataFolder() + "/guildsaves/" + fileName + ".yaml");
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    /**
     * Checks if a guild with a given name already exists
     * @param guildName The name of the guild
     * @return If the guild exists
     */
    public static boolean doesExist(String guildName) {
        String fileName = guildName.replace(" ", "_").toLowerCase();
        File dataFile = new File(ProgressionMMO.getInstance().getDataFolder() + "/guildsaves/" + fileName + ".yaml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        return data.contains("guild.name");
    }

    /**
     * Creates a new guild
     * @param guildName The name of the guild
     * @param owner The player that owns the guild
     * @return The instance of the guild, returns null if the guild already exists
     */
    public static MMOGuild create(String guildName, Player owner) {
        if(MMOGuild.doesExist(guildName)) {
            return null;
        } else {
            MMOGuild instance = new MMOGuild(guildName);
            instance.data = DefaultGuildDataFile.generate(guildName, owner, instance.dataFile);
            instance.save();
            guildInstances.put(guildName, instance);
            return get(guildName);
        }
    }


    /**
     * Invites a player to a guild
     * @param sender The player that sent the invite
     * @param target The player that receives the invite
     */
    public void invitePlayer(Player sender, Player target) {
        activeInvites.add(target.getUniqueId().toString());
        target.sendMessage("§2" + sender.getDisplayName() + "§r invited you to join §a" + guildName);
        TextComponent message = new TextComponent( "§2[Click here to accept the invite]" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmguild accept " + guildName ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("Click to join §a" + guildName).create()) );
        target.spigot().sendMessage(message);
    }

    /**
     * Returns if an invite for a certain player is active
     * @param player The player to be invited
     * @return True if an invite for that player exists
     */
    public boolean hasActiveInvite(Player player) {
        if(activeInvites.contains(player.getUniqueId().toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes/revokes an invitation for a player
     * @param p The player to be removed from the invite list
     */
    public void removeInvite(Player p) {
        if(hasActiveInvite(p)) {
            activeInvites.remove(activeInvites.indexOf(p.getUniqueId().toString()));
        }
    }

    /**
     * Sends a message to all currently online players (automatically adds prefix)
     * @param message The message to be sent to the members
     */
    public void sendMessageToAllPlayers(String message) {
        for(Player p : getOnlinePlayers()) {
            p.sendMessage("[§2"+guildName+"§r] "+message);
        }
    }



    /**
     * Checks if the guild has the player in it
     * @param p The player
     * @return True if the player is in the guild
     */
    public boolean hasPlayer(Player p) {
        return data.contains("guild.players." + p.getUniqueId().toString() + ".uuid");
    }

    /**
     * Returns true if a player (by name) is in the guild
     * @param playerName The name of the player (ignores casing)
     * @return True if player is in guild
     */
    public boolean hasPlayer(String playerName) {
        for(String path : data.getConfigurationSection("guild.players").getKeys(false)) {
            if(data.getString("guild.players." + path + ".name").equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all player names in a guild
     * @return The list of player names
     */
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> players = new ArrayList<String>();
        for(String path : data.getConfigurationSection("guild.players").getKeys(false)) {
            players.add(data.getString("guild.players." + path + ".name"));
        }
        return players;
    }

    /**
     * Adds the player to the guild
     * @param p The player to be added to the guild
     */
    public void addPlayer(Player p) {
        data.set("guild.players." + p.getUniqueId().toString() + ".uuid", p.getUniqueId());
        data.set("guild.players." + p.getUniqueId().toString() + ".name", p.getDisplayName());
        data.set("guild.players." + p.getUniqueId().toString() + ".rank", "MEMBER");
        data.set("guild.players." + p.getUniqueId().toString() + ".jointime", (int) System.currentTimeMillis()/1000);
    }

    /**
     * Removes a player from the guild
     * @param p The player to be removed
     */
    public void removePlayer(Player p) {
        if(!hasPlayer(p))return;
        data.set("guild.players." + p.getUniqueId().toString(), null);
    }

    /**
     * Deletes the guild
     */
    public void delete() {
        // Make all players leave
        for(Player players : getAllPlayers()) {
            MMOPlayer.getWithoutMemory(players).leaveGuild();
        }
        data = null;
        dataFile = null;
        String fileName = guildName.replace(" ", "_").toLowerCase();
        File df = new File(ProgressionMMO.getInstance().getDataFolder() + "/guildsaves/" + fileName + ".yaml");
        if(df.delete()) {
            System.out.println("Guild file was deleted: " + guildName);
        } else {
            System.out.println("Gould file couldn't be deleted: " + guildName);
        }
        guildInstances.remove(guildName);
    }

    /**
     * Gets the rank of a player (eg. MEMBER, LEADER, COLEADER)
     * @param p The player to be checked
     * @return The rank of the player
     */
    public String getRank(Player p) {
        return data.getString("guild.players." + p.getUniqueId().toString() + ".rank");
    }

    /**
     * Gets the home location of a guild
     * @return The location of the guild (with world)
     */
    public Location getHomeLocation() {
        return new Location(
                Bukkit.getWorld(data.getString("guild.home.world")),
                data.getDouble("guild.home.x"),
                data.getDouble("guild.home.y"),
                data.getDouble("guild.home.z")
        );
    }

    /**
     * Sets the new guild home teleport point
     * @param l The location of the new home
     */
    public void setHomeLocation(Location l) {
        data.set("guild.home.world", l.getWorld().getName());
        data.set("guild.home.x", l.getX());
        data.set("guild.home.y", l.getY());
        data.set("guild.home.z", l.getZ());
    }


    /**
     * Returns all online players of a guild
     * @return List of online players
     */
    public ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : getAllPlayers()) {
            if(p.isOnline()) {
                players.add(p);
            }
        }
        return players;
    }

    /**
     * Returns all players of a guild (also offline ones)
     * @return List of players
     */
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        for(String path : data.getConfigurationSection("guild.players").getKeys(false)) {
            UUID uuid = UUID.fromString(data.getString("guild.players." + path + ".uuid"));
            Player p = Bukkit.getPlayer(uuid);
            if(p != null) {
                players.add(p);
            }
        }
        return players;
    }


    /**
     * Returns the instance of a guild.
     * @param guildName The name of the guild to get
     * @return The instance, returns null if guild doesn't exist.
     */
    public static MMOGuild get(String guildName) {
        if(guildInstances.containsKey(guildName)) {
            return guildInstances.get(guildName);
        } else {
            if(MMOGuild.doesExist(guildName)) {
                guildInstances.put(guildName, new MMOGuild(guildName));
            } else {
                return null;
            }
            return get(guildName);
        }
    }

    /**
     * Unloads all instances
     */
    public static void unloadAll() {
        guildInstances.clear();
    }

    public void save() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The name of the guild
     * @return The name
     */
    public String getName() {
        return guildName;
    }

    /**
     * The owner of the guild
     * @return The owner's UUID as string
     */
    public String getOwnerId() {
        return guildOwnerId;
    }
}
