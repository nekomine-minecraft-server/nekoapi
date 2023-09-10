package net.nekomine.spigot.state.waiting;

import net.nekomine.spigot.state.StateService;
import net.nekomine.spigot.board.BoardService;
import net.nekomine.spigot.state.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class WaitingState implements State {
    private final Plugin plugin;
    private final BoardService boardService;
    private final Location spawnLocation;
    private final int maxPlayers;
    private WaitingBoardUpdater waitingBoardUpdater = new WaitingBoardUpdater();
    private PlayerListener playerListener;

    public WaitingState(Plugin plugin, BoardService boardService, Location spawnLocation, int maxPlayers) {
        this.plugin = plugin;
        this.spawnLocation = spawnLocation;
        this.maxPlayers = maxPlayers;
        this.boardService = boardService;
    }

    @Override
    public void start() {
        playerListener = new PlayerListener(maxPlayers, boardService, waitingBoardUpdater, spawnLocation);

        Bukkit.getPluginManager().registerEvents(playerListener, plugin);
    }

    @Override
    public void end() {
        HandlerList.unregisterAll(playerListener);
    }

    public void setWaitingBoardUpdater(WaitingBoardUpdater waitingBoardUpdater) {
        this.waitingBoardUpdater = waitingBoardUpdater;
    }
}
