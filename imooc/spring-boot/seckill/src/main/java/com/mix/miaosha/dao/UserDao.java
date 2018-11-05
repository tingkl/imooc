package com.mix.miaosha.dao;

import com.mix.miaosha.domain.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(int id);

    @Insert("insert into user(id, name)values(#{id}, #{name})")
    public Integer insert(User user);

    @Update("update user set password = '123456' where name = #{name}")
    int effectedRow(String name);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = 1 and stock_count > 0")
    int effectedRowReduce();
}

