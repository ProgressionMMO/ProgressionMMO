package ninja.lukasfend.ProgressionMMO.events;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOGuild;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventPlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(ProgressionMMO.guildChatEnabled.containsKey(p.getUniqueId().toString())) {
            if(!ProgressionMMO.guildChatEnabled.get(p.getUniqueId().toString())) { return; }
            MMOPlayer mp = MMOPlayer.get(p);
            MMOGuild mg = mp.getGuild();
            if(mp.hasGuild()) {
                e.setCancelled(true);
            } else {
                return;
            }
            String message = "[§a"+mg.getName()+"§r][§a"+mg.getRank(p)+"§r] §2" + p.getDisplayName() + "§r: "+e.getMessage();
            for(Player target : mg.getOnlinePlayers()) {
                target.sendMessage(message);
            }
        } else {
            ProgressionMMO.guildChatEnabled.put(p.getUniqueId().toString(), false);
        }
    }
}
