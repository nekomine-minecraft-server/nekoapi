package net.nekomine.spigot.npc;

import org.bukkit.Location;

public interface Npc {

    void spawn(Location location);

    void setGlowing(boolean glowing);

    boolean getGlowing();
}
