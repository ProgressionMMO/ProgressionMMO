package ninja.lukasfend.ProgressionMMO.mobs;

import java.util.ArrayList;

/**
 * A helper class that defines the stats and abilities of an entity
 */
public class MMOEntityStats {

    public int level;

    public int healthBase;
    public int healthPerLevel;

    public float damageBase;
    public float damagePerLevel;

    public int abilityPowerBase;
    public int abilityPowerPerLevel;

    public float critChanceBase;
    public float critChancePerLevel;

    public float critDamageBase;
    public float critDamagePerLevel;

    public int armorBase;
    public int armorPerLevel;

    public int magicResistBase;
    public int magicResistPerLevel;

    public float movementspeedBase;
    public float movementspeedPerLevel;

    // TODO: Add type for weakness (eg. armor reduction), etc.
    public ArrayList<String> weaknesses = new ArrayList<String>();
    // TODO: Add type for buffs (eg. armor penetration)
    public ArrayList<String> buffs = new ArrayList<String>();

    /*
    *       Calculation Functions
    */

    /**
     * Returns the total amount of HP the entity has
     */
    public int getTotalMaxHP() {
        return healthBase + (healthPerLevel*level);
    }

    /**
     * Returns the total physical damage of the entity
     */
    public float getTotalDamage() {
        return damageBase + (damagePerLevel*level);
    }

    /**
     * Returns the total ability power the entity has
     */
    public int getTotalAbilityPower() {
        return abilityPowerBase + (abilityPowerPerLevel*level);
    }

    /**
     * Returns the total crit chance of the entity
     */
    public float getTotalCritChance() {
        return critChanceBase + (critChancePerLevel*level);
    }

    /**
     * Returns the total crit damage multiplicator of the entity
     */
    public float getTotalCritDamage() {
        return critDamageBase + (critDamagePerLevel*level);
    }

    /**
     * Returns the total armor points the entity has
     */
    public int getTotalArmor() {
        return armorBase + (armorPerLevel*level);
    }

    /**
     * Returns the total magic resist points the entity has
     */
    public int getTotalMagicResist() {
        return magicResistBase + (magicResistPerLevel*level);
    }

    /**
     * Returns the total amount of movement speed the entity has
     */
    public float getTotalMovementspeed() {
        return movementspeedBase + (movementspeedPerLevel*level);
    }

}
