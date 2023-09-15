package net.nekomine.spigot.hub;

import lombok.RequiredArgsConstructor;
import net.nekomine.spigot.board.Board;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class HubBoardUpdateTask extends BukkitRunnable {
    private final Board board;

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(board::send);
    }
}
