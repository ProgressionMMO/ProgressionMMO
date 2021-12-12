package ninja.lukasfend.ProgressionMMO.handlers;

import org.bukkit.entity.Projectile;
import org.bukkit.persistence.PersistentDataType;

import ninja.lukasfend.ProgressionMMO.enums.ProjectileTag;

/**
 * Allows the tagging of projectiles
 */
public class ProjectileTagger {
	
	public static Projectile addBool(Projectile proj, ProjectileTag tag, boolean value) {
		proj.getPersistentDataContainer().set(tag.key(), PersistentDataType.SHORT, (short) (value?1:0));
		return proj;
	}
	public static boolean hasBool(Projectile proj, ProjectileTag tag) {
		return proj.getPersistentDataContainer().has(tag.key(), PersistentDataType.SHORT);
	}
	public static boolean getBool(Projectile proj, ProjectileTag tag) {
		if(!hasBool(proj, tag)) {
			return false;
		} else {
			return proj.getPersistentDataContainer().get(tag.key(), PersistentDataType.SHORT) == 1;
		}
	}
	
	
	
	public static Projectile addDouble(Projectile proj, ProjectileTag tag, double value) {
		proj.getPersistentDataContainer().set(tag.key(), PersistentDataType.DOUBLE, value);
		return proj;
	}
	public static boolean hasDouble(Projectile proj, ProjectileTag tag) {
		return proj.getPersistentDataContainer().has(tag.key(), PersistentDataType.DOUBLE);
	}
	public static double getDouble(Projectile proj, ProjectileTag tag) {
		if(!hasBool(proj, tag)) {
			return 0f;
		} else {
			return proj.getPersistentDataContainer().get(tag.key(), PersistentDataType.DOUBLE);
		}
	}
	
	
	
	
	public static Projectile addString(Projectile proj, ProjectileTag tag, String value) {
		proj.getPersistentDataContainer().set(tag.key(), PersistentDataType.STRING, value);
		return proj;
	}
	public static boolean hasString(Projectile proj, ProjectileTag tag) {
		return proj.getPersistentDataContainer().has(tag.key(), PersistentDataType.STRING);
	}
	public static String getString(Projectile proj, ProjectileTag tag) {
		if(!hasBool(proj, tag)) {
			return "";
		} else {
			return proj.getPersistentDataContainer().get(tag.key(), PersistentDataType.STRING);
		}
	}
	
	
	
	public static Projectile addInt(Projectile proj, ProjectileTag tag, int value) {
		proj.getPersistentDataContainer().set(tag.key(), PersistentDataType.INTEGER, value);
		return proj;
	}
	public static boolean hasInt(Projectile proj, ProjectileTag tag) {
		return proj.getPersistentDataContainer().has(tag.key(), PersistentDataType.INTEGER);
	}
	public static int getInt(Projectile proj, ProjectileTag tag) {
		if(!hasBool(proj, tag)) {
			return 0;
		} else {
			return proj.getPersistentDataContainer().get(tag.key(), PersistentDataType.INTEGER);
		}
	}
	

}
