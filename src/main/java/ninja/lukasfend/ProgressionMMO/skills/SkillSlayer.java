package ninja.lukasfend.ProgressionMMO.skills;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.handlers.MMOPlayer;

public class SkillSlayer extends Skill {

	@Override
	public SkillType getType() {
		return SkillType.SLAYER;
	}

	@Override
	public void handleLevelup(MMOPlayer player, int level) {
		// TODO Auto-generated method stub

	}

}
