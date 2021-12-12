package ninja.lukasfend.ProgressionMMO.enums;

import org.bukkit.NamespacedKey;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;

/**
 * Tags that are assigned to shot projectiles
 */
public enum ProjectileTag {
	DEAL_NO_DAMAGE
	;

	/**
	 * Creates a key
	 * @return A namespaced key instance for the tag
	 */
	public NamespacedKey key() {
		if(ProgressionMMO.projectileKeys.containsKey(this)) {
			return ProgressionMMO.projectileKeys.get(this);
		} else {
			ProgressionMMO.projectileKeys.put(this, new NamespacedKey(ProgressionMMO.getInstance(), "pmmo_"+this.toString()));
			return this.key();
		}
	}
}
