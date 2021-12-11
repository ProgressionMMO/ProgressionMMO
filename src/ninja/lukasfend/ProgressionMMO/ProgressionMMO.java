package ninja.lukasfend.ProgressionMMO;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.handlers.DataLoader;
import ninja.lukasfend.ProgressionMMO.skills.Skill;

public class ProgressionMMO extends JavaPlugin {
	
	private static ProgressionMMO instance;
	public static HashMap<SkillType, Skill> skills = new HashMap<SkillType, Skill>();

	@Override
	public void onEnable() {
		instance = this;
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}
		
		System.out.println("[ProgressionMMO] Loading skills...");
		skills = DataLoader.loadSkills();
		for(Skill skill : skills.values()) {
			Bukkit.getPluginManager().registerEvents(skill, this);
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static ProgressionMMO getInstance() {
		return instance;
	}
}
