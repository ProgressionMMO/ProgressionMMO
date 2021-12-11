package ninja.lukasfend.ProgressionMMO.enums;

import org.bukkit.Color;
import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import ninja.lukasfend.ProgressionMMO.skills.Skill;
import ninja.lukasfend.ProgressionMMO.skills.SkillStrength;

public enum SkillType {
	AGILITY,
	ARCHERY,
	ARMORSMITHING,
	ATTACK,
	DEFENSE,
	FARMING,
	FISHING,
	HEALTH,
	HERBLORE,
	MINING,
	SLAYER,
	SORCERY,
	STRENGTH,
	SUMMONING,
	THIEVING,
	WEAPONSMITHING
	;
	
	public String getSkillName() {
		switch(this) {
			default:
				return this.toString().substring(0,1).toUpperCase() + this.toString().substring(1).toLowerCase();
		}
	}
	
	public String getDescriptionLore() {
		switch(this) {
			case STRENGTH: 	
				return "\nStrength is a skill that is trained whenever\n"
						+ "you use melee attacks against opponents.\n"
						+ "The stronger the opponent,\n"
						+ "the more XP you'll get.\n"
						+ "Leveling up this skill will provide you\n"
						+ "with extra damage on all melee attacks";
			default:
				return "";
	}
	}
	
	public static SkillType fromString(String name) {
		switch(name.toUpperCase()) {
			case "AGILITY": 	
				return AGILITY;
			case "ARCHERY":
				return ARCHERY;
			case "ARMORSMITHING":
				return ARMORSMITHING;
			case "ATTACK":
				return ATTACK;
			case "DEFENSE":
				return DEFENSE;
			case "FARMING":
				return FARMING;
			case "FISHING":
				return FISHING;
			case "HEALTH":
				return HEALTH;
			case "HERBLORE":
				return HERBLORE;
			case "MINING":
				return MINING;
			case "SLAYER":
				return SLAYER;
			case "SORCERY":
				return SORCERY;
			case "STRENGTH":
				return STRENGTH;
			case "SUMMONING":
				return SUMMONING;
			case "THIEVING":
				return THIEVING;
			case "WEAPONSMITHING":
				return WEAPONSMITHING;
			default:
				return null;
		}
	}
	
	public String getColor() {
		switch(this) {
			case STRENGTH:
				return "§c";
			default:
				return "§0";
		}
	}
	
	public Material getIconMaterial() {
		switch(this) {
			case AGILITY: 	
				return Material.FEATHER;
			case ARCHERY:
				return Material.BOW;
			case ARMORSMITHING:
				return Material.ANVIL;
			case ATTACK:
				return Material.GOLDEN_SWORD;
			case DEFENSE:
				return Material.DIAMOND_CHESTPLATE;
			case FARMING:
				return Material.GOLDEN_HOE;
			case FISHING:
				return Material.FISHING_ROD;
			case HEALTH:
				return Material.TOTEM_OF_UNDYING;
			case HERBLORE:
				return Material.POTION;
			case MINING:
				return Material.DIAMOND_PICKAXE;
			case SLAYER:
				return Material.ZOMBIE_HEAD;
			case SORCERY:
				return Material.BLAZE_POWDER;
			case STRENGTH:
				return Material.NETHERITE_AXE;
			case SUMMONING:
				return Material.PAPER;
			case THIEVING:
				return Material.EMERALD;
			case WEAPONSMITHING:
				return Material.ARROW;
			default:
				return Material.AIR;
		}
	}
}
