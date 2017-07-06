package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by tingkl on 16/7/3.
 */
public interface SuccessKilledDao {
    /**
     * @param seckillId
     * @param userPhone
     * @return 插入多行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,
                                       @Param("userPhone") long userPhone);
}
