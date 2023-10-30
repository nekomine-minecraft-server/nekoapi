package net.nekocraft.common.impl.redis;

import net.nekomine.common.redis.RedisFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class EnvRedisFactory implements RedisFactory {

    @Override
    public RedissonClient create() {
        Config config = new Config();
        config.useSingleServer()
                .setPassword(System.getenv("redis_password"))
                .setUsername(System.getenv("redis_user"))
                .setAddress(System.getenv("redis_host"));

        return Redisson.create(config);
    }
}
