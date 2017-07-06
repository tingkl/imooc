package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tingkl on 16/7/3.
 */

/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉jnit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入 Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() throws Exception {
        int updateCount = seckillDao.reduceNumber(1000L, new Date());
        System.out.println(updateCount);
    }

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        //java没有保存形参的记录
        // queryAll(int offset, int limit) -> queryAll(arg0, arg1)
        // 当有多个参数时,需要告诉mybatis参数名,用mybatis注解

        List<Seckill> seckillList = seckillDao.queryAll(0, 100);
        for (Seckill seckill: seckillList) {
            System.out.println(seckill);
        }
    }
}