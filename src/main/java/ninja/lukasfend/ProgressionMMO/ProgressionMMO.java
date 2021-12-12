package ninja.lukasfend.ProgressionMMO;

import java.util.HashMap;

import ninja.lukasfend.ProgressionMMO.commands.CommandGuild;
import ninja.lukasfend.ProgressionMMO.defaults.DefaultConfigurationFile;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import ninja.lukasfend.ProgressionMMO.commands.CommandDev;
import ninja.lukasfend.ProgressionMMO.commands.CommandLevels;
import ninja.lukasfend.ProgressionMMO.commands.CommandSkill;
import ninja.lukasfend.ProgressionMMO.enums.ProjectileTag;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.events.EventProjectileHit;
import ninja.lukasfend.ProgressionMMO.handlers.DataLoader;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;
import ninja.lukasfend.ProgressionMMO.skills.Skill;

public class ProgressionMMO extends JavaPlugin {
	
	private static ProgressionMMO instance;
	public static HashMap<SkillType, Skill> skills = new HashMap<SkillType, Skill>();
	public static HashMap<ProjectileTag, NamespacedKey> projectileKeys = new HashMap<ProjectileTag, NamespacedKey>();
	public static HashMap<String, Boolean> guildChatEnabled = new HashMap<String, Boolean>();

	@Override
	public void onEnable() {
		instance = this;
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}

		if(!getConfig().contains("general.xpMultiplier")) {
			System.out.println("[ProgressionMMO] Creating default config...");
			DefaultConfigurationFile.generate(this);
		}

		System.out.println("[ProgressionMMO] Loading skills...");
		skills = DataLoader.loadSkills();
		for(Skill skill : skills.values()) {
			Bukkit.getPluginManager().registerEvents(skill, this);
		}
		
		System.out.println("[ProgressionMMO] Loading commands...");
		getCommand("pmdev").setExecutor(new CommandDev());
		getCommand("pmlevels").setExecutor(new CommandLevels());
		getCommand("pmskill").setExecutor(new CommandSkill());
		getCommand("pmguild").setExecutor(new CommandGuild());
		
		System.out.println("[ProgressionMMO] Registering events...");
		getServer().getPluginManager().registerEvents(new EventProjectileHit(), this);
		
	}
	
	@Override
	public void onDisable() {
		MMOPlayer.unloadAll();
	}
	
	public static ProgressionMMO getInstance() {
		return instance;
	}
}
