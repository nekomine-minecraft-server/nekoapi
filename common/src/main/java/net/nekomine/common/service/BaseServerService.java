package net.nekomine.common.service;

import net.nekomine.common.model.BaseModel;

import java.util.Optional;

public interface BaseServerService<Model extends BaseModel<Key>, Key> {

    Optional<Model> get(Key key);
}
