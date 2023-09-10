package net.nekomine.spigot.board;

import net.kyori.adventure.text.Component;
import net.nekomine.spigot.Service;
import net.nekomine.spigot.utility.functional.Updater;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class BoardServiceImpl extends Service implements BoardService {
    private final Map<String, BaseBoard<Component>> playerScoreboards = new HashMap<>();
    private final Plugin plugin;
    private BoardListener boardListener;
    private boolean enabled;

    public BoardServiceImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Board createBoard(Updater<Board> updater) {
        return new BoardImpl(playerScoreboards, plugin, updater);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        if (!isEnabled()) {
            enabled = true;

            boardListener = new BoardListener(playerScoreboards);
            Bukkit.getPluginManager().registerEvents(boardListener, plugin);
            return;
        }

        throw new IllegalArgumentException("Сервис уже включён!");
    }

    @Override
    public void disable() {
        if (isEnabled()) {
            enabled = false;
            playerScoreboards.forEach((s, componentBaseBoard) -> removeBoardIfNotNull(componentBaseBoard));
            playerScoreboards.clear();

            HandlerList.unregisterAll(boardListener);
            return;
        }

        throw new IllegalArgumentException("Сервис уже выключен!");
    }

    private void removeBoardIfNotNull(BaseBoard<Component> fastBoard) {
        if (fastBoard == null) {
            return;
        }

        fastBoard.delete();
    }
}
