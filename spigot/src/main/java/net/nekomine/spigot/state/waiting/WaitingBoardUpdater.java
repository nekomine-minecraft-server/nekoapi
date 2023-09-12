package net.nekomine.spigot.state.waiting;

import net.kyori.adventure.text.Component;
import net.nekomine.spigot.board.Board;
import net.nekomine.spigot.functional.Updater;

import java.util.Map;

import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

public class WaitingBoardUpdater implements Updater<Board> {
    @Override
    public void update(Board board) {
        board.setTitle(Component.text("Ожидание игры").color(YELLOW));

        Map<Integer, Component> lineMap = board.getLineMap();
        lineMap.put(1, Component.text("www.site.net").color(YELLOW));
    }
}
