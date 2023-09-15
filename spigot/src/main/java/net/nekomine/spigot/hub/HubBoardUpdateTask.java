package net.nekomine.spigot.hub;

import lombok.RequiredArgsConstructor;
import net.nekomine.spigot.board.Board;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class HubBoardUpdateTask extends BukkitRunnable {
    private final HubBoardUpdater boardUpdater;
    private final Board board;
    private byte i;

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(board::send);

        if (i == 2) {
            boardUpdater.updateOnline();
            i = 0;
            return;
        }

        i++;
    }
}
