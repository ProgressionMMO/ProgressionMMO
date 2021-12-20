package ninja.lukasfend.ProgressionMMO.features;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Handles the action bar above the health bar of minecraft
 */
public class ActionBar implements Listener {

    private final ProgressionMMO plugin = ProgressionMMO.getInstance();

    public static void sendActionBar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void sendXPReceived(Player p, int xp, SkillType skill) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + skill.getSkillName() + ": +" + xp + " XP"));
    }
}
