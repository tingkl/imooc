## 秒杀优化

>> 1.判断库存

    redis缓存

>> 2.判断是否秒杀过

    redis缓存

>> 3.减库存，下订单，写秒杀订单（事务）

    1. 减库存（再次判断库存 > 0）
        ```
        update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0
    
        ```
    2. 下订单
        减库存返回修改的行数，只有行数 > 0 时才下订单（effectedRow=true），说明还有库存才能下订单
        数据库设置秒杀订单唯一约束goodsId + userId,防止一个用户同时发出多条秒杀请求，导致秒杀了一种商品多件
        当一个用户同时秒杀一个商品多件时，由于唯一约束，数据库出错，事务回滚


## 超卖问题

1. 数据库加唯一索引：防止用户重复购买
2. SQL加库存数量判断：防止库存变成负数

## 秒杀接口优化

思路：减少数据库访问

1. 系统初始化，把商品库存数量加载到Redis
2. 收到请求，Redis预减库存，库存不足，直接返回
3. 请求入队，立即返回排队中
4. 请求出队，生成订单，减少库存
5. 客户端轮询，是否秒杀成功


## nginx负载均衡

```
upstream ms {
    server localhost:8060   weight=1 max_fails=2 fail_timeout=30s;
    server otherserver:8060 weight=1 max_fails=2 fail_timeout=30s;
}
server
{
    listen 80;
    server_name ms.tingkl.com;
    location / {
        proxy_pass  http://ms;
        # 传递ip地址
        # Forward the user's IP address to Rails
        proxy_set_header X-Real-IP $remote_addr;
        # needed for HTTPS
        # proxy_set_header X_FORWARDED_PROTO https;
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header Host $host;
        proxy_set_header Origin $http_origin;
        proxy_set_header Referer $http_referer;
        # 文件上传大小限制
        client_max_body_size  100m;
    }
}
```
