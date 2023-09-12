package net.nekomine.common.redis;

import org.redisson.api.RedissonClient;

public interface RedisFactory {

    RedissonClient create();

}
