package ninja.lukasfend.ProgressionMMO.defaults;

import org.bukkit.configuration.file.FileConfiguration;
import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;

public class DefaultConfigurationFile {
	
	public static FileConfiguration generate(ProgressionMMO plugin) {
		FileConfiguration data = plugin.getConfig();
		
		// Basic Data
		data.set("general.xpMultiplier", 1.0f);
		
		// Skill list
		for(SkillType skill : SkillType.values()) {
			data.set("general.skills."+skill.toString()+".xpMultiplier", 1.0f);
		}

		// Balancing Data

		/*
		*		SKILL CONFIGURATION
		*/
		
		return data;
	}
}
