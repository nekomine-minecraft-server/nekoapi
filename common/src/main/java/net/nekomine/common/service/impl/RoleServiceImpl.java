package net.nekomine.common.service.impl;

import net.nekomine.common.dao.BaseDao;
import net.nekomine.common.model.Role;

public class RoleServiceImpl extends BaseServiceImpl<Role, String> {
    public RoleServiceImpl(BaseDao<Role, String> baseDao) {
        super(baseDao);
    }
}
