package ninja.lukasfend.ProgressionMMO.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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

	public static MMOPlayer get(Player p) {
		String uuidStr = p.getUniqueId().toString();
		if(playerInstances.containsKey(uuidStr)) {
			System.out.println("found instance");
			return playerInstances.get(uuidStr);
		} else {
			playerInstances.put(uuidStr, new MMOPlayer(p));
			System.out.println("new instance");
			return MMOPlayer.get(p);
		}
	}
	public static void unload(Player p) {
		String uuidStr = p.getUniqueId().toString();
		if(playerInstances.containsKey(uuidStr)) {
			playerInstances.remove(uuidStr);
		}
		return;
	}

	
	// Functions
	public int getXP() {
		return data.getInt("player.totalXp");
	}
	public int getXP(SkillType skill) {
		return data.getInt("player.skills."+skill.toString() + ".xp");
	}
	public void addXP(SkillType skill, int xp) {
		int newXP = getXP(skill)+xp; 
		int previousLevel = Skill.getLevelByXp(newXP-xp);
		int newLevel = Skill.getLevelByXp(newXP);
		data.set("player.skills."+skill.toString()+".xp", newXP);
		data.set("player.totalXp", getXP()+xp);
		if( newLevel > previousLevel ) {
			// Level UP!
			ProgressionMMO.getInstance().skills.get(skill).evokeLevelup(player, skill, newLevel);
		}
		save();
	}
	public int getLevel(SkillType skill) {
		return Skill.getLevelByXp(getXP(skill));
	}
	public int getTotalLevel() {
		int total = 0;
		for(SkillType skill : SkillType.values()) {
			total += getLevel(skill);
		}
		return total;
	}
	public double getProgress(SkillType skill) {
		int level = getLevel(skill);
		if(level == 99) return 1.00f;
		int minXP = Skill.getTotalXPOfLevel(level);
		int maxXP = Skill.getTotalXPOfLevel(level+1);
		int currXP = getXP(skill);
		double progress = (double) ((double) currXP - (double) minXP) / ((double) maxXP);
		System.out.println("Level: " + level + " MinXP: " + minXP + " MaxXP: " + maxXP + " CurrXP: " + currXP + " Percentage: " + progress);
		if(progress < 0 || progress > 1) {
			return 0;
		}
		return progress;
	}

	public static void unloadAll() {
		playerInstances.clear();
	}
	
	
}
