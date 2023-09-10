package net.nekomine.spigot.utility.functional;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Deleter {
    void delete(Player player);

}
