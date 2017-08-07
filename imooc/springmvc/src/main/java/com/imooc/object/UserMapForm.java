package com.imooc.object;

import java.util.Map;

/**
 * Created by tingkl on 2017/8/7.
 */
public class UserMapForm {
    Map<String, User> users;

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserMapForm{" +
                "users=" + users +
                '}';
    }
}
