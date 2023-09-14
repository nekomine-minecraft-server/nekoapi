package net.nekomine.spigot.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class LocationUtil {

    @SuppressWarnings("ignore all")
    public List<Player> getNearPlayers(Player player, int radius) {
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        if (nearbyEntities.isEmpty()) {
            return Collections.emptyList();
        }
        List<Player> players = new ArrayList<>();
        for (Entity nearbyEntity : nearbyEntities) {
            if (nearbyEntity.getType() == EntityType.PLAYER) {
                players.add((Player) nearbyEntity);
            }
        }
        return Collections.unmodifiableList(players);
    }

}
