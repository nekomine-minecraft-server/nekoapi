package net.nekomine.common.service.impl;

import net.nekomine.common.model.BaseModel;
import net.nekomine.common.service.BaseServerService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Optional;

public class BaseServerServiceImpl<Model extends BaseModel<Key>, Key> implements BaseServerService<Model, Key> {
    private final RMap<Key, Model> roleMap;

    public BaseServerServiceImpl(RedissonClient redissonClient, String mapName) {
        this.roleMap = redissonClient.getMap(mapName);
    }

    public Optional<Model> get(Key key) {
        return Optional.ofNullable(roleMap.get(key));
    }

}
