package ninja.lukasfend.ProgressionMMO.handlers;

import java.util.HashMap;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.skills.*;

public class DataLoader {

	/**
	 * Loads all skills into a list and assigns the handlers
	 * @return The hashmap of skillhandlers by their enum
	 */
	public static HashMap<SkillType, Skill> loadSkills() {
		HashMap<SkillType, Skill> skills = new HashMap<SkillType, Skill>();

		skills.put(SkillType.AGILITY, new SkillAgility());
		skills.put(SkillType.ARCHERY, new SkillArchery());
		skills.put(SkillType.ARMORSMITHING, new SkillArmorsmithing());
		skills.put(SkillType.ATTACK, new SkillAttack());
		skills.put(SkillType.DEFENSE, new SkillDefense());
		skills.put(SkillType.FARMING, new SkillFarming());
		skills.put(SkillType.FISHING, new SkillFishing());
		skills.put(SkillType.HEALTH, new SkillHealth());
		skills.put(SkillType.HERBLORE, new SkillHerblore());
		skills.put(SkillType.MINING, new SkillMining());
		skills.put(SkillType.SLAYER, new SkillSlayer());
		skills.put(SkillType.SORCERY, new SkillSorcery());
		skills.put(SkillType.STRENGTH, new SkillStrength());
		skills.put(SkillType.SUMMONING, new SkillSummoning());
		skills.put(SkillType.THIEVING, new SkillThieving());
		skills.put(SkillType.WEAPONSMITHING, new SkillWeaponsmithing());

		return skills;
	}
	
}
