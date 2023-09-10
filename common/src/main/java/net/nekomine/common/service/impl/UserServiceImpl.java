package net.nekomine.common.service.impl;

import net.nekomine.common.dao.BaseDao;
import net.nekomine.common.model.User;

public class UserServiceImpl extends BaseServiceImpl<User, String> {
    public UserServiceImpl(BaseDao<User, String> baseDao) {
        super(baseDao);
    }
}
