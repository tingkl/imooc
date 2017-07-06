package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by tingkl on 16/7/4.
 */
/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉jnit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void testInsertSuccessKilled() throws Exception {
        int count = successKilledDao.insertSuccessKilled(1001l, 18817350807l);
        System.out.println(count);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1001l, 18817350807l);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}