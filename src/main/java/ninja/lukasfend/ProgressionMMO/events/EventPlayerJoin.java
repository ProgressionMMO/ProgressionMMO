package ninja.lukasfend.ProgressionMMO.events;

import ninja.lukasfend.ProgressionMMO.features.BossBarHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        BossBarHandler.getInstance().loadPlayer(e.getPlayer());
    }

}
