package com.mix.miaosha.service;

import com.mix.miaosha.dao.UserDao;
import com.mix.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class  UserService {
    @Autowired
    UserDao userDao;
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(1);
        u1.setName("111");
        User u2 = new User();
        u2.setId(2);
        u2.setName("222");
        userDao.insert(u2);
        userDao.insert(u1);
        return true;
    }
}
