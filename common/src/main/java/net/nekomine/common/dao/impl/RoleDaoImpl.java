package net.nekomine.common.dao.impl;

import net.nekomine.common.model.Role;
import org.redisson.api.RedissonClient;

public class RoleDaoImpl extends BaseDaoImpl<Role, String> {
    public RoleDaoImpl(RedissonClient redissonClient) {
        super(redissonClient, "ROLE_MAP");
    }
}
