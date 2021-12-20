package ninja.lukasfend.ProgressionMMO.features;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.datahandlers.MMOPlayer;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

/**
 * Implements the scoreboard that shows player's top levels
 */
public class FeatureScoreboard implements Listener {

    private final static ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static final Scoreboard board = manager.getNewScoreboard();
    private final static Objective objective = board.registerNewObjective("Skills", "Level");
    private final static Scoreboard empty = Bukkit.getScoreboardManager().getNewScoreboard();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        MMOPlayer mp = MMOPlayer.get(p);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ProgressionMMO.getInstance(), new Runnable() {
            public void run() {
                if(!mp.isUsingScoreboard()) {
                    p.setScoreboard(empty);
                    return;
                };
                // TODO: Performance increase is heavily needed here...
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                final Scoreboard board = manager.getNewScoreboard();
                final Objective objective = board.registerNewObjective("Skills", "Level");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(" §4§l§k,,;;|| §2§n§lYour Levels§r §4§l§k||;;,, ");

                for(SkillType skill : ProgressionMMO.skills.keySet()) {
                    Score entry = objective.getScore("§6"+skill.getSkillName());
                    entry.setScore(mp.getLevel(skill));
                }

                p.setScoreboard(board);
            }
        },0, 20 * 3);
    }
}
