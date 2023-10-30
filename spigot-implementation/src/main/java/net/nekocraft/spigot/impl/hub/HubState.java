package net.nekocraft.spigot.impl.hub;

import net.nekomine.common.service.VelocityServerService;
import net.nekomine.spigot.board.BoardService;
import net.nekomine.spigot.state.State;
import org.bukkit.plugin.Plugin;

public class HubState implements State {
    private final Plugin plugin;
    private final HubBoardUpdateTask hubBoardUpdateTask;

    public HubState(Plugin plugin, BoardService boardService, VelocityServerService velocityServerService) {
        this.plugin = plugin;
        HubBoardUpdater boardUpdater = new HubBoardUpdater(velocityServerService);
        this.hubBoardUpdateTask = new HubBoardUpdateTask(boardUpdater, boardService.createBoard(boardUpdater));
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
