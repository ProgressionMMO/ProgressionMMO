package ninja.lukasfend.ProgressionMMO.events;

import ninja.lukasfend.ProgressionMMO.features.BossBarHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        BossBarHandler.getInstance().unloadPlayer(e.getPlayer());
    }

}
