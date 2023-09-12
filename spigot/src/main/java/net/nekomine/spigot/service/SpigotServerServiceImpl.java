package net.nekomine.spigot.service;

import net.nekomine.common.model.SpigotServer;
import net.nekomine.common.service.impl.SpigotServerService;
import net.nekomine.common.utility.Service;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.redisson.api.RedissonClient;

public class SpigotServerServiceImpl extends SpigotServerService implements Service {
    private final SpigotServer spigotServer;
    private final Plugin plugin;

    public SpigotServerServiceImpl(RedissonClient redissonClient, SpigotServer spigotServer, Plugin plugin) {
        super(redissonClient);
        this.spigotServer = spigotServer;
        this.plugin = plugin;
    }

    private boolean enabled;
    private BukkitTask bukkitTask;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        if (!isEnabled()) {
            enabled = true;
            bukkitTask = new BukkitRunnable() {
                @Override
                public void run() {
                    roleMap.put(spigotServer.getKey(), spigotServer);
                }
            }.runTaskTimerAsynchronously(plugin, 0L, 20L);
            return;
        }

        throw new IllegalArgumentException("Сервис уже включён!");
    }

    @Override
    public void disable() {
        if (isEnabled()) {
            enabled = false;
            bukkitTask.cancel();
            return;
        }

        throw new IllegalArgumentException("Сервис уже выключен!");
    }
}