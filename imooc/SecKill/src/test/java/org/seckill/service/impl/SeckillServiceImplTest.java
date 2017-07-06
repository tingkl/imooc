package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tingkl on 16/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1000l);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000l);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000;
        long phone = 188173508073l;
        String md5 = "699d36e9d75790b2c1d8f0ab5502e779";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", execution);
        } catch (RepeatKillException e) {
            logger.error("",e);
        }
        catch (SeckillCloseException e2) {
            logger.error("",e2);
        }
    }

    @Test
    public void testSeckillLogic() {
        long id = 1003l;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 111l;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e2) {
                logger.error( e2.getMessage());
            }
        }
        else {
            // 秒杀未开启
            logger.warn("exposer={}", "exposer");
        }
    }

    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1003;
        long phone = 12343343;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            System.out.println(execution.getStateInfo());
        }

    }
}