package net.nekomine.common.service.impl;

import net.nekomine.common.model.VelocityServer;
import org.redisson.api.RedissonClient;

public class VelocityServerService extends BaseServerServiceImpl<VelocityServer, String> {
    public VelocityServerService(RedissonClient redissonClient) {
        super(redissonClient, "VELOCITY_SERVER_MAP");
    }
}
