package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tingkl on 16/7/4.
 */
//@Component  @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //注入Service依赖
    @Autowired //@Resource @Inject j2ee的标准, autowired为spring
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;
    private final String slat = "123456";
    public SeckillServiceImpl() {
    }

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        // 优化点:缓存优化
        /*
        * get from cache
        * if null
        * get db
        * else
        * put cache
        * */

        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            }
            else {
                redisDao.putSeckill(seckill);
                logger.info("save redis");
            }
            logger.info("from db");
        }
        else {
            logger.info("from redis");
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId); // TODO
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    /*
    * 使用注解控制事务方法的优点:
    * 1:开发团队达成一致约定,明确标准事务方法的编程风格
    * 2:保证事务方法执行时间尽可能短,不要穿插其他网络操作,RPC/HTTP请求或者玻璃到事务方法外部
    * 3:不是所有的方法都需要事务,如只有一条修改操作,制度操作不需要事务控制.
    *
    * */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillCloseException, RepeatKillException, SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        try {
            // 记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            // 唯一验证: seckillId, userPhone
            if (insertCount <= 0) {
                throw new RepeatKillException("seckill repeat");
            } else {
                int updateCount = seckillDao.reduceNumber(seckillId, new Date());
                if (updateCount <= 0) {
                    // 没有更新到记录
                    //rollback
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
            //  一定要抛出异常，然后才能触发回滚

        } catch (SeckillCloseException e1) {
            throw e1;
        }catch (RepeatKillException e2) {
            throw e2;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    //抛出异常是为了告诉spring是否rollback，此处使用存储过程的话，就不需要抛异常了
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if(md5 ==null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStateEnum.DATA_REWRITE);
        }
        Date killTime=new Date();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        //执行存储过程，result被赋值
        try {
            seckillDao.killByProcedure(map);
            int result=(Integer) map.get("result");
            if(result==1){
                SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
            }else{
                return new SeckillExecution(seckillId,SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
