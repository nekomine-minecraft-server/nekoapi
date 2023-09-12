package net.nekomine.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.nekomine.common.model.VelocityServer;
import net.nekomine.common.redis.impl.EnvRedisFactory;
import net.nekomine.common.utility.Service;
import net.nekomine.velocity.service.VelocityServerServiceImpl;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

@Getter
public class VelocityApiPlugin {
    private final RedissonClient redissonClient;
    private final VelocityServer velocityServer;
    private final VelocityServerServiceImpl velocityServerService;
    private final List<Service> services = new ArrayList<>();

    @Inject
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
