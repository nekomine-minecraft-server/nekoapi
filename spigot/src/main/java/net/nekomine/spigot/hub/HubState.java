package net.nekomine.spigot.hub;

import net.nekomine.spigot.board.BoardService;
import net.nekomine.spigot.state.State;
import org.bukkit.plugin.Plugin;

public class HubState implements State {
    private final Plugin plugin;
    private final HubBoardUpdateTask hubBoardUpdateTask;

    public HubState(Plugin plugin, BoardService boardService) {
        this.plugin = plugin;
        this.hubBoardUpdateTask = new HubBoardUpdateTask(boardService.createBoard(new HubBoardUpdater()));
    }

    @Override
    public void start() {
        hubBoardUpdateTask.runTaskTimerAsynchronously(plugin, 20, 20);
    }

    @Override
    public void end() {
        hubBoardUpdateTask.cancel();
    }

    @Override
    public String getMapName() {
        return "lobby";
    }
}
