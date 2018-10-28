package com.mix.tpl.service;

import com.mix.tpl.entity.User;

public interface UserService {
    User findByUsername(String username);
}
