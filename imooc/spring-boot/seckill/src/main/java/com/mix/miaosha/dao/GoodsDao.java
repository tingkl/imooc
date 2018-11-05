package com.mix.miaosha.dao;

import com.mix.miaosha.domain.entity.MiaoshaGoods;
import com.mix.miaosha.domain.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {
    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsByGoodsId(@Param("goodsId") Long goodsId);

    // 数据库本身会对写加锁，不会出现两个线程更新一条记录的情况
    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(MiaoshaGoods g);
}
