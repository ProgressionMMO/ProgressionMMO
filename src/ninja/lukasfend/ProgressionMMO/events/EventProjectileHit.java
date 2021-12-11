package ninja.lukasfend.ProgressionMMO.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import ninja.lukasfend.ProgressionMMO.enums.ProjectileTag;
import ninja.lukasfend.ProgressionMMO.handlers.ProjectileTagger;

public class EventProjectileHit implements Listener {
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		Projectile proj = e.getEntity();
		if(e.getHitEntity() != null) {			
			if(ProjectileTagger.hasBool(proj, ProjectileTag.DEAL_NO_DAMAGE)) {
				if(ProjectileTagger.getBool(proj, ProjectileTag.DEAL_NO_DAMAGE)) {
					e.setCancelled(true);
				}
			}
		}
	}

}
