package net.nekocraft.velocity.impl;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.nekocraft.common.impl.redis.EnvRedisFactory;
import net.nekocraft.velocity.impl.server.VelocityServerServiceImpl;
import net.nekomine.common.model.VelocityServer;
import net.nekomine.common.utility.Service;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
public class VelocityApiPlugin {
    private final RedissonClient redissonClient;
    private final VelocityServer velocityServer;
    private final VelocityServerServiceImpl velocityServerService;
    private final List<Service> services = new ArrayList<>();

    @Inject
    @SuppressWarnings("unused")
    public VelocityApiPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, CommandManager commandManager) {
        redissonClient = new EnvRedisFactory().create();
        velocityServer = new VelocityServer(getProxyName(dataDirectory), new ArrayList<>(), server.getConfiguration().getShowMaxPlayers());
        velocityServerService = new VelocityServerServiceImpl(redissonClient, this, velocityServer, server);

        services.add(velocityServerService);
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
