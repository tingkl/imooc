package com.mix.miaosha.dao;

import com.mix.miaosha.domain.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where mobile = #{mobile}")
    public MiaoshaUser getByMobile(@Param("mobile") String mobile);

}

