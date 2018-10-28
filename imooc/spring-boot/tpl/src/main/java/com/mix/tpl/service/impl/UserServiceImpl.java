package com.mix.tpl.service.impl;

import com.mix.tpl.dao.UserDao;
import com.mix.tpl.entity.User;
import com.mix.tpl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }
}
