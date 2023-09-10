package net.nekomine.spigot.board;

import net.kyori.adventure.text.Component;
import net.nekomine.spigot.utility.functional.Updater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

class BoardImpl implements Board {
    protected final Map<String, BaseBoard<Component>> baseBoardMap;
    protected final Map<Integer, Component> lineMap = new TreeMap<>(Comparator.reverseOrder());
    protected final Plugin plugin;
    protected Updater<Board> updater;
    protected Component title;

    public BoardImpl(Map<String, BaseBoard<Component>> baseBoardMap, Plugin plugin, Updater<Board> updater) {
        this.baseBoardMap = baseBoardMap;
        this.plugin = plugin;
        this.updater = updater;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public void setTitle(Component title) {
        this.title = title;
    }

    @Override
    public Map<Integer, Component> getLineMap() {
        return lineMap;
    }

    @Override
    public Updater<Board> getUpdater() {
        return updater;
    }

    @Override
    public void setUpdater(Updater<Board> updater) {
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

        if (lineMap.isEmpty()) {
            return;
        }

        BaseBoard<Component> baseBoard = baseBoardMap.getOrDefault(player.getName(), new BaseComponentBoard(player));

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            baseBoard.updateTitle(title);
            baseBoard.updateLines(lineMap.values());
        });
    }

    @Override
    public void delete(Player player) {
        BaseBoard<Component> board = baseBoardMap.remove(player.getName());

        if (board == null) {
            return;
        }

        board.delete();
    }
}
