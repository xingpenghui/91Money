package com.qfedu.core.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *@Author feri
 *@Date Created in 2018/7/20 11:56
 */
public class JedisUtil {

    private String host;
    private int port;
    private String password;
    private Jedis jedis;

    public JedisUtil(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
        jedis=new Jedis(host,port);
        jedis.auth(password);
    }

    //新增
    public void addStr(String key,String value){
        jedis.set(key,value);
    }

    public void addStr(String key,String value,TimeUnit unit,int time){
        addStr(key,value);
        expire(key,unit,time);
    }
    //新增 list
    public void addList(String key,String... values){
        jedis.lpush(key,values);
    }
    //新增 set
    public void addSet(String key,String... values){
        jedis.sadd(key,values);
    }
    //新增 zset
    public void addZSet(String key,double socre,String value){
        jedis.zadd(key,socre,value);
    }
    //批量新增
    public void addZSet(String key, Map<String,Double> map){
        jedis.zadd(key,map);
    }
    public void addHash(String key,String field,String value){
        jedis.hset(key,field,value);
    }

    //删除
    public void delKey(String... keys){
        jedis.del(keys);
    }
    //删除元素
    public void delList(String key,String value){
        jedis.lrem(key,1,value);
    }
    public void delList(String key,int count,String value){
        jedis.lrem(key,count,value);
    }
    public void delSet(String key,String... values){
        jedis.srem(key,values);
    }
    public void delHash(String key,String... filed){
        jedis.hdel(key,filed);
    }

    //修改
    public void updList(String key ,long index,String value){
        jedis.lset(key,index,value);
    }
//    public void updList(String key ,long index,String value){
//        jedis.lset(key,index,value);
//    }

    //查询
    public String getStr(String key){
        return jedis.get(key);
    }
    public List<String> getList(String key){
      return  jedis.lrange(key,0,countList(key)-1);
    }

    //查询元素个数
    public long countList(String key){
        return jedis.llen(key);
    }
    public long countSet(String key){
        return jedis.scard(key);
    }
    //设置有效期  只支持天、时、分、秒  默认秒
    public void expire(String key,TimeUnit unit,int time){
        int d=0;
        switch (unit){
            case DAYS:
                d=time*24*60*60;
            break;
            case HOURS:
                d=time*60*60;
                break;
            case MINUTES:
                d=time*60;
                break;
                default:
                    d=time;
                    break;
        }
        jedis.expire(key,d);
    }
    //取消有效期
    public void perists(String key){
        jedis.persist(key);
    }

    //是否存在
    public boolean isKey(String key){
        return jedis.exists(key);
    }

    public Set<String> keys(String p){
        return jedis.keys(p);
    }
    public long countKeys(){
        return  jedis.dbSize();
    }
    public void changeDb(int index){
        jedis.select(index);
    }

    public void destroy(){
        if(jedis!=null){
            jedis.disconnect();
            jedis.close();
        }
    }
}
