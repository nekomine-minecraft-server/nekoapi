package net.nekomine.common.dao.impl;

import net.nekomine.common.dao.BaseDao;
import net.nekomine.common.model.BaseModel;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Optional;

public class BaseDaoImpl<Model extends BaseModel<Key>, Key> implements BaseDao<Model, Key> {

    private final RMap<Key, Model> roleMap;

    public BaseDaoImpl(RedissonClient redissonClient, String mapName) {
        this.roleMap = redissonClient.getMap(mapName);
    }

    @Override
    public Optional<Model> find(Key key) {
        return Optional.ofNullable(roleMap.get(key));
    }

    @Override
    public Model save(Model model) {
        return roleMap.put(model.getKey(), model);
    }

    @Override
    public List<Model> findAll() {
        return roleMap.values().stream().toList();
    }
}
