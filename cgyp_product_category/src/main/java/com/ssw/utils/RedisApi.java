package com.ssw.utils;


import com.ssw.common.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

/**
 * @Auther:Wss
 * @Date:2019/12/4 0004
 * @Description:com.ssw.utils
 * @version: 1.0
 */
public class RedisApi {

    @Autowired
    RedisPool redisPool;
    /*字符串  set*/
public String set(String key,String value) {

   Jedis jedis = redisPool.getJedis();
   String result = null;
   try{
       result = jedis.set(key,value);
       redisPool.returnJedis(jedis);
   }catch(Exception e){
        redisPool.returnBrokenResource(jedis);
    }
        return result;
}
    /*字符串  get*/
    public String get(String key) {

        Jedis jedis = redisPool.getJedis();
        String result = null;
        try{
            result = jedis.get(key);
            redisPool.returnJedis(jedis);
        }catch(Exception e){
            redisPool.returnBrokenResource(jedis);
        }
        return result;
    }
    /*字符串  set 设置过期时间*/
    public String setex(String key,int timeout,String value) {

        Jedis jedis = redisPool.getJedis();
        String result = null;
        try{
            result = jedis.setex(key, timeout,value);
            redisPool.returnJedis(jedis);
        }catch(Exception e){
            redisPool.returnBrokenResource(jedis);
        }
        return result;
    }
    /*字符串  给某个可以set过期时间 */
    public Long expire(String key,int timeout) {

        Jedis jedis = redisPool.getJedis();
        Long result = null;
        try{
            result = jedis.expire(key, timeout);
            redisPool.returnJedis(jedis);
        }catch(Exception e){
            redisPool.returnBrokenResource(jedis);
        }
        return result;
    }
    /*字符串 加redis分布式事务锁*/
    public String set(String key,String value,String nx,String px,int timeout ){
        Jedis jedis = redisPool.getJedis();
        String result = null;
        try{
            result = jedis.set(key,value,nx,px,timeout);
            redisPool.returnJedis(jedis);
        }catch(Exception e){
            redisPool.returnBrokenResource(jedis);
        }
        return result;
    }
    /*释放redis事务锁*/
    public Long del(String key){
        Jedis jedis = redisPool.getJedis();
        Long result = null;
        try{
            result = jedis.del(key);
            redisPool.returnJedis(jedis);
        }catch(Exception e){
            redisPool.returnBrokenResource(jedis);
        }
        return result;
    }
}
