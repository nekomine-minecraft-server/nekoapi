package net.nekocraft.common.impl.service;

import net.nekomine.common.model.VelocityServer;
import net.nekomine.common.service.VelocityServerService;
import org.redisson.api.RedissonClient;

public class VelocityServerServiceImpl extends BaseServerServiceImpl<VelocityServer, String> implements VelocityServerService {
    public VelocityServerServiceImpl(RedissonClient redissonClient) {
        super(redissonClient, "VELOCITY_SERVER_MAP");
    }
}
