package ninja.lukasfend.ProgressionMMO.enums;

import org.bukkit.NamespacedKey;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;

public enum ProjectileTag {
	DEAL_NO_DAMAGE
	;
	
	public NamespacedKey key() {
		if(ProgressionMMO.projectileKeys.containsKey(this)) {
			return ProgressionMMO.projectileKeys.get(this);
		} else {
			ProgressionMMO.projectileKeys.put(this, new NamespacedKey(ProgressionMMO.getInstance(), "pmmo_"+this.toString()));
			return this.key();
		}
	}
}
