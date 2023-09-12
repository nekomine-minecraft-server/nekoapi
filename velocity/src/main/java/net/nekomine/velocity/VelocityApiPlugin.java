package net.nekomine.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.nekomine.common.model.VelocityServer;

import java.util.ArrayList;

import net.nekomine.common.redis.impl.EnvRedisFactory;
import net.nekomine.velocity.service.VelocityServerServiceImpl;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;

import java.nio.file.Path;

public class VelocityApiPlugin {
    private final RedissonClient redissonClient;
    private final VelocityServer velocityServer;
    private final VelocityServerServiceImpl velocityServerService;

    @Inject
    public VelocityApiPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, CommandManager commandManager) {
        redissonClient = new EnvRedisFactory().create();
        velocityServer = new VelocityServer(getProxyName(dataDirectory), new ArrayList<>(), server.getConfiguration().getShowMaxPlayers());
        velocityServerService = new VelocityServerServiceImpl(redissonClient, this, velocityServer, server);
    }

    /**
     * Получить имя прокси по папке
     */
    public String getProxyName(Path dataDirectory) {
        try {
            return dataDirectory
                    .toFile()
                    .getCanonicalFile()
                    .getParentFile()
                    .getParentFile()
                    .getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
