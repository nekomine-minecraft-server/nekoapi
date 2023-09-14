package net.nekomine.spigot.gameuser;

import org.bukkit.entity.Player;

public interface Spectator {

    String getName();

    Player getPlayer();

    boolean isAlwaysFly();
}
