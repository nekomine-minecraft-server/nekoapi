package net.nekomine.spigot.board;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

class BoardListener implements Listener {
    private final Map<String, BaseBoard<Component>> playerScoreboards;

    public BoardListener(Map<String, BaseBoard<Component>> playerScoreboards) {
        this.playerScoreboards = playerScoreboards;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        removeBoardIfNotNull(playerScoreboards.remove(name));
    }

    private void removeBoardIfNotNull(BaseBoard<Component> fastBoard) {
        if (fastBoard == null) {
            return;
        }

        fastBoard.delete();
    }
}
