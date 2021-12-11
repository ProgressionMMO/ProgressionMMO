package ninja.lukasfend.ProgressionMMO.handlers;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.defaults.DefaultConfigurationFile;

public class ConfigurationVar {

	private static ConfigurationVar instance;
	private static ProgressionMMO plugin = ProgressionMMO.getInstance();
	
	
	public static ConfigurationVar gameCfg() {
		if(instance==null) {
			instance = new ConfigurationVar();
			return instance;
		} else {
			return instance;
		}
	}
	public static void setDefault(String path, Object value) {
		if(!plugin.getConfig().contains("skills.xpMultiplier")) {
			DefaultConfigurationFile.generate(plugin);
			plugin.saveConfig();
		}
		if(!plugin.getConfig().contains(path))
			plugin.getConfig().set(path, value);
	}
	public static int getInt(String path, int defaultValue) {
		setDefault(path, defaultValue);
		return plugin.getConfig().getInt(path);
	}
	public static double getDouble(String path, double defaultValue) {
		setDefault(path, defaultValue);
		return plugin.getConfig().getDouble(path);
	}
	public static boolean getBool(String path, boolean defaultValue) {
		setDefault(path, defaultValue);
		return plugin.getConfig().getBoolean(path);
	}
	public static String getString(String path, int defaultValue) {
		setDefault(path, defaultValue);
		return plugin.getConfig().getString(path);
	}
}
