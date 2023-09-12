package net.nekomine.velocity.service;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import net.nekomine.common.model.VelocityServer;
import net.nekomine.common.service.impl.VelocityServerService;
import net.nekomine.common.utility.Service;
import net.nekomine.velocity.VelocityApiPlugin;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class VelocityServerServiceImpl extends VelocityServerService implements Service {
    private final VelocityApiPlugin velocityApiPlugin;
    private final VelocityServer VelocityServer;
    private final ProxyServer proxyServer;

    public VelocityServerServiceImpl(RedissonClient redissonClient, VelocityApiPlugin velocityApiPlugin, VelocityServer VelocityServer, ProxyServer proxyServer) {
        super(redissonClient, "VELOCITY_SERVER_MAP");
        this.velocityApiPlugin = velocityApiPlugin;
        this.VelocityServer = VelocityServer;
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
                    .buildTask(velocityApiPlugin, () -> roleMap.put(VelocityServer.getKey(), VelocityServer))
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