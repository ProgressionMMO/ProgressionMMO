package ninja.lukasfend.ProgressionMMO.features;

import ninja.lukasfend.ProgressionMMO.ProgressionMMO;
import ninja.lukasfend.ProgressionMMO.enums.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Helper class for handling bossbars (eg. the XP bars when training a skill)
 */
public class BossBarHandler {

    private int taskID;
    private static BossBarHandler instance;
    private HashMap<String, HashMap<SkillType, BossBar>> skillBars = new HashMap<>();
    private HashMap<String, HashMap<SkillType, Long>> expirationTime = new HashMap<>();

    private BossBarHandler() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ProgressionMMO.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(String playerUUID : skillBars.keySet()) {
                    for(SkillType skillType : expirationTime.get(playerUUID).keySet()) {
                        long expTime = expirationTime.get(playerUUID).get(skillType);
                        long currentTime = System.currentTimeMillis();
                        if(expTime <= currentTime) {
                            BossBar bb = getBar(Bukkit.getPlayer(UUID.fromString(playerUUID)), skillType);
                            bb.setVisible(false);
                            expirationTime.get(playerUUID).remove(skillType);
                        }
                    }
                }
            }
        }, 0, 20);
    }

    /**
     * Returns the singleton instance of the bossbarhandler
     * @return The instance of the boss bar handler
     */
    public static BossBarHandler getInstance() {
        if(instance == null)
            instance = new BossBarHandler();

        return instance;
    }

    /**
     * Returns the player's boss bar for a skill
     * @param p The player to get the boss bar for
     * @param skillType The type of skill the boss bar is of
     * @return The BossBar
     */
    public BossBar getBar(Player p, SkillType skillType) {
        if(!hasPlayer(p))
            loadPlayer(p);

        if(skillBars.get(p.getUniqueId().toString()).containsKey(skillType)) {
            return skillBars.get(p.getUniqueId().toString()).get(skillType);
        }

        HashMap<SkillType, BossBar> playerBars = skillBars.get(p.getUniqueId().toString());

        BossBar newBar = Bukkit.createBossBar(skillType.getSkillName(), BarColor.YELLOW, BarStyle.SOLID);
        newBar.addPlayer(p);

        playerBars.put(skillType, newBar);

        return getBar(p, skillType);
    }

    /**
     * Returns the timestamp when a bossbar should turn invisible again
     * @param p The player
     * @param skillType The skill type for the bossbar
     * @return The timestamp (in ms) when the bar should be hidden - returns current timestamp if no entry is in memory
     */
    public long getExpirationTime(Player p, SkillType skillType) {
        if(expirationTime.containsKey(p.getUniqueId().toString())) {
            if(expirationTime.get(p.getUniqueId().toString()).containsKey(skillType)) {
                return expirationTime.get(p.getUniqueId().toString()).get(skillType);
            } else {
                return System.currentTimeMillis();
            }
        }
        return System.currentTimeMillis();
    }

    /**
     * Makes sure the boss bar is shown for at least a given duration (extends previous set values if needed)
     * @param p The player
     * @param skillType The type of the skill
     * @param timeInMs The time it should remain visible (in milliseconds)
     */
    public void showForAtLeast(Player p, SkillType skillType, long timeInMs) {
        BossBar bb = getBar(p, skillType);
        bb.setVisible(true);
        expirationTime.get(p.getUniqueId().toString()).put(skillType, System.currentTimeMillis() + timeInMs);
    }




    /**
     * Returns if a player is in the bossbar memory at the moment
     * @param p The player
     * @return True if the player is currently in the bossbar memory
     */
    public boolean hasPlayer(Player p) {
        return skillBars.containsKey(p.getUniqueId().toString());
    }

    /**
     * Loads a player to the bossbar memory
     * @param p The player to be load
     */
    public void loadPlayer(Player p) {
        if(!hasPlayer(p)) {
            skillBars.put(p.getUniqueId().toString(), new HashMap<>());
            expirationTime.put(p.getUniqueId().toString(), new HashMap<>());
        }
    }

    /**
     * Unloads a player from the bossbar memory
     * @param p The player to be unload
     */
    public void unloadPlayer(Player p) {
        if(!hasPlayer(p)) {
            return;
        }
        skillBars.remove(p.getUniqueId().toString());
        expirationTime.remove(p.getUniqueId().toString());
    }

}
