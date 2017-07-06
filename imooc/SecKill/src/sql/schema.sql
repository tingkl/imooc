--  创建数据库
CREATE DATABASE seckill;

-- 使用数据库
use seckill;

-- 创建秒杀库存表
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
-- mysql支持字符串时间
insert into seckill (name, number, start_time, end_time)
    VALUES
      ('1000元秒杀iphone6', 100, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
      ('500元秒杀ipad2', 200, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
      ('300元秒杀小米4', 300, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
      ('200元秒杀红米note', 400, '2015-11-01 00:00:00', '2015-11-02 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
create table success_killed (
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态表示:-1 无效 0 成功 1 已付款',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键，一个用户不能对同一个商品重复的秒杀*/
  key idx_create_time(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 为什么手写DDL
-- 记录每次上线的DDL修改
-- 上线V1.1
ALTER TABLE seckill
  DROP INDEX idx_create_time,
  ADD index idx_c_s(create_time, start_time);