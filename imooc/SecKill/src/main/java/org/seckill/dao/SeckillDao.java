package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tingkl on 16/7/3.
 */
public interface SeckillDao {
    /**
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1,表示更新多记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    Seckill queryById(long seckillId);

    List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);// java不保存形参名称，当有一个以上参数时，需要指定参数名

    void killByProcedure(Map<String, Object> map);
}
