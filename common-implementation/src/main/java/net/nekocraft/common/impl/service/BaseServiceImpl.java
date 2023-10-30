package net.nekocraft.common.impl.service;

import net.nekomine.common.dao.BaseDao;
import net.nekomine.common.model.BaseModel;
import net.nekomine.common.service.BaseService;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BaseServiceImpl<Model extends BaseModel<Key>, Key> implements BaseService<Model, Key> {

    private final BaseDao<Model, Key> baseDao;

    public BaseServiceImpl(BaseDao<Model, Key> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public Model getOrCreate(Key key, Model model) {
        return baseDao.find(key).orElse(baseDao.save(model));
    }

    @Override
    public Model get(Key key) {
        return baseDao.find(key).orElseThrow();
    }

    @Override
    public Model save(Model model) {
        return baseDao.save(model);
    }

    @Override
    public void getAsync(Key key, Consumer<Model> consumer) {
        CompletableFuture.runAsync(() -> {
            Model model = get(key);
            consumer.accept(model);
            save(model);
        });
    }
}
