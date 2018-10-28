package com.mix.tpl.dao;

import com.mix.tpl.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User findByUserName(@Param("username") String username);
}
