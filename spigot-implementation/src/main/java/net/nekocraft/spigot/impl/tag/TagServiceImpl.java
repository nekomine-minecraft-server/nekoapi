package net.nekocraft.spigot.impl.tag;

import net.nekomine.common.utility.BaseService;
import net.nekomine.spigot.functional.Updater;
import net.nekomine.spigot.tag.Tag;
import net.nekomine.spigot.tag.TagService;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("unused")
public class TagServiceImpl extends BaseService implements TagService {
    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    private final Plugin plugin;
    private TagListener tagListener;

    public TagServiceImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Tag createTag(Updater<Tag> updater) {
        return new BukkitTag(scoreboard, updater);
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
