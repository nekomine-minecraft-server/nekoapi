package net.nekocraft.common.impl.service;

import net.nekomine.common.dao.BaseDao;
import net.nekomine.common.model.User;

@SuppressWarnings("unused")
public class UserServiceImpl extends BaseServiceImpl<User, String> {
    public UserServiceImpl(BaseDao<User, String> baseDao) {
        super(baseDao);
    }
}
