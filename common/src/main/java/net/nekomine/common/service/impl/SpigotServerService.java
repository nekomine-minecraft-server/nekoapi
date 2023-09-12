package net.nekomine.common.service.impl;

import net.nekomine.common.model.SpigotServer;
import org.redisson.api.RedissonClient;

public class SpigotServerService extends BaseServerServiceImpl<SpigotServer, String> {

    public SpigotServerService(RedissonClient redissonClient) {
        super(redissonClient, "SPIGOT_SERVER_MAP");
    }
}