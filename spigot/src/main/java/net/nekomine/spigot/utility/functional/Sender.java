package net.nekomine.spigot.utility.functional;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Sender {
    void send(Player player);

}
