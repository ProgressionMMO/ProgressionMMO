package ninja.lukasfend.ProgressionMMO.datahandlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ninja.lukasfend.ProgressionMMO.features.ActionBar;
import ninja.lukasfend.ProgressionMMO.features.BossBarHandler;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.defaults.DefaultPlayerDataFile;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.skills.Skill;

public class MMOPlayer {
	
	// Instance List for multipleton? instances
	private static HashMap<String, MMOPlayer> playerInstances = new HashMap<String, MMOPlayer>();
	private Player player;
	private File dataFile;
	private FileConfiguration data;
	
	private MMOPlayer(Player p) {
		player = p;
		dataFile = new File(ProgressionMMO.getInstance().getDataFolder() + "/playersaves/" + player.getUniqueId().toString() + ".yml");
		data = YamlConfiguration.loadConfiguration(dataFile);
		
		if(!data.contains("player.uuid")) {
			data = DefaultPlayerDataFile.generate(p, dataFile);
			save();
		}
		
	}

	public void save() {
		try {
			data.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Returns the loaded instance of the Player
	 * @param p The Player
	 * @return The MMOPlayer instance
	 */
	public static MMOPlayer get(Player p) {
		String uuidStr = p.getUniqueId().toString();
		if(playerInstances.containsKey(uuidStr)) {
			return playerInstances.get(uuidStr);
		} else {
			playerInstances.put(uuidStr, new MMOPlayer(p));
			return MMOPlayer.get(p);
		}
	}

	/**
	 * Returns a MMOPlayer instance without saving the instance to memory
	 * @return
	 */
	public static MMOPlayer getWithoutMemory(Player p) {
		String uuid = p.getUniqueId().toString();
		if(playerInstances.containsKey(uuid)) {
			return get(p);
		} else {
			return new MMOPlayer(p);
		}
	}

	/**
	 * Unloads a player from memory, if loaded
	 * @param p The player
	 */
	public static void unload(Player p) {
		String uuidStr = p.getUniqueId().toString();
		if(playerInstances.containsKey(uuidStr)) {
			playerInstances.remove(uuidStr);
		}
	}

	
	// Functions

	/**
	 * Returns the guild of a player
	 * @return The guild. Null if player is not in a guild or the guild doesn't exist.
	 */
	public MMOGuild getGuild() {
		if(!hasGuild())return null;
		String guildName = data.getString("player.guild");
		if(!MMOGuild.doesExist(guildName)) return null;
		return MMOGuild.get(guildName);
	}

	/**
	 * Returns true if the player is in a guild
	 * @return True if in a guild
	 */
	public boolean hasGuild() {
		return !(Objects.equals(data.getString("player.guild"), "none"));
	}

	/**
	 * Sets the guild of a player
	 * @param guild
	 */
	public void setGuild(MMOGuild guild) {
		data.set("player.guild", guild.getName());
		save();
		guild.addPlayer(player);
	}

	/**
	 * Returns the guild rank a player has
	 * @return
	 */
	public String getGuildRank() {
		if(!hasGuild()) return null;
		return getGuild().getRank(player);
	}

	/**
	 * Leaves the guild of the player
	 */
	public void leaveGuild() {
		getGuild().removePlayer(player);
		data.set("player.guild", "none");
		save();
	}

	/**
	 * Returns the total xp a player has
	 * @return The total XP
	 */
	public int getXP() {
		return data.getInt("player.totalXp");
	}


	/**
	 * Returns the xp a player has in a Skill
	 * @param skill The skill
	 * @return The skill's xp
	 */
	public int getXP(SkillType skill) {
		return data.getInt("player.skills."+skill.toString() + ".xp");
	}

	/**
	 * Adds a given amount of XP to a player's skill
	 * @param skill The skill
	 * @param xp The amount of XP
	 */
	public void addXP(SkillType skill, int xp) {
		int newXP = getXP(skill)+xp; 
		int previousLevel = Skill.getLevelByXp(newXP-xp);
		int newLevel = Skill.getLevelByXp(newXP);
		int nextLevelXP = Skill.getTotalXPOfLevel(newLevel + 1);
		int currentLevelXP = Skill.getTotalXPOfLevel(newLevel);
		data.set("player.skills."+skill.toString()+".xp", newXP);
		data.set("player.totalXp", getXP()+xp);
		// Display bossbar
		BossBarHandler bbh = BossBarHandler.getInstance();
		BossBar bb = bbh.getBar(player, skill);
		double percentageToNextLevel = (double) (newXP - currentLevelXP) / (nextLevelXP - currentLevelXP);

		System.out.println(percentageToNextLevel + " %, newXP: " + newXP + " nextXP: " + nextLevelXP);

		bb.setProgress(percentageToNextLevel);
		bbh.showForAtLeast(player, skill, 1000*4); // Show for 4 seconds

		ActionBar.sendXPReceived(player, xp, skill);

		if( newLevel > previousLevel ) {
			// Level UP!
			ProgressionMMO.getInstance().skills.get(skill).evokeLevelup(player, skill, newLevel);
		}
		save();
	}

	/**
	 * Returns the level the player has in a skill
	 * @param skill The skill
	 * @return The level
	 */
	public int getLevel(SkillType skill) {
		return Skill.getLevelByXp(getXP(skill));
	}

	/**
	 * Returns a player's total level (sum of all levels)
	 * @return The total level
	 */
	public int getTotalLevel() {
		int total = 0;
		for(SkillType skill : SkillType.values()) {
			total += getLevel(skill);
		}
		return total;
	}

	/**
	 * Returns the progress (in % as factor between 0.00-1.00) a player has to reaching the next level
	 * @param skill The skill
	 * @return The progress (0 if already at max level)
	 */
	public double getProgress(SkillType skill) {
		int level = getLevel(skill);
		if(level == 99) return 1.00f;
		int minXP = Skill.getTotalXPOfLevel(level);
		int maxXP = Skill.getTotalXPOfLevel(level+1);
		int currXP = getXP(skill);
		double progress = (double) ((double) currXP - (double) minXP) / ((double) maxXP);
		if(progress < 0 || progress > 1) {
			return 0;
		}
		return progress;
	}

	/**
	 * Returns if the player is using a scoreboard
	 * @return True if scoreboard is active
	 */
	public boolean isUsingScoreboard() {
		return data.getBoolean("player.config.useScoreboard");
	}

	/**
	 * Toggles the scoreboard on/off
	 */
	public void toggleScoreboard() {
		data.set("player.config.useScoreboard", !isUsingScoreboard());
		save();
	}

	/**
	 * Unloads all players from memory
	 */
	public static void unloadAll() {
		playerInstances.clear();
	}
	
	
}
