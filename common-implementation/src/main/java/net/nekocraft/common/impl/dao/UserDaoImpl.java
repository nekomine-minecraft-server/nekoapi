package net.nekocraft.common.impl.dao;

import net.nekomine.common.model.User;
import org.redisson.api.RedissonClient;

@SuppressWarnings("unused")
public class UserDaoImpl extends BaseDaoImpl<User, String> {
    public UserDaoImpl(RedissonClient redissonClient) {
        super(redissonClient, "USER_MAP");
    }
}
