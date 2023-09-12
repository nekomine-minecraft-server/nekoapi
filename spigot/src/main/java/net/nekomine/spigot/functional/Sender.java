package net.nekomine.spigot.functional;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Sender {
    void send(Player player);

}
