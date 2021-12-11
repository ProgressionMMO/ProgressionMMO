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
import ninja.lukasfend.ProgressionMMO.handlers.MMOPlayer;

public abstract class Skill implements Listener {
	
	public abstract SkillType getType();
	public abstract void handleLevelup(MMOPlayer player, int level);
	
	public static int getXPOfLevel(int level) {
		if(level <= 1) return 0;
		double xp = 0.25 * ( (level-1f) + 300f * Math.pow(2f, (level-1f)/7f) );
		return (int) Math.round(xp);
	}
	public static int getTotalXPOfLevel(int level) {
		double total=0;
		for(int i = 1; i < level+1; i++) {
			total += getXPOfLevel(i);
		}
		return (int) Math.round(total);
	}
	public static int getLevelByXp(int xp) {
		int level = 1;
		while(getTotalXPOfLevel(level) < xp) {
			level++;
		}
		if(level > 100) {
			level = level - (level % 100);
		}
		return level-1;
	}
	
	@SuppressWarnings("deprecation")
	public void evokeLevelup(Player p, SkillType skill, int level) {
		if(level % 10 == 0) {
			TextComponent message = new TextComponent( "§b" + p.getDisplayName() + "§r just reached the §olevel " + level + "§r milestone in §r" + skill.getColor() + skill.getSkillName() + "§r.");
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " + p.getName() ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(p.getDisplayName() + " reached Level " + level + " in §l" + skill.getSkillName()).create()) );
			
			for(Player x : Bukkit.getServer().getOnlinePlayers()) {
				x.spigot().sendMessage(message);
			}
			EffectLevelup.playEffect(p, 2);
			p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3, 2f);
		} else if(level == 99) {
			TextComponent message = new TextComponent( "§c§l" + p.getDisplayName() + "§4§l has achieved level 99 §r" + skill.getColor() + skill.getSkillName() + "§r. §6Congratulations!");
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " + p.getName() ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(p.getDisplayName() + " reached the maximum level in §l" + skill.getSkillName()).create()) );
			
			for(Player x : Bukkit.getServer().getOnlinePlayers()) {
				x.spigot().sendMessage(message);
			}
			EffectLevelup.playEffect(p, 2);
			p.getWorld().playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3, 0.5f);
		} else {
			
		}
		
		TextComponent message = new TextComponent( "You've just advanced to level "+level+"§r in §l" + skill.getColor() + skill.getSkillName() + "§r.");
		message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " ) );
		message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("You reached level " + level + " " + skill.getSkillName()).create()) );
		p.spigot().sendMessage(message);
		EffectLevelup.playEffect(p, 1);
		
		// Send player message
		this.handleLevelup(MMOPlayer.get(p), level);
		
	}
}
