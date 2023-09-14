package net.nekomine.spigot.gameuser;

import org.bukkit.entity.Player;

public interface Gamer {

    String getName();

    Player getPlayer();

    String getDisplayName();
}
