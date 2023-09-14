package net.nekomine.spigot.tag;

import net.nekomine.common.utility.BaseService;
import net.nekomine.common.utility.Service;
import net.nekomine.spigot.functional.Updater;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TagServiceImpl extends BaseService implements TagService{
    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    private final Plugin plugin;
    private TagListener tagListener;

    private boolean enabled = false;

    public TagServiceImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Tag createTag(Updater<Tag> updater) {
        return new BukkitTag(scoreboard, updater);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        super.enable();

        tagListener = new TagListener(scoreboard);
        Bukkit.getPluginManager().registerEvents(new TagListener(scoreboard), plugin);
    }

    @Override
    public void disable() {
        super.disable();

        scoreboard.getTeams().forEach(Team::unregister);
        HandlerList.unregisterAll(tagListener);
    }
}
