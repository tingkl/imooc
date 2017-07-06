package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by tingkl on 16/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉jnit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private long id = 1002;
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void testGetSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null) {
            seckill = seckillDao.queryById(id);
            System.out.println("from db");
            if (seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println("save redis");
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
        else {
            System.out.println("from redis:" + seckill);
        }
    }

    @Test
    public void testPutSeckill() throws Exception {

    }
}