package net.nekocraft.spigot.impl.tag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

class TagListener implements Listener {
    private final Scoreboard scoreboard;

    public TagListener(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setScoreboard(scoreboard);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoin2(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setScoreboard(scoreboard);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        Team team = scoreboard.getTeam(name);

        if (team == null) {
            return;
        }

        team.unregister();
    }
}
