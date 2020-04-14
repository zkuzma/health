package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.SMSUtilsMyself;
import com.itheima.utils.ValidateCodeUtils;
import com.sun.tools.javac.jvm.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @auther 大雄
 * @create 2020-04-06 15:53
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateController {
    @Autowired
    JedisPool jedisPool;
    /**
     * 预约套餐发送验证码
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        String code=null;
        try {
            code = ValidateCodeUtils.generateValidateCode(4).toString();
            SMSUtilsMyself.sendShortMessage(SMSUtilsMyself.VALIDATE_CODE,telephone, code);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,5*60,code);
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);


    }
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        String code=null;
        try {
            code = ValidateCodeUtils.generateValidateCode(4).toString();
            if (false){

                SMSUtilsMyself.sendShortMessage(SMSUtilsMyself.VALIDATE_CODE,telephone, code);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

            jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,5*60,code);

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);


    }




}
