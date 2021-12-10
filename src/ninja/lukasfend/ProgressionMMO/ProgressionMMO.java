package ninja.lukasfend.ProgressionMMO;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ProgressionMMO extends JavaPlugin {
	
	private static ProgressionMMO instance;

	@Override
	public void onEnable() {
		instance = this;
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static ProgressionMMO getInstance() {
		return instance;
	}
}
