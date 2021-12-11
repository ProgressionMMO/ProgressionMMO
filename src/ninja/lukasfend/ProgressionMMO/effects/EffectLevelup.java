package ninja.lukasfend.ProgressionMMO.effects;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import ninja.lukasfend.ProgressionMMO.enums.ProjectileTag;
import ninja.lukasfend.ProgressionMMO.handlers.ProjectileTagger;

public class EffectLevelup {

	public static void playEffect(Player p, int effectLevel) {
		Location loc = p.getLocation();
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.setPower(3);
		
		fwm.addEffect(FireworkEffect.builder().withColor(Color.GREEN, Color.RED, Color.ORANGE, Color.AQUA).flicker(true).build());
		
		fw.setFireworkMeta(fwm);
		fw.detonate();
		ProjectileTagger.addBool(fw, ProjectileTag.DEAL_NO_DAMAGE, true);
		
		for(int i = 0; i < effectLevel; i++) {
			Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
			fw2.setFireworkMeta(fwm);
			FireworkMeta fwm2 = fw.getFireworkMeta().clone();
			fwm2.setPower(4+effectLevel);
			fw2.setFireworkMeta(fwm2);
			ProjectileTagger.addBool(fw2, ProjectileTag.DEAL_NO_DAMAGE, true);
			fw2.detonate();
		}
		
	}
	
}
