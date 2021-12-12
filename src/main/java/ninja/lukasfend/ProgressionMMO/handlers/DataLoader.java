package ninja.lukasfend.ProgressionMMO.handlers;

import java.util.HashMap;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.skills.Skill;
import ninja.lukasfend.ProgressionMMO.skills.SkillStrength;

public class DataLoader {
	
	public static HashMap<SkillType, Skill> loadSkills() {
		HashMap<SkillType, Skill> skills = new HashMap<SkillType, Skill>();
		
		skills.put(SkillType.STRENGTH, new SkillStrength());
		
		return skills;
	}
	
}
