## 超卖问题

1. 数据库加唯一索引：防止用户重复购买
2. SQL加库存数量判断：防止库存变成负数

## 秒杀接口优化

思路：健身数据库访问

1. 系统初始化，把商品库存数量加载到Redis
2. 收到请求，Redis预减库存，库存不足，直接返回
3. 请求入队，立即返回排队中
4. 请求出队，生成订单，减少库存
5. 客户端轮休，是否秒杀成功