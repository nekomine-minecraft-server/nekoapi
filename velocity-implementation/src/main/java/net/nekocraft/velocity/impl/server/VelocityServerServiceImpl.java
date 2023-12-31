package net.nekocraft.velocity.impl.server;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import net.nekocraft.velocity.impl.VelocityApiPlugin;
import net.nekomine.common.model.VelocityServer;
import net.nekomine.common.utility.Service;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class VelocityServerServiceImpl extends net.nekocraft.common.impl.service.VelocityServerServiceImpl implements Service {
    private final VelocityApiPlugin velocityApiPlugin;
    private final VelocityServer velocityServer;
    private final ProxyServer proxyServer;

    public VelocityServerServiceImpl(RedissonClient redissonClient, VelocityApiPlugin velocityApiPlugin, VelocityServer VelocityServer, ProxyServer proxyServer) {
        super(redissonClient);
        this.velocityApiPlugin = velocityApiPlugin;
        this.velocityServer = VelocityServer;
        this.proxyServer = proxyServer;
    }

    private boolean enabled;
    private ScheduledTask scheduledTask;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        if (!isEnabled()) {
            enabled = true;
            scheduledTask = proxyServer.getScheduler()
                    .buildTask(velocityApiPlugin, () -> {
                        velocityServer.setPlayers(proxyServer.getAllPlayers()
                                .stream()
                                .map(Player::getUsername)
                                .toList());
                        serverMap.put(velocityServer.getId(), velocityServer);
                    })
                    .repeat(20L, TimeUnit.SECONDS)
                    .schedule();
            return;
        }

        throw new IllegalArgumentException("Сервис уже включён!");
    }

    @Override
    public void disable() {
        if (isEnabled()) {
            enabled = false;
            scheduledTask.cancel();
            return;
        }

        throw new IllegalArgumentException("Сервис уже выключен!");
    }
}