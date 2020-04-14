package com.itheima.job;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @auther 大雄
 * @create 2020-04-03 19:46
 */
public class ClearImg {
    @Autowired
    JedisPool jedisPool;


    public void deleteImg() {
        //差集,数据库没有的图片名称
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if (sdiff==null || sdiff.size()==0){
            System.out.println("没有垃圾图片");
        }else {
            for (String s : sdiff) {
                System.out.println("垃圾图片名称:"+s);
//            删除七牛云中的垃圾图片
                QiniuUtils.deleteFileFromQiniu(s);
                //删除redis中的垃圾图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, s);
            }
        }



    }
}
