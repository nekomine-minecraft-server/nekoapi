package net.nekomine.spigot.board;

import net.kyori.adventure.text.Component;
import net.nekomine.common.utility.BaseService;
import net.nekomine.spigot.functional.Updater;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class BoardServiceImpl extends BaseService implements BoardService {
    private final Map<String, BaseBoard<Component>> playerScoreboards = new HashMap<>();
    private final Plugin plugin;
    private BoardListener boardListener;

    public BoardServiceImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Board createBoard(Updater<Board> updater) {
        return new BoardImpl(playerScoreboards, plugin, updater);
    }

    @Override
    public void enable() {
        super.enable();

        boardListener = new BoardListener(playerScoreboards);
        Bukkit.getPluginManager().registerEvents(boardListener, plugin);
    }

    @Override
    public void disable() {
        super.disable();

        playerScoreboards.forEach((s, componentBaseBoard) -> removeBoardIfNotNull(componentBaseBoard));
        playerScoreboards.clear();

        HandlerList.unregisterAll(boardListener);
    }

    private void removeBoardIfNotNull(BaseBoard<Component> fastBoard) {
        if (fastBoard == null) {
            return;
        }

        fastBoard.delete();
    }
}
