package ninja.lukasfend.ProgressionMMO.skills;

import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import ninja.lukasfend.ProgressionMMO.handlers.MMOPlayer;

public class SkillThieving extends Skill {

	@Override
	public SkillType getType() {
		return SkillType.THIEVING;
	}

	@Override
	public void handleLevelup(MMOPlayer player, int level) {
		// TODO Auto-generated method stub

	}

}
