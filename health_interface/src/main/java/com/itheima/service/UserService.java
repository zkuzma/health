package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @auther 大雄
 * @create 2020-04-11 13:01
 */
public interface UserService {
    User findUserByUsername(String username);
}
