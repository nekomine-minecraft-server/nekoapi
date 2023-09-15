package net.nekomine.spigot.hub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.nekomine.common.model.VelocityServer;
import net.nekomine.common.service.impl.VelocityServerService;
import net.nekomine.spigot.board.Board;
import net.nekomine.spigot.functional.Updater;
import org.bukkit.Bukkit;

import java.util.Map;

public class HubBoardUpdater implements Updater<Board> {
    private final VelocityServerService velocityServerService;
    private final Component title = Component.text()
            .content("NekoMine")
            .style(Style.style(TextDecoration.BOLD))
            .color(NamedTextColor.AQUA)
            .build();
    private final Component site = Component.text("www.nekomine.net").color(NamedTextColor.YELLOW);

    private final Component hubOnlineText = Component.text("Онлайна хаба: ");
    private final Component totalOnlineText = Component.text("Онлайн проекта: ");
    private Component totalOnline = Component.text(String.valueOf(0)).color(NamedTextColor.GREEN);
    private Component hubOnline = Component.text(String.valueOf(0)).color(NamedTextColor.GREEN);

    public HubBoardUpdater(VelocityServerService velocityServerService) {
        this.velocityServerService = velocityServerService;
    }

    public void updateOnline() {
        hubOnline = Component.text(String.valueOf(Bukkit.getOnlinePlayers().size())).color(NamedTextColor.GREEN);
        totalOnline = Component.text(String.valueOf(velocityServerService.findAll().stream().mapToInt(VelocityServer::getOnline).sum())).color(NamedTextColor.GREEN);
    }

    @Override
    public void update(Board board) { //TODO локализовать
        board.setTitle(title);

        Map<Integer, Component> lineMap = board.getLineMap();
        lineMap.put(4, hubOnlineText.append(hubOnline));
        lineMap.put(3, totalOnlineText.append(totalOnline));
        lineMap.put(2, null);
        lineMap.put(1, site);
    }
}
