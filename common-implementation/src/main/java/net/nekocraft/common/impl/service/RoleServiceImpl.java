package net.nekocraft.common.impl.service;

import net.nekomine.common.dao.BaseDao;
import net.nekomine.common.model.Role;

@SuppressWarnings("unused")
public class RoleServiceImpl extends BaseServiceImpl<Role, String> {
    public RoleServiceImpl(BaseDao<Role, String> baseDao) {
        super(baseDao);
    }
}
