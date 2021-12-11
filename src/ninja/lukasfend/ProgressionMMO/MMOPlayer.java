package ninja.lukasfend.ProgressionMMO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
			return playerInstances.get(uuidStr);
		} else {
			playerInstances.put(uuidStr, new MMOPlayer(p));
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
		data.set("player.skills."+skill.toString()+".xp", previousLevel+xp);
		data.set("player.totalXp", getXP()+xp);
		if( newLevel > previousLevel ) {
			// Level UP!
			// Skill.levelUp(player, newLevel);
		}
		save();
	}
	
	
}
