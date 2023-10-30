package net.nekomine.common.service;

import net.nekomine.common.model.BaseModel;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface BaseService<Model extends BaseModel<Key>, Key> {
    Model getOrCreate(Key key, Model model);

    Model get(Key roleName);

    Model save(Model role);

    void getAsync(Key key, Consumer<Model> consumer);
}
