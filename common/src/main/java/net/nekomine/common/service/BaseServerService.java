package net.nekomine.common.service;

import net.nekomine.common.model.BaseModel;

import java.util.Collection;
import java.util.Optional;

public interface BaseServerService<Model extends BaseModel<Id>, Id> {

    Optional<Model> get(Id id);

    Collection<Model> findAll();

}
