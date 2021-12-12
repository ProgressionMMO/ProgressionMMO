package ninja.lukasfend.ProgressionMMO.defaults;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class DefaultGuildDataFile {

	/**
	 * Generates the default data file for a new guild.
	 * @param guildName The name of the guild
	 * @param owner The player that owns the guild
	 * @param file The file to be written to
	 * @return The new file configurations
	 */
	public static FileConfiguration generate(String guildName, Player owner, File file) {
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		// Basic Data
		data.set("guild.name", guildName);
		data.set("guild.owner", owner.getUniqueId().toString());
		data.set("guild.creation", System.currentTimeMillis()/1000);

		// Guild home
		Location l = owner.getWorld().getSpawnLocation();
		data.set("guild.home.x", l.getX());
		data.set("guild.home.y", l.getY());
		data.set("guild.home.z", l.getZ());
		data.set("guild.home.world", l.getWorld().getName());

		// Player list
		data.set("guild.players." + owner.getUniqueId().toString() + ".name", owner.getDisplayName());
		data.set("guild.players." + owner.getUniqueId().toString() + ".rank", "LEADER");
		data.set("guild.players." + owner.getUniqueId().toString() + ".jointime", System.currentTimeMillis()/1000);
		data.set("guild.players." + owner.getUniqueId().toString() + ".uuid", owner.getUniqueId().toString());

		return data;
	}
}
