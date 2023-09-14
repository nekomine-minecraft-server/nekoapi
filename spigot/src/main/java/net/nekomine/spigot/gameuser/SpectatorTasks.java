package net.nekomine.spigot.gameuser;

import lombok.val;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jboss.marshalling.Pair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpectatorTasks extends BukkitRunnable {
    private final Map<String, Map<String, Runnable>> tasks = new ConcurrentHashMap<>();
    private final SpectatorService spectatorService;

    public SpectatorTasks(SpectatorService spectatorService) {
        this.spectatorService = spectatorService;
    }

    @SuppressWarnings("unused")
    public void findNearestTask(Player player) {
        if (player == null) {
            return;
        }

        Runnable runnable = () -> {
            if (player.getInventory().getItemInMainHand().getType() != Material.COMPASS) {
                return;
            }

            val nearestPlayer = findNearestPlayer(player);
            Player nearest = nearestPlayer.getA();
            if (nearest == null || !nearest.isOnline()) {
                player.setCompassTarget(player.getWorld().getSpawnLocation());
                return;
            }

            player.setCompassTarget(nearest.getLocation());
            player.sendActionBar(Component.text("COMPASS_MESSAGE_SPECTATOR" + nearest.getName() + String.format("%.2f", nearestPlayer.getB()))); //TODO: локализовать
        };
        addRunnable(player.getName(), "FIND_NEAREST_TASK", runnable);
    }

    public Pair<Player, Double> findNearestPlayer(Player player) {
        Player nearest = null;
        double nearestDistance = 0D;

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() != target.getWorld()) {
                continue;
            }

            if (!spectatorService.isSpectator(target.getName())) {
                continue;
            }

            if (nearest == null || nearestDistance > player.getLocation().distance(target.getLocation())) {
                double distance = distance(player.getLocation(), target.getLocation());

                nearest = target;
                nearestDistance = distance;
            }
        }

        return Pair.create(nearest, nearestDistance);
    }

    public double distance(Location location1, Location location2) {
        if (location1.getWorld().equals(location2.getWorld())) {
            return location1.distance(location2);
        }
        return -1;
    }

    @SuppressWarnings("ignore all")
    private void addRunnable(String playerName, String key, Runnable runnable) {
        String name = playerName.toLowerCase();
        Map<String, Runnable> runnableMap = tasks.get(name);
        if (runnableMap == null) {
            runnableMap = new ConcurrentHashMap<>();
            tasks.put(name, runnableMap);
        }

        runnableMap.put(key, runnable);
    }

    public void cancelAllTasks(Player player) {
        if (player == null) {
            return;
        }

        tasks.remove(player.getName().toLowerCase());
    }

    @SuppressWarnings("unused")
    public void cancelTaskByKey(Player player, String key) {
        if (player == null) {
            return;
        }

        String name = player.getName().toLowerCase();
        Map<String, Runnable> remove = tasks.get(name);
        if (remove == null) {
            return;
        }

        remove.remove(key);
        if (remove.isEmpty()) {
            tasks.remove(name);
        }
    }

    /**
     * очистить все таски
     */
    public void clearAll() {
        tasks.clear();
    }

    @Override
    public void run() {
        for (Map<String, Runnable> value : tasks.values()) {
            for (Runnable runnable : value.values()) {
                runnable.run();
            }
        }
    }
}
