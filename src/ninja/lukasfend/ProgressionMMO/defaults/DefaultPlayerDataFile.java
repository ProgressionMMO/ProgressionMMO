package ninja.lukasfend.ProgressionMMO.defaults;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DefaultPlayerDataFile {
	
	public static FileConfiguration generate(Player p, File file) {
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		// Basic Data
		data.set("player.uuid", p.getUniqueId().toString());
		data.set("player.name", p.getName());
		
		return data;
	}
}
