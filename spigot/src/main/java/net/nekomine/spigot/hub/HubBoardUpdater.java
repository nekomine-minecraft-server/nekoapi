package net.nekomine.spigot.hub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.nekomine.spigot.board.Board;
import net.nekomine.spigot.functional.Updater;

import java.util.Map;

public class HubBoardUpdater implements Updater<Board> {
    private final Component title = Component.text()
            .color(NamedTextColor.AQUA)
            .style(Style.style(TextDecoration.BOLD))
            .content("NekoMine")
            .build();

    private final Component site = Component.text("www.nekomine.net").color(NamedTextColor.YELLOW);

    @Override
    public void update(Board board) { //TODO локализовать
        board.setTitle(title);

        Map<Integer, Component> lineMap = board.getLineMap();
        lineMap.put(2, Component.text(" "));
        lineMap.put(1, site);
    }
}
