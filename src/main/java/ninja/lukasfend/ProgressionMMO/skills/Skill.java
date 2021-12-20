package ninja.lukasfend.ProgressionMMO.skills;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ninja.lukasfend.ProgressionMMO.effects.EffectLevelup;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;

public abstract class Skill implements Listener {
	
	public abstract SkillType getType();
	public abstract void handleLevelup(MMOPlayer player, int level);

	/**
	 * Returns the XP needed to advance from the previous level to the given level
	 * @param level The level to be reached
	 * @return The amount of xp
	 */
	public static int getXPOfLevel(int level) {
		if(level <= 1) return 0;
		if(level > 99) {
			level = 99;
		}
		double xp = 0.25 * ( (level-1f) + 300f * Math.pow(2f, (level-1f)/7f) );
		return (int) Math.round(xp);
	}

	/**
	 * Returns the total xp needed to advance to a given level
	 * @param level The level to be reached
	 * @return The amount of xp
	 */
	public static int getTotalXPOfLevel(int level) {
		double total=0;
		if(level > 99) {
			level = 99;
		}
		for(int i = 1; i <= level; i++) {
			total += getXPOfLevel(i);
		}
		return (int) Math.round(total);
	}


	/**
	 * Returns the level by the xp
	 * @param xp The xp of the skill
	 * @return The level the skill has
	 */
	public static int getLevelByXp(int xp) {
		if(xp >= getTotalXPOfLevel(99)) {
			return 99;
		}
		if(xp == 0) {
			return 1;
		}
		int level = 1;
		while(getTotalXPOfLevel(level) < xp) {
			level++;
		}
		return level-1;
	}

	/**
	 * Evokes a levelup check, called whenever the player advances to a new level
	 * @param p The player
	 * @param skill The skill in which the player advanced
	 * @param level The level to which the player advanced
	 */
	@SuppressWarnings("deprecation")
	public void evokeLevelup(Player p, SkillType skill, int level) {
		if(level % 10 == 0) {
			TextComponent message = new TextComponent( "§b" + p.getDisplayName() + "§r just reached the §olevel " + level + "§r milestone in §r" + skill.getColor() + skill.getSkillName() + "§r.");
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " + p.getName() ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(p.getDisplayName() + " reached Level " + level + " in §l" + skill.getSkillName()).create()) );
			
			for(Player x : Bukkit.getServer().getOnlinePlayers()) {
				x.spigot().sendMessage(message);
			}
			EffectLevelup.playEffect(p, false);
			p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3, 2f);
		} else if(level == 99) {
			TextComponent message = new TextComponent( "§c§l" + p.getDisplayName() + "§4§l has achieved level 99 §r" + skill.getColor() + skill.getSkillName() + "§r. §6Congratulations!");
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " + p.getName() ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(p.getDisplayName() + " reached the maximum level in §l" + skill.getSkillName()).create()) );
			
			for(Player x : Bukkit.getServer().getOnlinePlayers()) {
				x.spigot().sendMessage(message);
			}
			EffectLevelup.playEffect(p, true);
			p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3, 0.5f);
		}
		
		TextComponent message = new TextComponent( "You've just advanced to level "+level+"§r in §l" + skill.getColor() + skill.getSkillName() + "§r.");
		message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " ) );
		message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("You reached level " + level + " " + skill.getSkillName()).create()) );
		p.spigot().sendMessage(message);
		EffectLevelup.playEffect(p, false);
		
		// Send player message
		this.handleLevelup(MMOPlayer.get(p), level);
		
	}
}
