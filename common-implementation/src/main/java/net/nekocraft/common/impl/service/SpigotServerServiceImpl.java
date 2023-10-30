package net.nekocraft.common.impl.service;

import net.nekomine.common.model.SpigotServer;
import net.nekomine.common.service.SpigotServerService;
import org.redisson.api.RedissonClient;

public class SpigotServerServiceImpl extends BaseServerServiceImpl<SpigotServer, String> implements SpigotServerService {

    public SpigotServerServiceImpl(RedissonClient redissonClient) {
        super(redissonClient, "SPIGOT_SERVER_MAP");
    }
}