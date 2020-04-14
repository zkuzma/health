package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @auther 大雄
 * @create 2020-04-09 15:42
 */
@RestController
@RequestMapping("/login")
public class LoginMobileController {
    @Autowired
    JedisPool jedisPool;
    @Reference
    MemberService memberService;

    @RequestMapping("/check")
    public Result login(@RequestBody Map map, HttpServletResponse response){
        String redisCode = jedisPool.getResource().get((String) map.get("telephone") + RedisMessageConstant.SENDTYPE_LOGIN);
        String validateCode = (String) map.get("validateCode");
        if (redisCode == null || !redisCode.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        String telephone = (String) map.get("telephone");
        Member member=memberService.findMemberByTelephone(telephone);
        if (member==null){
            member=new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);

        }
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setPath("/pages");//路径
        cookie.setMaxAge(60*60*24*30);//有效期30天（单位秒）
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
