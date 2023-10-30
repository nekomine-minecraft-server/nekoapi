package net.nekocraft.common.impl.dao;

import net.nekomine.common.model.Role;
import org.redisson.api.RedissonClient;

@SuppressWarnings("unused")
public class RoleDaoImpl extends BaseDaoImpl<Role, String> {
    public RoleDaoImpl(RedissonClient redissonClient) {
        super(redissonClient, "ROLE_MAP");
    }
}
