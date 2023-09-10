package net.nekomine.common.dao.impl;

import net.nekomine.common.model.User;
import org.redisson.api.RedissonClient;

public class UserDaoImpl extends BaseDaoImpl<User, String> {
    public UserDaoImpl(RedissonClient redissonClient) {
        super(redissonClient, "USER_MAP");
    }
}
