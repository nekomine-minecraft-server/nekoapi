package net.nekomine.spigot.tag;

import net.nekomine.spigot.Service;
import net.nekomine.spigot.utility.functional.Updater;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TagServiceImpl extends Service implements TagService {
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
        if (!isEnabled()) {
            enabled = true;
            tagListener = new TagListener(scoreboard);
            Bukkit.getPluginManager().registerEvents(new TagListener(scoreboard), plugin);
            return;
        }

        throw new IllegalArgumentException("Сервис уже включён!");
    }

    @Override
    public void disable() {
        if (isEnabled()) {
            enabled = false;
            scoreboard.getTeams().forEach(Team::unregister);

            HandlerList.unregisterAll(tagListener);
            return;
        }

        throw new IllegalArgumentException("Сервис уже выключен!");
    }
}
