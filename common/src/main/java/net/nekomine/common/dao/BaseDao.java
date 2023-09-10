package net.nekomine.common.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<Model, Key> {

    Optional<Model> find(Key key);

    Model save(Model model);

    List<Model> findAll();
}
