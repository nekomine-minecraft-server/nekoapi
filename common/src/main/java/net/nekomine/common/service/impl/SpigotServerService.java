package net.nekomine.common.service.impl;

import net.nekomine.common.model.VelocityServer;
import org.redisson.api.RedissonClient;

public class SpigotServerService extends BaseServerServiceImpl<VelocityServer, String> {

    public SpigotServerService(RedissonClient redissonClient, String mapName) {
        super(redissonClient, mapName);
    }
}