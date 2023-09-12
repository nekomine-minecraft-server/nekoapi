package net.nekomine.spigot.functional;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Deleter {
    void delete(Player player);

}
