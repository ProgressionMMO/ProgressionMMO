package ninja.lukasfend.ProgressionMMO.defaults;

import org.bukkit.configuration.file.FileConfiguration;
import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;

public class DefaultConfigurationFile {
	
	public static FileConfiguration generate(ProgressionMMO plugin) {
		FileConfiguration data = plugin.getConfig();
		
		// Basic Data
		data.set("skills.xpMultiplier", 1.0f);
		
		// Skill list
		for(SkillType skill : SkillType.values()) {
			data.set("skills.skills."+skill.toString()+".multiplier", 1.0f);
		}
		
		return data;
	}
}
