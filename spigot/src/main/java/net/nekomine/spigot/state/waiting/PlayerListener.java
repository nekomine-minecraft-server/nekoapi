package net.nekomine.spigot.state.waiting;

import net.nekomine.spigot.board.Board;
import net.nekomine.spigot.board.BoardService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class PlayerListener implements Listener {
    private final int maxPlayers;
    private final Board waitingBoard;
    private final Location spawnLocation;

    public PlayerListener(int maxPlayers, BoardService boardService, WaitingBoardUpdater updater, Location spawnLocation) {
        this.maxPlayers = maxPlayers;
        this.spawnLocation = spawnLocation;
        this.waitingBoard = boardService.createBoard(updater);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        int online = Bukkit.getOnlinePlayers().size();

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage("Игрок зашёл на " + online + "/" + maxPlayers);
        });

        Player player = event.getPlayer();

        waitingBoard.send(player);
        player.teleport(spawnLocation);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        int online = Bukkit.getOnlinePlayers().size() - 1;

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage("Игрок вышел на " + online + "/" + maxPlayers);
        });
    }
}
