package net.nekomine.spigot.board;

import net.kyori.adventure.text.Component;
import net.nekomine.spigot.functional.Deleter;
import net.nekomine.spigot.functional.Sender;
import net.nekomine.spigot.functional.Updater;
import org.bukkit.entity.Player;

import java.util.Map;

public interface Board extends Sender, Deleter {

    Component getTitle();

    void setTitle(Component title);

    Map<Integer, Component> getLineMap();

    Updater<Board> getUpdater();

    void setUpdater(Updater<Board> updater);

    void send(Player player);

}
