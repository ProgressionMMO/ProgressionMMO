package ninja.lukasfend.ProgressionMMO.defaults;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;

public class DefaultPlayerDataFile {

	/**
	 * Creates the default player data file
	 * @param p The player
	 * @param file The file to write to.
	 * @return The FileConfiguration
	 */
	public static FileConfiguration generate(Player p, File file) {
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		// Basic Data
		data.set("player.uuid", p.getUniqueId().toString());
		data.set("player.name", p.getName());
		data.set("player.guild", "none");
		
		// Skill list
		for(SkillType skill : SkillType.values()) {
			data.set("player.skills."+skill.toString()+".xp", 0);
		}
		data.set("player.totalXp", 0);
		
		return data;
	}
}
