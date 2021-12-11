package ninja.lukasfend.ProgressionMMO.skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ninja.lukasfend.ProgressionMMO.MMOPlayer;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;

public abstract class Skill implements Listener {
	
	public abstract SkillType getType();
	public abstract void handleLevelup(MMOPlayer player, int level);
	
	public static int getXPOfLevel(int level) {
		return (int) (0.25 * ((level-1)+300*2^((level-1)/7)));
	}
	public static int getTotalXPOfLevel(int level) {
		int total=0;
		for(int i = 1; i < level; i++) {
			total += getXPOfLevel(i);
		}
		return total;
	}
	public static int getLevelByXp(int xp) {
		int level = 1;
		while(getTotalXPOfLevel(level) < xp) {
			level++;
		}
		if(level > 99) {
			level = level - (level % 99);
		}
		return level;
	}
	
	@SuppressWarnings("deprecation")
	public void evokeLevelup(Player p, SkillType skill, int level) {
		if(level % 10 == 0) {
			TextComponent message = new TextComponent( "§b" + p.getDisplayName() + "§r just reached the §olevel " + level + "§r milestone in §r" + skill.getColor() + skill.getSkillName() + "§r.");
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " + p.getName() ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(p.getDisplayName() + " reached Level " + level + " in " + skill.getSkillName()).create()) );
			
			for(Player x : Bukkit.getServer().getOnlinePlayers()) {
				x.spigot().sendMessage(message);
			}
		} else if(level == 99) {
			TextComponent message = new TextComponent( "§c§l" + p.getDisplayName() + "§4§l has achieved level 99 §r" + skill.getColor() + skill.getSkillName() + "§r. Congratulations!");
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " + p.getName() ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder(p.getDisplayName() + " reached the maximum level in " + skill.getSkillName()).create()) );
			
			for(Player x : Bukkit.getServer().getOnlinePlayers()) {
				x.spigot().sendMessage(message);
			}
		}
		
		TextComponent message = new TextComponent( "You've just advanced to level §r in " + skill.getColor() + skill.getSkillName() + "§r.");
		message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/pmlevels " ) );
		message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("You reached level " + level + " " + skill.getSkillName()).create()) );
		p.spigot().sendMessage(message);
		
		// Send player message
		this.handleLevelup(MMOPlayer.get(p), level);
		
	}
}
