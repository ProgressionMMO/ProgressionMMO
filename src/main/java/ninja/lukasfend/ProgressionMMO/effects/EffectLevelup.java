package ninja.lukasfend.ProgressionMMO.effects;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import ninja.lukasfend.ProgressionMMO.enums.ProjectileTag;
import ninja.lukasfend.ProgressionMMO.handlers.ProjectileTagger;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class EffectLevelup {

	public static void playEffect(Player p, boolean is99) {
		Location loc = p.getLocation();
		loc.add(new Vector(0,1.3,0));
		loc.add(loc.getDirection().normalize().multiply(0.4f));
		new ParticleBuilder(is99 ? ParticleEffect.SOUL_FIRE_FLAME : ParticleEffect.FLAME, loc)
				.setColor(Color.GREEN)
				.setAmount(50)
				.setSpeed(0.1f)
				.display();
		
	}
	
}
