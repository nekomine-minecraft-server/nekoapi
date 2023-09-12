package net.nekomine.spigot.tag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nekomine.spigot.functional.Updater;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

class BukkitTag implements Tag {
    private final Scoreboard scoreboard;
    private final Updater<Tag> updater;
    private Component prefix;
    private Component suffix;
    private Component name;

    public BukkitTag(Scoreboard scoreboard, Updater<Tag> updater) {
        this.scoreboard = scoreboard;
        this.updater = updater;
    }

    @Override
    public void send(Player player) {
        if (!player.isOnline()) {
            return;
        }

        if (updater != null) {
            updater.update(this);
        }

        Team team = scoreboard.getTeam(player.getName());

        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }

        team.displayName(prefix.append(name).append(suffix));
        team.suffix(suffix);
        team.prefix(prefix);
        team.addEntry(player.getName());

        team.color(NamedTextColor.GOLD);
        team.setCanSeeFriendlyInvisibles(true);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setAllowFriendlyFire(true);
    }

    @Override
    public Component getPrefix() {
        return prefix;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public void setPrefix(Component prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(Component suffix) {
        this.suffix = suffix;
    }

    @Override
    public void setName(Component name) {
        this.name = name;
    }

    @Override
    public void delete(Player player) {
        Team team = scoreboard.getTeam(player.getName());

        if (team == null) {
            return;
        }

        team.unregister();
    }
}
