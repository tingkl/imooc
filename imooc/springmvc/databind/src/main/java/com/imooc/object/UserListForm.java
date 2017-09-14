package com.imooc.object;


import java.util.List;

/**
 * Created by tingkl on 2017/8/7.
 */
public class UserListForm {
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserListForm{" +
                "users=" + users +
                '}';
    }
}
