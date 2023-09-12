package net.nekomine.common.service.impl;

import net.nekomine.common.model.SpigotServer;
import org.redisson.api.RedissonClient;

public class SpigotServerService extends BaseServerServiceImpl<SpigotServer, String> {

    public SpigotServerService(RedissonClient redissonClient, String mapName) {
        super(redissonClient, mapName);
    }
}