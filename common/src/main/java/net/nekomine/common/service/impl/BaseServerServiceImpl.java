package net.nekomine.common.service.impl;

import net.nekomine.common.model.BaseModel;
import net.nekomine.common.service.BaseServerService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.Optional;

public class BaseServerServiceImpl<Model extends BaseModel<Key>, Key> implements BaseServerService<Model, Key> {
    protected final RMap<Key, Model> serverMap;

    public BaseServerServiceImpl(RedissonClient redissonClient, String mapName) {
        this.serverMap = redissonClient.getMap(mapName);
    }

    public Optional<Model> get(Key key) {
        return Optional.ofNullable(serverMap.get(key));
    }

    public Collection<Model> findAll() {
        return serverMap.values();
    }

}
